/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Yaml - DISCONTINUED.
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

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * YamlEncoder - The usage of YamlEncoder mirrors that of java.beans.XMLEncoder.
 * You create an encoder, make some calls to writeObject, and then close the
 * encoder. In most cases you may find it is not necessary to access this class
 * directly as {@link Yaml} contain methods for the most common usages. The
 * utility functions that were previously in this class are now in {@link Yaml}.
 */
public class YamlEncoder {

    PrintWriter out;

    Map<Object, ObjectEntry> referenceMap = new IdentityHashMap<Object, ObjectEntry>();

    NameGenerator nameGenerator = new NameGenerator();

    SpecialTreatmentManager stManager = new SpecialTreatmentManager();

    YamlConfig config = YamlConfig.getDefaultConfig();

    /**
     * Creates a YamlEncoder writing to specifed stream.
     * 
     * @param out
     *            the output stream to write to.
     */
    public YamlEncoder(OutputStream out) {
        this.out = new PrintWriter(new OutputStreamWriter(out));
    }

    /**
     * Creates a YamlEncoder writing to specifed file.
     * 
     * @param file
     *            file to write to.
     * @throws FileNotFoundException
     */
    public YamlEncoder(File file) throws FileNotFoundException {
        this(new FileOutputStream(file));
    }

    /**
     * Creates a YamlEncoder writing to specifed file.
     * 
     * @param filename
     *            name of file to write to.
     * @throws FileNotFoundException
     */
    public YamlEncoder(String filename) throws FileNotFoundException {
        this(new File(filename));
    }

    /**
     * Returns the indentation amount used for one indentation level.
     * 
     * @return the amount of indentation used for one indentation level.
     */
    public String getIndentAmount() {
        return config.getIndentAmount();
    }

    /**
     * Sets indentation amount.
     * 
     * @param indentAmount
     *            must be a string consisting only of spaces.
     */
    public void setIndentAmount(String indentAmount) {
        config.setIndentAmount(indentAmount);
    }

    /**
     * Returns whether the minimal output option is set.
     * 
     * @return whether the minimal output option is set.
     */
    public boolean isMinimalOutput() {
        return config.isMinimalOutput();
    }

    /**
     * Sets the minimal output option.
     * 
     * @param minimalOutput
     *            true for on; false for off.
     */
    public void setMinimalOutput(boolean minimalOutput) {
        config.setMinimalOutput(minimalOutput);
    }

    class ObjectEntry {
        Object target;

        String refname = null;

        int refs = 0;

        boolean anchorDeclared = false;

        ObjectEntry(Object t) {
            target = t;
        }

        public String toString() {
            return "{target: " + target + ", refname: " + refname + ", refs: " + refs + "}";
        }
    }

    class NameGenerator {
        BigInteger i = BigInteger.ONE;

        String generate(Object obj) {
            String ret = i.toString();
            i = i.add(BigInteger.ONE);
            return ret;
        }
    }

    void traverseAndCount(Object obj) {
        if (obj == null)
            return;
        if (obj instanceof String)
            return;
        mark(obj);
        if (refCount(obj) > 1)
            return;
        else if (ReflectionUtil.isSimpleType(obj.getClass()))
            return;
        else if (needSpecialTreatment(obj))
            traverseAndCountSpecialTreatment(obj);
        else if (obj instanceof Collection)
            traverseAndCountCollection((Collection) obj);
        else if (obj instanceof Map)
            traverseAndCountMap((Map) obj);
        else if (obj.getClass().isArray())
            traverseAndCountArray(obj);
        else
            traverseAndCountJavaBean(obj);
    }

    void traverseAndCountCollection(Collection c) {
        for (Object obj : c) {
            traverseAndCount(obj);
        }
    }

    void traverseAndCountArray(Object array) {
        if (!array.getClass().getComponentType().isPrimitive())
            for (int i = 0; i < Array.getLength(array); i++) {
                Object obj = Array.get(array, i);
                traverseAndCount(obj);
            }
    }

    void traverseAndCountMap(Map map) {
        for (Map.Entry ent : (Set<Map.Entry>) map.entrySet()) {
            traverseAndCount(ent.getKey());
            traverseAndCount(ent.getValue());
        }
    }

    void traverseAndCountJavaBean(Object bean) {
        List<PropertyDescriptor> props = ReflectionUtil.getProperties(bean);
        for (PropertyDescriptor prop : props) {
            if (!prop.getPropertyType().isPrimitive())
                try {
                    Object value = prop.getReadMethod().invoke(bean, null);
                    if (value != null)
                        traverseAndCount(value);
                } catch (Exception e) {
                    System.err.println(e);
                }
        }
    }

    void traverseAndCountSpecialTreatment(Object obj) {

    }

    int refCount(Object obj) {
        ObjectEntry ent = referenceMap.get(obj);
        return ent != null ? ent.refs : 0;
    }

    boolean toBeAnchored(Object obj) {
        ObjectEntry ent = referenceMap.get(obj);
        return (ent != null && ent.refs > 1 && !ent.anchorDeclared);
    }

    boolean toBeAliased(Object obj) {
        ObjectEntry ent = referenceMap.get(obj);
        return (ent != null && ent.refs > 1 && ent.anchorDeclared);
    }

    void mark(Object obj) {
        ObjectEntry ent = referenceMap.get(obj);
        if (ent == null) {
            ent = new ObjectEntry(obj);
            referenceMap.put(obj, ent);
        }
        ent.refs++;
    }

    void generateNames() {
        for (Map.Entry<Object, ObjectEntry> ent : referenceMap.entrySet()) {
            if (ent.getValue().refs >= 2)
                ent.getValue().refname = generateName(ent.getValue());
        }
    }

    String generateName(Object obj) {
        return nameGenerator.generate(obj);
    }

    /**
     * Write an object to the stream.
     * 
     * @param obj
     *            object to write.
     */
    public void writeObject(Object obj) {
        traverseAndCount(obj);
        generateNames();
        // if (!minimalOutput)
        out.print("--- ");
        writeObject(obj, "", obj.getClass());
        reset();
    }

    void reset() {
        referenceMap.clear();
        nameGenerator = new NameGenerator();
    }

    String indent(String s) {
        return getIndentAmount() + s;
    }

    boolean needSpecialTreatment(Object value) {
        return stManager.needsSpecialTreatment(value);
    }

    void writeSpecial(Object value, String indent, Class expectedType) {
        stManager.encode(value, this, indent);
    }

    void writeObject(Object value, String indent, Class expectedType) {
        if (value == null)
            out.println("~");
        else if (toBeAliased(value))
            writeReference(value);
        else {
            if (toBeAnchored(value))
                writeAlias(value);
            if (ReflectionUtil.isSimpleType(value.getClass()))
                writeSimpleValue(value, expectedType, indent);
            else if (needSpecialTreatment(value))
                writeSpecial(value, indent, expectedType);
            else if (value instanceof Collection)
                writeCollection((Collection) value, indent);
            else if (value instanceof Map)
                writeMap((Map) value, indent, expectedType);
            else if (value.getClass().isArray())
                writeArray(value, indent, expectedType);
            else
                writeJavaBean(value, indent, expectedType);
        }
    }

    void writeReference(Object value) {
        ObjectEntry ent = referenceMap.get(value);
        out.println("*" + ent.refname);
    }

    void writeAlias(Object value) {
        ObjectEntry ent = referenceMap.get(value);
        out.print("&" + ent.refname + " ");
        ent.anchorDeclared = true;

    }

    void writeSimpleValue(Object value, Class expectedType, String indent) {
        if (value instanceof String || value instanceof Character)
            out.println(stringify(value, indent));
        else {
            if ((expectedType == null || !isMinimalOutput())
                    && !(value.getClass() == Integer.class || value.getClass() == Boolean.class)) {
                out.print("!" + getTransferName(value.getClass()) + " ");
            }
            if (value instanceof Date)
                out.println("" + ((Date) value).getTime());
            else if (value instanceof File)
                out.println(value.toString());
            else if (value.getClass().isEnum())
                writeEnum(value, expectedType);
            else
                out.println(value.toString());
        }
    }

    void writeEnum(Object value, Class expectedType) {
        try {
            out.println(value.getClass().getMethod("name", null).invoke(value, null));
        } catch (Exception e) {
            throw new YamlParserException("Cannot invoke name() method of " + value);
        }
    }

    String quote(Object value) {
        return "\"" + value + "\"";
    }

    static String stringify(Object value, String indent) {
        String text = value.toString();
        // special handling for multiple lines
        if (text.indexOf('\n') != -1) {
            StringBuffer sb = new StringBuffer();
            sb.append("|");
            StringTokenizer tokenizer = new StringTokenizer(text, "\n");
            while (tokenizer.hasMoreTokens()) {
                String line = tokenizer.nextToken();
                sb.append("\n" + indent + line);
            }
            return sb.toString();
        } else if ("".equals(text)) {
            return quote(text);
        } else {
            String indicators = ":[]{},\"";
            boolean quoteIt = false;
            for (char c : indicators.toCharArray())
                if (text.indexOf(c) != -1) {
                    quoteIt = true;
                    break;
                }
            if (quoteIt) {
                text = text.replaceAll("\\\\", "\\\\\\\\");
                text = text.replaceAll("\"", "\\\\\"");
                text = quote(text);
            }
            return text;
        }
    }

    static String quote(String value) {
        return "\"" + value + "\"";
    }

    /**
     * assumes map is not null
     * 
     * @param map
     * @param indent
     */
    void writeMap(Map<String, Object> map, String indent, Class expectedType) {
        if (!isMinimalOutput() && (expectedType != Map.class || expectedType != HashMap.class))
            out.println("!" + getTransferName(map.getClass()));
        else
            out.println();
        for (Map.Entry<String, Object> ent : map.entrySet()) {
            out.print(indent + ent.getKey() + ": ");
            writeObject(ent.getValue(), indent(indent), null);
        }
    }

    /**
     * assumes col is not null
     * 
     * @param col
     * @param indent
     */
    void writeCollection(Collection col, String indent) {
        if (col.size() > 0) {
            if (isMinimalOutput() || col instanceof ArrayList)
                out.println();
            else
                out.println("!" + getTransferName(col.getClass()));
            for (Object o : col) {
                out.print(indent + "- ");
                writeObject(o, indent(indent), null);
            }
        }
    }

    /**
     * assumes array is not null
     * 
     * @param array
     * @param indent
     */
    void writeArray(Object array, String indent, Class expectedType) {
        if (Array.getLength(array) == 0) {
            if (isMinimalOutput() && array.getClass() == expectedType)
                out.println("[]");
            else
                out.println("!" + ReflectionUtil.arrayName(array.getClass(), config) + " []");
        } else {
            if (isMinimalOutput() && array.getClass() == expectedType)
                out.println();
            else
                out.println("!" + ReflectionUtil.arrayName(array.getClass(), config));
            for (int i = 0; i < Array.getLength(array); i++) {
                Object o = Array.get(array, i);
                out.print(indent + "- ");
                if (array.getClass().getComponentType().isPrimitive())
                    // if elements are primitive, they won't be in the reference
                    // map
                    // do don't treat it like an object and just write it out
                    writeSimpleValue(o, expectedType == null ? array.getClass().getComponentType()
                            : expectedType.getComponentType(), indent(indent));
                else
                    writeObject(o, indent(indent), expectedType == null ? array.getClass().getComponentType()
                            : expectedType.getComponentType());
            }
        }
    }

    /**
     * assumes value is not null
     * 
     * @param value
     * @param indent
     */
    private void writeJavaBean(Object value, String indent, Class expectedType) {

        List<PropertyDescriptor> allProps = ReflectionUtil.getProperties(value);
        List<Object> propsOrFields = new ArrayList<Object>();
        List<Field> fields = ReflectionUtil.getFields(value);
        Object prototype = null;
        try {
            prototype = value.getClass().newInstance();
        } catch (Exception e) {
        }
        for (Field field : fields) {
            if (!ReflectionUtil.isPublicMemberField(field))
                continue;
            for (PropertyDescriptor prop : allProps)
                if (field.getName().equals(prop.getName()))
                    continue;
            Object pv = null;
            Object protov = null;
            try {
                pv = field.get(value);
                if (prototype != null)
                    protov = field.get(prototype);
            } catch (Exception e) {
            }
            if (prototype != null && !same(pv, protov)) {
                propsOrFields.add(field);
            }
        }
        for (PropertyDescriptor prop : allProps) {
            Object pv = null;
            Object protov = null;
            try {
                pv = prop.getReadMethod().invoke(value, (Object[]) null);
                if (prototype != null)
                    protov = prop.getReadMethod().invoke(prototype, (Object[]) null);
            } catch (Exception e) {
            }
            if (prototype != null && !same(pv, protov)) {
                propsOrFields.add(prop);
            }
        }

        if (propsOrFields.size() == 0) {
            if (isMinimalOutput() && value.getClass() == expectedType)
                out.println("{}");
            else
                out.println("!" + getTransferName(value.getClass()) + " {}");
        } else {
            if (isMinimalOutput() && value.getClass() == expectedType)
                out.println();
            else
                out.println("!" + getTransferName(value.getClass()));

            for (Object obj : propsOrFields) {
                Object pv = null;
                Object protov = null;
                if (obj instanceof PropertyDescriptor) {
                    PropertyDescriptor prop = (PropertyDescriptor) obj;
                    try {
                        pv = prop.getReadMethod().invoke(value, (Object[]) null);
                        if (prototype != null)
                            protov = prop.getReadMethod().invoke(prototype, (Object[]) null);
                    } catch (Exception e) {
                    }
                    if (prototype != null && !same(pv, protov)) {
                        out.print(indent + prop.getName() + ": ");
                        writeObject(pv, indent(indent), prop.getPropertyType());
                    }
                } else {
                    Field field = (Field) obj;
                    try {
                        pv = field.get(value);
                        if (prototype != null)
                            protov = field.get(prototype);
                    } catch (Exception e) {
                    }
                    if (prototype != null && !same(pv, protov)) {
                        out.print(indent + field.getName() + ": ");
                        writeObject(pv, indent(indent), field.getType());
                    }
                }
            }
        }
    }

    boolean same(Object one, Object other) {
        if (one != null) {
            return one.equals(other);
        } else if (other != null)
            return other.equals(one);
        else
            return true;
    }

    String getTransferName(Class clazz) {
        return ReflectionUtil.className(clazz, config);
    }

    /**
     * Closes this YamlEncoder instance. This must be done after a write
     * sequence for the write to be effective.
     * 
     */
    public void close() {
        out.close();
    }

    /**
     * Flushes the outputStream that this YamlEncoder points to.
     * 
     */
    public void flush() {
        out.flush();
    }

    /**
     * 
     * @return the config object for this encoder.
     */
    public YamlConfig getConfig() {
        return config;
    }

    /**
     * 
     * @param config
     *            the new config object for this encoder.
     */
    public void setConfig(YamlConfig config) {
        this.config = config;
    }

}
