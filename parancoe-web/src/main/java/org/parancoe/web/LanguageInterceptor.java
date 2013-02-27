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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

/**
 * @author paolo.dona@seesaw.it
 */
public class LanguageInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(LanguageInterceptor.class);

    public LanguageInterceptor() {
        logger.debug("LanguageInterceptor set up");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
            Object object) throws Exception {
        RequestContext rc = new RequestContext(req);
        req.setAttribute("requestContext", rc);
        req.setAttribute("lang", rc.getLocale().getLanguage());
        logger.debug("LanguageInterceptor.preHandle()");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
            ModelAndView arg3) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
            Exception arg3) throws Exception {
    }
}
