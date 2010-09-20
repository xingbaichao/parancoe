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

import org.parancoe.yaml.parser.ParserEvent;
import org.parancoe.yaml.parser.YamlParser;

public class TestYamlParserEvent implements ParserEvent {
    int level = 0;

    public void event(String a) {
    }

    public void error(Exception e, int line) {
        throw new RuntimeException("Error near line " + line + ": " + e);
    }

    public void event(int c) {
        switch (c) {
        case YamlParser.MAP_CLOSE:
        case YamlParser.LIST_CLOSE:

            level--;
            break;
        }

        System.out.println(sp() + (char) c);

        switch (c) {
        case YamlParser.LIST_OPEN:
        case YamlParser.MAP_OPEN:

            level++;
            break;
        }
    }

    public void content(String a, String b) {
        System.out.println(sp() + a + " = <" + b + ">");
    }

    public void property(String a, String b) {
        System.out.println(sp() + "( " + a + " = <" + b + "> )");
    }

    private String sp() {
        if (level < 0)
            return "";
        char[] cs = new char[level * 4];
        for (int i = 0; i < cs.length; i++)
            cs[i] = ' ';
        return new String(cs);
    }
}
