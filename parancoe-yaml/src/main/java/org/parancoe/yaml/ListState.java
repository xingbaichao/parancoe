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

import static org.parancoe.yaml.parser.YamlParser.LIST_CLOSE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;

import org.ho.util.Logger;

class ListState extends State {

    /**
     * @param aliasMap
     * @param stack
     */
    ListState(Map<String, Object> aliasMap, Stack<State> stack, YamlDecoder decoder, Logger logger) {
        super(aliasMap, stack, decoder, logger);
    }

    @Override
    public void nextOnContent(String type, String content) {
        Object toAdd;
        if ("alias".equals(type)) {
            String alias = content.substring(1);
            toAdd = aliasMap.get(alias);
        } else if (getClassname() != null) {
            toAdd = convertType(content, ReflectionUtil.classForName(getClassname()));
        } else
            toAdd = decodeSimpleType(content);// need to handle tags here
        if (getAnchorname() != null)
            markAnchor(toAdd, getAnchorname());
        add(toAdd);
        clear();
    }

    @Override
    public void nextOnEvent(int event) {
        switch (event) {
        case LIST_CLOSE:
            stack.pop();
            handleArray(stack.peek().getClassname());
            stack.peek().childCallback(getObj());
            break;
        default:
            super.nextOnEvent(event);
        }

    }

    void handleArray(String classname) {
        if (getObj() instanceof ArrayList && classname != null && ReflectionUtil.isArrayName(classname)) {
            ArrayList list = (ArrayList) getObj();
            String componentTypeName = ReflectionUtil.arrayComponentName(classname);
            Class componentType = ReflectionUtil.classForName(componentTypeName);
            if (componentType == null)
                throw new YamlParserException("class " + componentTypeName + " cannot be resolved.");
            Object array = Array.newInstance(componentType, list.size());
            for (int i = 0; i < Array.getLength(array); i++)
                Array.set(array, i, list.get(i));
            setObj(array);

        }
    }

    @SuppressWarnings("unchecked")
    void add(Object value) {
        if (getClassname() != null && value != null
                && !ReflectionUtil.isCastableFrom(value.getClass(), getClassname()))
            throw new YamlParserException(value + " of type " + value.getClass().getName()
                    + " cannot be cast into type " + getClassname());
        ((Collection) getObj()).add(value);
    }

    @Override
    public void childCallback(Object child) {
        add(child);
        clear();
    }

}
