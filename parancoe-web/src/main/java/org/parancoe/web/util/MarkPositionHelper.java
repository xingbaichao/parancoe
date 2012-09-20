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

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.parancoe.web.MarkPositionInterceptor;
import org.parancoe.web.tag.MarkPositionTag;
import org.parancoe.web.tag.MarkedPositionTag;

/**
 * Utility methods for marking positions during navigations.
 *
 * @see MarkPositionInterceptor
 * @see MarkPositionTag
 * @see MarkedPositionTag
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class MarkPositionHelper {

    public static String getMarkedPosition(HttpSession session, String pathId) {
        String backUrl = (String) session.getAttribute(getSessionAttributeKey(pathId));
        return StringUtils.isNotBlank(backUrl) ? backUrl : "";
    }

    public static String getSessionAttributeKey(String pathId) {
        return MarkPositionTag.PREFIX + pathId;
    }

    public static void markPosition(HttpServletRequest request, String pathId, String url) {        
        request.getSession().setAttribute(MarkPositionHelper.getSessionAttributeKey(pathId), url);
    }
    
    public static void markPosition(HttpSession session, String pathId, String url) {        
        session.setAttribute(MarkPositionHelper.getSessionAttributeKey(pathId), url);
    }
    
    public static void unmarkPosition(HttpSession session, String pathId) {
        session.removeAttribute(MarkPositionHelper.getSessionAttributeKey(pathId));
    }
    
    public static void unmarkAllPositions(HttpSession session) {
        Enumeration attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = (String)attributeNames.nextElement();
            if (name.startsWith(MarkPositionTag.PREFIX)) {
                session.removeAttribute(name);
            }
        }
    }
    
}
