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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.ho.util.BiDirectionalMap;

/**
 * YamlConfig represents a Jyaml configuration and contains all methods in
 * {@link YamlOperations} and is used as the entry point for Yaml operations
 * when multiple Jyaml configurations are used in the same application. See
 * {@link YamlOperations} for documentation on the Yaml entry point methods.
 * 
 */
public class YamlConfig implements YamlOperations, Cloneable {

    private static YamlConfig defaultConfig;

    static {

        try {
            defaultConfig = fromResource("jyaml.yml");
        } catch (Exception e) {
            try {
                defaultConfig = fromFile("jyaml.yml");
            } catch (Exception e1) {
                defaultConfig = new YamlConfig();
            }
        }

    }

    /**
     * The default Jyaml configuration
     * 
     * @return the default Jyaml configuration
     */
    public static YamlConfig getDefaultConfig() {
        // not cloning here speeds up performance but it will make the default
        // config globally modifiable
        return defaultConfig; // (YamlConfig)defaultConfig.clone();
    }

    String indentAmount = "  ";

    boolean minimalOutput = false;

    boolean suppressWarnings = false;

    BiDirectionalMap<String, String> transfers = null;

    /**
     * Returns the indentation amount used for one indentation level.
     * 
     * @return the amount of indentation used for one indentation level.
     */
    public String getIndentAmount() {
        return indentAmount;
    }

    /**
     * Sets indentation amount.
     * 
     * @param indentAmount
     *            must be a string consisting only of spaces.
     */
    public void setIndentAmount(String indentAmount) {
        this.indentAmount = indentAmount;
    }

    /**
     * Returns whether the minimal output option is set.
     * 
     * @return whether the minimal output option is set.
     */
    public boolean isMinimalOutput() {
        return minimalOutput;
    }

    /**
     * Sets the minimal output option.
     * 
     * @param minimalOutput
     *            true for on; false for off.
     */
    public void setMinimalOutput(boolean minimalOutput) {
        this.minimalOutput = minimalOutput;
    }

    /**
     * returns whether the suppress warnings option is on
     * 
     * @return whether the suppress warnings option is on
     */
    public boolean isSuppressWarnings() {
        return suppressWarnings;
    }

    /**
     * sets the suppress warnings option
     * 
     * @param suppressWarnings
     *            true for on; false for off.
     */
    public void setSuppressWarnings(boolean suppressWarnings) {
        this.suppressWarnings = suppressWarnings;
    }

    /**
     * returns the transfer-to-classname mapping for this configuration
     * 
     * @return a transfer-classname bi-directional map
     */
    public BiDirectionalMap<String, String> getTransfers() {
        return transfers;
    }

    /**
     * sets the transfer-to-classname mapping for this configuration
     * 
     * @param transferDictionary
     *            a transfer-classname bi-directional map
     */
    public void setTransfers(BiDirectionalMap<String, String> transferDictionary) {
        this.transfers = transferDictionary;
    }

    String transfer2classname(String transferName) {
        if (transfers != null && transfers.containsKey(transferName))
            return transfers.get(transferName);
        return transferName;
    }

    String classname2transfer(String classname) {
        if (transfers != null && transfers.getReverse().containsKey(classname))
            return transfers.getReverse().get(classname);
        return classname;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error();
        }
    }

    /**
     * Loads a YamlConfig from a Yaml configuration file
     * 
     * @param filename
     *            the name of the file to load
     * @return a YamlConfig object
     * @throws FileNotFoundException
     * @throws EOFException
     */
    public static YamlConfig fromFile(String filename) throws FileNotFoundException, EOFException {
        YamlDecoder dec = new YamlDecoder(filename, new YamlConfig());
        YamlConfig ret = dec.readObjectOfType(YamlConfig.class);
        dec.close();
        return ret;
    }

    /**
     * Loads a YamlConfig from a resource on the classpath
     * 
     * @param filename
     *            the name of the resource
     * @return a YamlConfig object
     * @throws EOFException
     */
    public static YamlConfig fromResource(String filename) throws EOFException {
        YamlDecoder dec = new YamlDecoder(YamlConfig.class.getClassLoader().getResourceAsStream(filename),
                new YamlConfig());
        YamlConfig ret = dec.readObjectOfType(YamlConfig.class);
        dec.close();
        return ret;
    }

    /*
     * ===================== Below methods implement YamlOperations
     * =====================
     */

    public Object load(InputStream in) {
        YamlDecoder dec = new YamlDecoder(in);
        dec.setConfig(this);
        Object ret = null;
        try {
            ret = dec.readObject();
        } catch (EOFException e) {
        }
        return ret;
    }

    public Object load(File file) throws FileNotFoundException {
        return load(new FileInputStream(file));
    }

    public Object load(String yamlText) {
        return load(new ByteArrayInputStream(yamlText.getBytes()));
    }

    public <T> T loadType(InputStream in, Class<T> clazz) {
        YamlDecoder dec = new YamlDecoder(in);
        dec.setConfig(this);
        T ret = null;
        try {
            ret = dec.readObjectOfType(clazz);
        } catch (EOFException e) {
        }
        return ret;
    }

    public <T> T loadType(File file, Class<T> clazz) throws FileNotFoundException {
        return loadType(new FileInputStream(file), clazz);
    }

    public <T> T loadType(String yamlText, Class<T> clazz) {
        return loadType(new ByteArrayInputStream(yamlText.getBytes()), clazz);
    }

    public YamlStream loadStream(InputStream in) {
        YamlDecoder dec = new YamlDecoder(in);
        dec.setConfig(this);
        return dec.asStream();
    }

    public YamlStream loadStream(File file) throws FileNotFoundException {
        return loadStream(new FileInputStream(file));
    }

    public YamlStream loadStream(String yamlText) {
        return loadStream(new ByteArrayInputStream(yamlText.getBytes()));
    }

    public <T> YamlStream<T> loadStreamOfType(InputStream in, Class<T> clazz) {
        YamlDecoder dec = new YamlDecoder(in);
        dec.setConfig(this);
        return dec.asStreamOfType(clazz);
    }

    public <T> YamlStream<T> loadStreamOfType(File file, Class<T> clazz) throws FileNotFoundException {
        return loadStreamOfType(new FileInputStream(file), clazz);
    }

    public <T> YamlStream<T> loadStreamOfType(String yamlText, Class<T> clazz) throws FileNotFoundException {
        return loadStreamOfType(new ByteArrayInputStream(yamlText.getBytes()), clazz);
    }

    public void dump(Object obj, File file) throws FileNotFoundException {
        YamlEncoder enc = new YamlEncoder(new FileOutputStream(file));
        enc.setConfig(this);
        enc.writeObject(obj);
        enc.close();
    }

    public void dump(Object obj, File file, boolean minimalOutput) throws FileNotFoundException {
        YamlEncoder enc = new YamlEncoder(new FileOutputStream(file));
        enc.setConfig(this);
        enc.setMinimalOutput(minimalOutput);
        enc.writeObject(obj);
        enc.close();
    }

    public void dumpStream(Iterator iterator, File file, boolean minimalOutput) throws FileNotFoundException {
        YamlEncoder enc = new YamlEncoder(new FileOutputStream(file));
        enc.setConfig(this);
        enc.setMinimalOutput(minimalOutput);
        while (iterator.hasNext())
            enc.writeObject(iterator.next());
        enc.close();
    }

    public void dumpStream(Iterator iterator, File file) throws FileNotFoundException {
        YamlEncoder enc = new YamlEncoder(new FileOutputStream(file));
        enc.setConfig(this);
        while (iterator.hasNext())
            enc.writeObject(iterator.next());
        enc.close();
    }

    public String dump(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YamlEncoder enc = new YamlEncoder(out);
        enc.setConfig(this);
        enc.writeObject(obj);
        enc.close();
        return new String(out.toByteArray());
    }

    public String dump(Object obj, boolean minimalOutput) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YamlEncoder enc = new YamlEncoder(out);
        enc.setConfig(this);
        enc.setMinimalOutput(minimalOutput);
        enc.writeObject(obj);
        enc.close();
        return new String(out.toByteArray());
    }

    public String dumpStream(Iterator iterator) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YamlEncoder enc = new YamlEncoder(out);
        enc.setConfig(this);
        while (iterator.hasNext())
            enc.writeObject(iterator.next());
        enc.close();
        return new String(out.toByteArray());
    }

    public String dumpStream(Iterator iterator, boolean minimalOutput) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YamlEncoder enc = new YamlEncoder(out);
        enc.setConfig(this);
        enc.setMinimalOutput(minimalOutput);
        while (iterator.hasNext())
            enc.writeObject(iterator.next());
        enc.close();
        return new String(out.toByteArray());
    }

}
