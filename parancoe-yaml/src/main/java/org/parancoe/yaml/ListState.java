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
