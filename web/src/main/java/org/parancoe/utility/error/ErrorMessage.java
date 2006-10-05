// Copyright 2006 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.utility.error;

import java.io.Serializable;

/**
 * <p>ErrorMessage</p>
 * <p>A container for error messages, used during bean validation and in other controller operations.
 * <br>To specify a message without using the resource bundle key, set to [false] <code>resource</code> attribute.
 * <br>The default behaviour is to search for the key</p>
 * @author
 * @version 1.0
 */
public class ErrorMessage implements Serializable {

    /** The error key in the ResurceBundle */
    protected String key = null;
    /** The parametrized values in ResurceBundle error messages */
    protected Object[] values = null;
    /** If true, key is the key in the ResourceBundle, otherwise key is the message string itself */
    protected boolean resource = true;

    /**
     * <p>Constructor for an error not parametrized</p>
     *  @param key the ResourceBundle key
     */
    public ErrorMessage(String key) {
        this(key, null);
    }//[c] ErrorMessage

    /**
     * <p>Construct an error with only one parametrized value</p>
     * @param key the ResourceBundle key
     * @param value the parametrized value
     */
    public ErrorMessage(String key, Object value) {
        this(key, new Object[] {value});
    }//[c] ErrorMessage

    /**
     * <p>Construct an error with an array of parametrized values</p>
     * @param key the ResourceBundle key
     * @param values an array containing the parametrized values
     */
    public ErrorMessage(String key, Object[] values) {
        this.key = key;
        this.values = values;
        this.resource = true;
    }//[c] ErrorMessage

    /**
     * <p>Construct an error in which the key could be the message itself</p>
     * @param key the key of the error or the message itself
     * @param resource if <code>true</code> then <code>key</code> is a key in the ResurceBundle otherwise key is the error message itself
     */
    public ErrorMessage(String key, boolean resource) {
        this.key = key;
        this.resource = resource;
    }//[c] ErrorMessage

    /**
     * <p>The error message key</p>
     * @return the error message key
     */
    public String getKey() {
        return (this.key);
    }//[m] getKey

    /**
     * <p>An array of values for pararmetrized error messages</p>
     * @return An array of values for pararmetrized error messages
     */
    public Object[] getValues() {
        return (this.values);
    }//[m] getValues

    /**
     * <p>Tells if key is an error message or the ResourceBundle key </p>
     * @param if <code>true</code> then <code>key</code> is a key of the ResurceBundle otherwise is the error message string.
     */
    public boolean isResource() {
        return (this.resource);
    }//[m] isResource

}//{c} ErrorMessage
