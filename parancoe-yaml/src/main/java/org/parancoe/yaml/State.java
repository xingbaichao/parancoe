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

import static org.parancoe.yaml.parser.YamlParser.LIST_OPEN;
import static org.parancoe.yaml.parser.YamlParser.MAP_OPEN;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.ho.util.Logger;

abstract class State {

    Logger logger;

    YamlDecoder decoder;

    Map<String, Object> aliasMap;

    Stack<State> stack;

    Object obj;

    String declaredClassname;

    String arrayComponentName;

    String anchorname;

    State(Map<String, Object> aliasMap, Stack<State> stack, YamlDecoder decoder, Logger logger) {
        this.aliasMap = aliasMap;
        this.stack = stack;
        this.decoder = decoder;
        this.logger = logger;
    }

    public void nextOnEvent(int event) {
        switch (event) {
        case MAP_OPEN:
            // create an new object and push it on the stack
            openMap(stack);
            break;
        case LIST_OPEN:
            openList(stack);
            break;
        default:
        }
    }

    public void nextOnContent(String type, String content) {
    }

    public void nextOnProperty(String type, String value) {
        if ("transfer".equals(type)) {
            if (getDeclaredClassname() == null && value.startsWith("!")) {
                setDeclaredClassname(ReflectionUtil.transfer2classname(value.substring(1), decoder
                        .getConfig()));
            }
        } else if ("anchor".equals(type)) {
            if (value.startsWith("&"))
                setAnchorname(value.substring(1));
        }
    }

    public abstract void childCallback(Object child);

    void clear() {
        setDeclaredClassname(null);
        setAnchorname(null);
    }

    Object createObject() {
        if (getClassname() == null)
            return null;
        else
            try {
                if (decoder.getStManager().needsSpecialTreatment(getClassname())) {
                    return decoder.getStManager().getHolder(getClassname());
                } else
                    return Class.forName(getClassname()).newInstance();
            } catch (Exception e) {
                return null;
            }
    }

    void openMap(Stack<State> stack) {
        Object obj = createObject();
        if (obj == null)
            obj = new HashMap();
        if (getAnchorname() != null)
            markAnchor(obj, getAnchorname());
        State s = new MapState(aliasMap, stack, decoder, logger);
        s.obj = obj;
        stack.push(s);
    }

    void openList(Stack<State> stack) {
        String parentCName = stack.peek().getClassname();
        Object newObject = createObject();
        if (newObject == null)
            newObject = new ArrayList();
        if (getAnchorname() != null)
            markAnchor(newObject, getAnchorname());
        State s = new ListState(aliasMap, stack, decoder, logger);
        if (!(newObject instanceof Collection))
            throw new YamlParserException(newObject
                    + " is not a Collection and so cannot be mapped from a sequence.");
        s.obj = newObject;
        stack.push(s);

        if (ReflectionUtil.isArrayName(parentCName)) {
            // if (ReflectionUtil.isArrayName(
            // ReflectionUtil.arrayComponentName(parentCName))){
            stack.peek().setArrayComponentName(ReflectionUtil.arrayComponentName(parentCName));
            // }
        }
    }

    void markAnchor(Object obj, String anchorname) {
        if (aliasMap.get(anchorname) == null)
            aliasMap.put(anchorname, obj);
    }

    static Object decodeSimpleType(String content) {
        if (content.startsWith("\"") && content.endsWith("\""))
            if (content.length() > 2)
                return content.substring(1, content.length() - 1);
            else
                return "";
        else if ("~".equals(content)) {
            return null;
        } else {
            try {
                return new Integer(content); // return integer
            } catch (NumberFormatException e) {
            }
            try {
                return new Double(content);
            } catch (NumberFormatException e) {
            }
            if ("true".equalsIgnoreCase(content) || "false".equalsIgnoreCase(content))
                return new Boolean(content); // return boolean
            else
                return content; // return String
        }
    }

    static Object convertType(Object value, Class type) {
        if ("~".equals(value))
            return null;
        else if (type == Character.class || type == Character.TYPE)
            return Character.valueOf(value.toString().charAt(0));
        else if (type == Integer.class || type == Integer.TYPE)
            return new Integer(value.toString());
        else if (type == String.class) {
            return (String) value;
        } else if (type == Long.class || type == Long.TYPE)
            return new Long(value.toString());
        else if (type == Short.class || type == Short.TYPE)
            return new Short(value.toString());
        else if (type == Byte.class || type == Byte.TYPE)
            return new Byte(value.toString());
        else if (type == Double.class || type == Double.TYPE)
            return new Double(value.toString());
        else if (type == Float.class || type == Float.TYPE)
            return new Float(value.toString());
        else if (type == Boolean.class || type == Boolean.TYPE)
            return Boolean.parseBoolean(value.toString());
        else if (type == BigDecimal.class)
            return new BigDecimal((String) value);
        else if (type == BigInteger.class)
            return new BigInteger((String) value);
        else if (type == File.class)
            return new File((String) value);
        else if (type == Date.class) {
            String s = (String) value;
            try {
                return new Date(Long.parseLong(s));
            } catch (NumberFormatException e) {
            }
            return new Date(s);
        } else if (type.isEnum()) {
            try {
                return type.getMethod("valueOf", new Class[] { String.class }).invoke(type,
                        new Object[] { value });
            } catch (Exception e) {
                throw new YamlParserException("Problem getting " + value + " value of enum type " + type);
            }
        } else
            // no special conversion, fall back to decodeSimpleType
            return decodeSimpleType((String) value);
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getClassname() {
        // this check handles ignore failed transfers and allows fall back to
        // array component type
        if (declaredClassname == null || ReflectionUtil.classForName(declaredClassname) == null)
            return arrayComponentName;
        else
            return declaredClassname;
    }

    public String getDeclaredClassname() {
        return declaredClassname;
    }

    public void setDeclaredClassname(String type) {
        this.declaredClassname = type;
    }

    public String getAnchorname() {
        return anchorname;
    }

    public void setAnchorname(String anchorname) {
        this.anchorname = anchorname;
    }

    public String getArrayComponentName() {
        return arrayComponentName;
    }

    public void setArrayComponentName(String arrayComponentName) {
        this.arrayComponentName = arrayComponentName;
    }

}
