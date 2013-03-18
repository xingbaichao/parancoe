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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.ho.util.Logger;
import org.parancoe.yaml.parser.YamlParserEvent;

class JYamlParserEvent extends YamlParserEvent {

    Stack<State> stack = new Stack<State>();

    Map<String, Object> aliasMap = new HashMap<String, Object>();

    public JYamlParserEvent(Logger logger, YamlDecoder decoder) {
        stack.push(new NoneState(aliasMap, stack, decoder, logger));
    }

    public JYamlParserEvent(Class clazz, Logger logger, YamlDecoder decoder) {
        this(logger, decoder);
        String classname = ReflectionUtil.className(clazz);
        stack.peek().setDeclaredClassname(classname);
        if (decoder.getStManager().needsSpecialTreatment(classname))
            stack.peek().setObj(decoder.getStManager().getHolder(classname));
        else if (!clazz.isArray() && !ReflectionUtil.isSimpleType(clazz))
            try {
                stack.peek().setObj(clazz.newInstance());
            } catch (Exception e) {
                throw new YamlParserException("Can't instantiate object of type " + clazz.getName());
            }

    }

    @Override
    public void content(String a, String b) {
        stack.peek().nextOnContent(a, b);
    }

    @Override
    public void error(Exception e, int line) {
        throw new YamlParserException("Error near line " + line + ": " + e);
    }

    @Override
    public void event(int c) {
        stack.peek().nextOnEvent(c);
    }

    @Override
    public void property(String a, String b) {
        stack.peek().nextOnProperty(a, b);
    }

    public Object getBean() {
        return stack.peek().getObj();
    }

}
