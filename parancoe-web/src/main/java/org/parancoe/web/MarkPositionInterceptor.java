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
import org.parancoe.web.util.MarkPositionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class MarkPositionInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(MarkPositionInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        log.debug("Executing postHandle.");
        String backUrl = request.getParameter("_back");
        if (backUrl != null) {
            String pathId = request.getParameter("_pathId");
            MarkPositionHelper.markPosition(request, pathId, backUrl);
            log.debug("Marked position ({}) for pathId ({}).", new Object[]{backUrl, pathId});
        }
    }
}
