// Copyright 2006-2007 The Parancoe Team
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

package org.parancoe.web.util;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class FlashHelper {
    
    public static void setError(HttpServletRequest req, String code) {
        Map<String, String> flash = (Map<String, String>)req.getAttribute("flash");
        if (flash == null) flash = new HashMap<String, String>();
        flash.put("error", code);
        req.setAttribute("flash", flash);
    }
    
    public static void setNotice(HttpServletRequest req, String code) {
        Map<String, String> flash = (Map<String, String>)req.getAttribute("flash");
        if (flash == null) flash = new HashMap<String, String>();
        flash.put("notice", code);
        req.setAttribute("flash", flash);
    }
    
    public static void setRedirectError(HttpServletRequest req, String code) {
        Map<String, String> flash = (Map<String, String>)req.getSession().getAttribute("flash");
        if (flash == null) flash = new HashMap<String, String>();
        flash.put("error", code);
        req.getSession().setAttribute("flash", flash);
    }
    
    public static void setRedirectNotice(HttpServletRequest req, String code) {
        Map<String, String> flash = (Map<String, String>)req.getSession().getAttribute("flash");
        if (flash == null) flash = new HashMap<String, String>();
        flash.put("notice", code);
        req.getSession().setAttribute("flash", flash);
    }
    
}

