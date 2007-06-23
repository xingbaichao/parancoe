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
