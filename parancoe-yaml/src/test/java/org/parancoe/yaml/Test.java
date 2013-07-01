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

import java.io.Reader;
import java.io.StringReader;

import org.parancoe.yaml.parser.ParserEvent;
import org.parancoe.yaml.parser.YamlParser;

public class Test {
    public static void main(String[] args) throws Exception {
        // String yamlText = "--- |\n" +
        // "hello world\n" +
        // "how are you?\n";
        // String yamlText = "--- \n" +
        // "string: |\n" +
        // " Some comments\n" +
        // " here and there\n" +
        // "two: three";

        // String yamlText =
        // "---\n" +
        // "date: 11/30/2005\n" +
        // "receipts:\n" +
        // " - store: la madeleine\n" +
        // " category: \"dining out: lunch\"\n" +
        // " total: 13.14\n" +
        // "---\n" +
        // "date: 12/1/2005\n" +
        // "receipts:\n" +
        // " - store: sushi me\n" +
        // " category: \"dining out: lunch\"\n" +
        // " total: 5.60";

        // String yamlText =
        // "---\n" +
        // "date: 11/30/2005\n" +
        // "receipts:\n" +
        // " - store: la madeleine\n" +
        // " category: dining out: lunch\n" +
        // " total: 13.14\n" +
        // "---\n" +
        // "date: 12/1/2005\n" +
        // "receipts:\n" +
        // " - store: sushi me\n" +
        // " category: dining out: lunch\n" +
        // " total: 5.60";

        // String yamlText =
        // "--- \n" +
        // "date: 11/29/2005\n" +
        // "receipts:\n" +
        // " - store: ken stanton music\n" +
        // " category: entertainment\n" +
        // " description: saxophone repair\n" +
        // " total: 382.00\n" +
        // " - store: walmart\n" +
        // " category: groceries\n" +
        // " total: 14.26";
        // System.out.println(yamlText);

        String yamlText = "- [1, 1, 0]\n" + "- [1, -1, 2]";

        // File f = new File("test.yaml");
        // Reader reader = new BufferedReader(new FileReader(f));
        Reader reader = new StringReader(yamlText);
        ParserEvent handler = new TestYamlParserEvent();
        YamlParser y = new YamlParser(reader, handler);
        y.parse();
        reader.close();

    }

}