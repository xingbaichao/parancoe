/*
 * Copyright (c) 2005, Yu Cheung Ho
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 *
 *    * Redistributions of source code must retain the above copyright notice, this list of 
 *        conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above copyright notice, this list 
 *        of conditions and the following disclaimer in the documentation and/or other materials 
 *        provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF 
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.parancoe.yaml;

import static org.parancoe.yaml.parser.YamlParser.MAP_CLOSE;
import static org.parancoe.yaml.parser.YamlParser.MAP_SEPARATOR;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import org.ho.util.Logger;

class MapState extends State {

    String key;

    MapState(Map<String, Object> aliasMap, Stack<State> stack, YamlDecoder decoder, Logger logger) {
        super(aliasMap, stack, decoder, logger);
    }

    @Override
    public void nextOnContent(String type, String content) {
        if (key == null)
            key = content;
        else {
            Object newObject = null;
            if ("alias".equals(type)) {
                String alias = content.substring(1);
                newObject = aliasMap.get(alias);
            }
            if (decoder.getStManager().needsSpecialTreatment(obj)) {
                Class clazz = decoder.getStManager().getPropertyType(obj, key);
                decoder.getStManager().setProperty(obj, key, convertType(content, clazz));
            } else if (getObj() instanceof Map) {
                if (newObject == null)
                    newObject = decodeSimpleType(content);
                // we allow arbitrary simple types as key in a map
                ((Map) getObj()).put(decodeSimpleType(key), newObject);
            } else {
                if (ReflectionUtil.hasProperty(obj, key))
                    if (newObject == null)
                        newObject = setProperty(obj, key, content);
                    else
                        ReflectionUtil.setProperty(obj, key, newObject);
                else {
                    try {
                        Field field = obj.getClass().getField(key);

                        if (field != null && ReflectionUtil.isPublicMemberField(field)) {
                            if (newObject == null)
                                newObject = convertType(content, field.getType());
                            field.set(obj, newObject);
                        }
                    } catch (Exception e) {
                        logger.warn("Can't set " + key + " field on " + obj + " with value " + newObject
                                + "\n" + e);
                    }

                }

            }
            if (getAnchorname() != null)
                markAnchor(newObject, getAnchorname());
            clear();
            key = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.parancoe.yaml.yaml.states.State#createObject()
     */
    @Override
    Object createObject() {
        Object ret = super.createObject();
        if (ret != null)
            return ret;
        else if (!(obj instanceof Map)) {
            Class type = null;
            if (decoder.getStManager().needsSpecialTreatment(obj)) {
                type = decoder.getStManager().getPropertyType(obj, key);
            } else if (ReflectionUtil.hasProperty(obj, key))
                type = ReflectionUtil.getPropertyDescriptor(obj, key).getPropertyType();
            else {
                try {
                    Field field = obj.getClass().getField(key);
                    type = field.getType();
                } catch (Exception e) {
                }
            }
            if (type != null)
                try {
                    return type.newInstance();
                } catch (Exception e) {
                    return null;
                }
        }
        return null;
    }

    @Override
    public void nextOnEvent(int event) {
        switch (event) {
        case MAP_SEPARATOR:
            break;
        case MAP_CLOSE:
            stack.pop();
            if (decoder.getStManager().needsSpecialTreatment(obj))
                obj = decoder.getStManager().getRealObject(obj);
            stack.peek().childCallback(obj);
            break;
        default:
            super.nextOnEvent(event);
        }

    }

    Collection createCollectionObjectFromExpectedType(Class type) {
        if (type.isArray()) {
            setDeclaredClassname(type.getComponentType().getName() + "[]");
            return new ArrayList();
        } else if (type.isInterface()) {
            if (type == Set.class) {
                return new HashSet();
            } else if (type == List.class) {
                return new Vector();
            } else if (type == Queue.class) {
                return new LinkedList();
            } else
                throw new YamlParserException("Unsupported collection type " + type.getCanonicalName());
        } else if (Collection.class.isAssignableFrom(type)) {
            try {
                return (Collection) type.newInstance();
            } catch (Exception e) {
                throw new YamlParserException("Can't instantiate type " + type.getCanonicalName());
            }
        } else
            throw new YamlParserException("Expecting a Collection type, got " + type.getCanonicalName());
    }

    @Override
    void openList(Stack<State> stack) {
        Object newObject = createObject();
        Class type = null;
        String componentTypeName = null;
        if (newObject == null) {
            if (ReflectionUtil.isArrayName(getClassname())) {
                newObject = new ArrayList();
                componentTypeName = ReflectionUtil.arrayComponentName(getClassname());
            } else if (getObj() instanceof Map) {
                // can't infer type if we have a Map
                newObject = new ArrayList();
            } else { // JavaBean
                PropertyDescriptor prop = ReflectionUtil.getPropertyDescriptor(getObj(), key);
                if (prop != null) {
                    type = prop.getPropertyType();
                    newObject = createCollectionObjectFromExpectedType(type);
                } else {
                    try {
                        type = getObj().getClass().getField(key).getType();
                        newObject = createCollectionObjectFromExpectedType(type);
                    } catch (Exception e) {
                        logger.info("Can't read from field or property " + key + " on " + getObj() + ".");
                        newObject = new ArrayList();
                    }

                }
            }
        }
        if (getAnchorname() != null)
            markAnchor(newObject, getAnchorname());
        State s = new ListState(aliasMap, stack, decoder, logger);
        if (!(newObject instanceof Collection))
            throw new YamlParserException(newObject
                    + " is not a Collection and so cannot be mapped from a sequence.");
        s.obj = newObject;
        if (componentTypeName != null || (type != null && type.isArray())) {
            s.setArrayComponentName(componentTypeName == null ? ReflectionUtil.className(type
                    .getComponentType()) : componentTypeName);
        }
        stack.push(s);
    }

    @Override
    public void childCallback(Object child) {
        if (decoder.getStManager().needsSpecialTreatment(obj)) {
            decoder.getStManager().setProperty(obj, key, child);
        } else if (obj instanceof Map) {
            ((Map) obj).put(key, child);
        } else if (ReflectionUtil.hasProperty(obj, key))
            ReflectionUtil.setProperty(obj, key, child);
        else
            try {
                obj.getClass().getField(key).set(obj, child);
            } catch (Exception e) {
                logger.warn("Can't set " + key + " field on " + obj + " with value " + child + "\n" + e);
            }
        clear();
        key = null;
    }

    Object setProperty(Object bean, String key, Object value) {
        PropertyDescriptor prop = ReflectionUtil.getPropertyDescriptor(bean, key);
        try {
            Object ret = convertType(value, prop.getPropertyType());
            prop.getWriteMethod().invoke(bean, new Object[] { ret });
            return ret;
        } catch (Exception e) {
            logger.warn("Can't set " + key + " property on " + bean + " with value " + value + "\n" + e);
            return null;
        }
    }

}
