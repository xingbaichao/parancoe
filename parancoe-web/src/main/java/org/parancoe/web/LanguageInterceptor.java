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
package org.parancoe.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @author paolo.dona@seesaw.it
 */
public class LanguageInterceptor extends HandlerInterceptorAdapter {
    public static final Logger logger = Logger.getLogger(LanguageInterceptor.class);


    public LanguageInterceptor() {
        logger.debug("LanguageInterceptor set up");
    }

    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                             Object object) throws Exception {
        RequestContext rc = new RequestContext(req);
        req.setAttribute("requestContext",rc);
        req.setAttribute("lang", rc.getLocale().getLanguage());
        logger.debug("LanguageInterceptor.preHandle()");
        return true;
    }
}
