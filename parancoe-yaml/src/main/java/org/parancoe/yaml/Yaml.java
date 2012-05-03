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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Yaml is the front end to the Jyaml library. It contains the most commonly
 * used and easy to use methods for accessing Yaml. For most usages, this is the
 * only class one needs to use. All of these methods also exist in
 * YamlOperations and are documented there. See {@link YamlOperations}.
 * 
 */
public class Yaml {

    public static Object load(InputStream in) {
        return YamlConfig.getDefaultConfig().load(in);
    }

    public static Object load(File file) throws FileNotFoundException {
        return YamlConfig.getDefaultConfig().load(file);
    }

    public static Object load(String yamlText) {
        return YamlConfig.getDefaultConfig().load(yamlText);
    }

    public static <T> T loadType(InputStream in, Class<T> clazz) throws FileNotFoundException {
        return YamlConfig.getDefaultConfig().loadType(in, clazz);
    }

    public static <T> T loadType(File file, Class<T> clazz) throws FileNotFoundException {
        return YamlConfig.getDefaultConfig().loadType(file, clazz);
    }

    public static <T> T loadType(String yamlText, Class<T> clazz) {
        return YamlConfig.getDefaultConfig().loadType(yamlText, clazz);
    }

    public static YamlStream loadStream(InputStream in) {
        return YamlConfig.getDefaultConfig().loadStream(in);
    }

    public static YamlStream loadStream(File file) throws FileNotFoundException {
        return YamlConfig.getDefaultConfig().loadStream(file);
    }

    public static YamlStream loadStream(String yamlText) {
        return YamlConfig.getDefaultConfig().loadStream(yamlText);
    }

    public static <T> YamlStream<T> loadStreamOfType(InputStream in, Class<T> clazz) {
        return YamlConfig.getDefaultConfig().loadStreamOfType(in, clazz);
    }

    public static <T> YamlStream<T> loadStreamOfType(File file, Class<T> clazz) throws FileNotFoundException {
        return YamlConfig.getDefaultConfig().loadStreamOfType(file, clazz);
    }

    public static <T> YamlStream<T> loadStreamOfType(String yamlText, Class<T> clazz)
            throws FileNotFoundException {
        return YamlConfig.getDefaultConfig().loadStreamOfType(yamlText, clazz);
    }

    public static void dump(Object obj, File file) throws FileNotFoundException {
        YamlConfig.getDefaultConfig().dump(obj, file);
    }

    public static void dump(Object obj, File file, boolean minimalOutput) throws FileNotFoundException {
        YamlConfig.getDefaultConfig().dump(obj, file, minimalOutput);
    }

    public static void dumpStream(Iterator iterator, File file, boolean minimalOutput)
            throws FileNotFoundException {
        YamlConfig.getDefaultConfig().dumpStream(iterator, file, minimalOutput);
    }

    public static void dumpSream(Iterator iterator, File file) throws FileNotFoundException {
        YamlConfig.getDefaultConfig().dumpStream(iterator, file);
    }

    public static String dump(Object obj) {
        return YamlConfig.getDefaultConfig().dump(obj);
    }

    public static String dump(Object obj, boolean minimalOutput) {
        return YamlConfig.getDefaultConfig().dump(obj, minimalOutput);
    }

    public static String dumpStream(Iterator iterator) {
        return YamlConfig.getDefaultConfig().dumpStream(iterator);
    }

    public static String dumpStream(Iterator iterator, boolean minimalOutput) {
        return YamlConfig.getDefaultConfig().dumpStream(iterator, minimalOutput);
    }
}
