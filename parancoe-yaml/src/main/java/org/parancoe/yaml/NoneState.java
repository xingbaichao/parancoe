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

import java.util.Map;
import java.util.Stack;

import org.ho.util.Logger;

/**
 * NoneState is a special state that never gets put on the Stack. You will only
 * ever be in the NoneState at the beginning of a org.parancoe.yaml.parser when
 * you don't know whether the top level element if a map or a list yet.
 * 
 * @author Toby.Ho
 * 
 */
class NoneState extends State {

    NoneState(Map<String, Object> aliasMap, Stack<State> stack, YamlDecoder decoder, Logger logger) {
        super(aliasMap, stack, decoder, logger);
    }

    @Override
    public void childCallback(Object child) {
        this.obj = child;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.parancoe.yaml.yaml.states.State#nextOnContent(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void nextOnContent(String type, String content) {
        if (getClassname() != null)
            obj = convertType(content, ReflectionUtil.classForName(getClassname()));
        else
            obj = decodeSimpleType(content);
    }

}
