/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.parancoe.yaml;

import java.awt.Color;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SpecialTreatmentManager {

    Map<String, DefaultSpecialTreatment> treatmentTable = new HashMap<String, DefaultSpecialTreatment>();

    static class Holder {
        Class clazz;

        Map<String, Object> values = new HashMap<String, Object>();

        Holder(Class clazz) {
            this.clazz = clazz;
        }
    }

    static class DefaultSpecialTreatment {
        Class clazz;

        String[] constructorFields;

        List<String> removeFields = new ArrayList<String>();

        DefaultSpecialTreatment(String classname) {
            try {
                this.clazz = Class.forName(classname);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public Object getHolder() {
            return new Holder(clazz);
        }

        public void setProperty(Object obj, String key, Object value) {
            Holder holder = ((Holder) obj);
            holder.values.put(key, value);
        }

        public Object getRealObject(Object obj) {
            Holder holder = (Holder) obj;
            return decode(holder.values);
        }

        public Class getPropertyType(Object obj, String key) {
            try {
                Field field = clazz.getField(key);
                if (ReflectionUtil.isPublicMemberField(field))
                    return field.getType();
            } catch (Exception e) {
            }
            PropertyDescriptor prop = ReflectionUtil.getPropertyDescriptor(clazz, key);
            if (prop != null)
                return prop.getPropertyType();
            else
                throw new YamlParserException("No field or property named " + key + " found for class "
                        + clazz.getName());

        }

        public Object decode(Map<String, Object> map) {
            ArrayList<Class> constructorArgsTypes = new ArrayList<Class>();
            ArrayList<Object> constructorArgs = new ArrayList<Object>();
            if (constructorFields != null)
                for (String fieldName : constructorFields) {
                    Class type = null;
                    try {
                        Field field = clazz.getField(fieldName);
                        type = field.getType();
                    } catch (Exception e) {
                        PropertyDescriptor prop = ReflectionUtil.getPropertyDescriptor(clazz, fieldName);
                        type = prop.getPropertyType();
                    }
                    constructorArgsTypes.add(type);
                    constructorArgs.add(map.get(fieldName));
                }
            Object newObject = ReflectionUtil.invokeConstructor(clazz, constructorArgsTypes.isEmpty() ? null
                    : constructorArgsTypes.toArray(new Class[] {}), constructorArgs.isEmpty() ? null
                    : constructorArgs.toArray());
            return newObject;
        }

        public void encode(Object obj, YamlEncoder enc, String indent) {
            if (!enc.isMinimalOutput())
                enc.out.println("!" + clazz.getCanonicalName());
            else
                enc.out.println();
            for (String fieldname : constructorFields) {
                try {

                    Class type = null;
                    Object value = null;
                    try {
                        Field field = clazz.getField(fieldname);
                        if (ReflectionUtil.isPublicMemberField(field)) {
                            type = field.getType();
                            value = field.get(obj);
                        }
                    } catch (Exception e) {
                    }
                    if (type == null) {
                        PropertyDescriptor prop = ReflectionUtil.getPropertyDescriptor(clazz, fieldname);
                        type = prop.getPropertyType();
                        value = prop.getReadMethod().invoke(obj, null);
                    }
                    enc.out.print(indent + fieldname + ": ");
                    if (type.isPrimitive())
                        enc.writeSimpleValue(value, type, enc.indent(indent));
                    else
                        enc.writeObject(value, enc.indent(indent), type);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void traverseAndCount(Object obj, YamlEncoder enc) {
            List<PropertyDescriptor> props = ReflectionUtil.getPropertiesExcluding(removeFields, obj);
            List<Field> allFields = ReflectionUtil.getFieldsExcluding(removeFields, obj);
            List<Field> fields = new ArrayList<Field>(allFields);
            for (Field field : allFields)
                for (PropertyDescriptor prop : props)
                    if (field.getName().equals(prop.getName()))
                        fields.remove(field);
            for (PropertyDescriptor prop : props) {
                if (!prop.getPropertyType().isPrimitive()) {
                    Method readMethod = prop.getReadMethod();
                    try {
                        Object value = readMethod.invoke(obj, null);
                        enc.traverseAndCount(value);
                    } catch (Exception e) {
                        throw new YamlParserException(e);
                    }
                }
            }
            for (Field field : fields) {
                if (ReflectionUtil.isPublicMemberField(field) && !field.getType().isPrimitive()) {
                    Object value = null;
                    try {
                        value = field.get(obj);
                    } catch (Exception e) {
                        throw new YamlParserException("This shouldn't happen.");
                    }

                    enc.traverseAndCount(value);
                }
            }
        }
    }

    static class ColorSpecialTreatment extends DefaultSpecialTreatment {

        public ColorSpecialTreatment(String classname) {
            super(classname);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.parancoe.yaml.yaml.SpecialTreatmentManager.DefaultSpecialTreatment#decode(java.util.Map)
         */
        @Override
        public Object decode(Map<String, Object> map) {
            return new Color(((Number) map.get("red")).floatValue() / 256, ((Number) map.get("green"))
                    .floatValue() / 256, ((Number) map.get("blue")).floatValue() / 256, ((Number) map
                    .get("alpha")).floatValue() / 256);
        }

    }

    public SpecialTreatmentManager() {
        initialize();
    }

    void registerConstructor(String classname, String[] fields) {
        if (treatmentTable.containsKey(classname)) {
            treatmentTable.get(classname).constructorFields = fields;
        } else {
            DefaultSpecialTreatment enc = new DefaultSpecialTreatment(classname);
            enc.constructorFields = fields;
            treatmentTable.put(classname, enc);
        }
    }

    void registerSpecialTreatment(String classname, DefaultSpecialTreatment specialTreatment) {
        treatmentTable.put(classname, specialTreatment);
    }

    void removeProperty(String classname, String propertyname) {
        if (treatmentTable.containsKey(classname)) {
            treatmentTable.get(classname).removeFields.add(propertyname);
        } else {
            DefaultSpecialTreatment enc = new DefaultSpecialTreatment(classname);
            enc.removeFields.add(propertyname);
            treatmentTable.put(classname, enc);
        }
    }

    public boolean needsSpecialTreatment(Object value) {
        if (value instanceof Holder)
            return treatmentTable.containsKey(holderKey(value));
        else
            return treatmentTable.containsKey(value.getClass().getName());
    }

    public boolean needsSpecialTreatment(String classname) {
        return treatmentTable.containsKey(classname);
    }

    public void encode(Object obj, YamlEncoder enc, String indent) {
        treatmentTable.get(obj.getClass().getName()).encode(obj, enc, indent);
    }

    public Class getPropertyType(Object obj, String key) {
        return treatmentTable.get(holderKey(obj)).getPropertyType(obj, key);
    }

    public void setProperty(Object obj, String key, Object value) {
        treatmentTable.get(holderKey(obj)).setProperty(obj, key, value);
    }

    public Object getHolder(String classname) {
        return treatmentTable.get(classname).getHolder();
    }

    public Object getRealObject(Object holder) {
        return treatmentTable.get(holderKey(holder)).getRealObject(holder);
    }

    public String holderKey(Object holder) {
        return ((Holder) holder).clazz.getName();
    }

    public void traverseAndCount(Object value, YamlEncoder enc) {
        treatmentTable.get(value.getClass().getName()).traverseAndCount(value, enc);
    }

    void initialize() {
        registerConstructor("java.awt.Point", new String[] { "x", "y" });
        registerConstructor("java.awt.Dimension", new String[] { "width", "height" });
        registerConstructor("java.awt.Rectangle", new String[] { "x", "y", "width", "height" });

        registerConstructor("java.awt.Font", new String[] { "name", "style", "size" });

        removeProperty("java.awt.Rectangle", "bounds");
        removeProperty("java.awt.Dimension", "size");
        removeProperty("java.awt.Point", "location");

        registerSpecialTreatment("java.awt.Color", new ColorSpecialTreatment("java.awt.Color"));
        registerConstructor("java.awt.Color", new String[] { "red", "green", "blue", "alpha" });
        registerConstructor("javax.swing.plaf.ColorUIResource", new String[] { "red", "green", "blue" });
    }

}
