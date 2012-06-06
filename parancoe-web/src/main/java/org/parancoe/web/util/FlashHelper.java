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
package org.parancoe.web.util;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class FlashHelper {
    public static String DEFAULT_NOTICE_TYPE = "notice";
    public static String DEFAULT_ERROR_TYPE = "error";
    public static String DEFAULT_FLASH_ATTRIBUTE = "flash";
    
    public static void setError(HttpServletRequest req, String code) {
        doSet(req, code, DEFAULT_ERROR_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void setNotice(HttpServletRequest req, String code) {
        doSet(req, code, DEFAULT_NOTICE_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void doSet(HttpServletRequest req, String code, String type, String attribute) {
        Map<String, String> flash = (Map<String, String>)req.getAttribute(attribute);
        if (flash == null) flash = new HashMap<String, String>();
        if (type == null) {
            type = DEFAULT_ERROR_TYPE;
        }
        if (attribute == null) {
            attribute = DEFAULT_FLASH_ATTRIBUTE;
        }
        flash.put(type, code);
        req.setAttribute(attribute, flash);
    }

    public static void setRedirectError(HttpServletRequest req, String code) {
        doSetRedirect(req, code, DEFAULT_ERROR_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void setRedirectNotice(HttpServletRequest req, String code) {
        doSetRedirect(req, code, DEFAULT_NOTICE_TYPE, DEFAULT_FLASH_ATTRIBUTE);
    }
    
    public static void doSetRedirect(HttpServletRequest req, String code, String type, String attribute) {
        Map<String, String> flash = (Map<String, String>)req.getSession().getAttribute(attribute);
        if (flash == null) flash = new HashMap<String, String>();
        if (type == null) {
            type = DEFAULT_ERROR_TYPE;
        }
        if (attribute == null) {
            attribute = DEFAULT_FLASH_ATTRIBUTE;
        }
        flash.put(type, code);
        req.getSession().setAttribute(attribute, flash);
    }
    
}

