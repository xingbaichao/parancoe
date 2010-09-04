/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Web.
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
package org.parancoe.web;

public class WebUtils {

    public static String camelizeMethod(String rawMethodUrl) {
        
        if (rawMethodUrl == null || rawMethodUrl.length() == 0) {
            throw new IllegalArgumentException("rawMethodUrl cannot be null or empty");
        }
        
        rawMethodUrl = rawMethodUrl.toLowerCase();
        String[] methodParts = rawMethodUrl.split("_");
        
        StringBuffer sb = new StringBuffer(methodParts[0]);
        for(int i = 1; i < methodParts.length; i++) {
            sb.append(methodParts[i].substring(0,1).toUpperCase());
            sb.append(methodParts[i].substring(1,methodParts[i].length()));
        }
        
        return sb.toString();
    }
}
