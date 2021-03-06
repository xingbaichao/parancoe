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
package org.parancoe.web.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Questa classe fa da proxy per tutti gli interceptor configurati nei plugins.
 * Parancoe chiama questo interceptor che poi fa il dispatch a tutti gli
 * interceptor dei plugin.
 *
 * @author paolo.dona@seesaw.it
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class PluginInterceptor  implements HandlerInterceptor, ApplicationContextAware {
    private ApplicationContext ctx;

    private static final Logger logger = LoggerFactory.getLogger(PluginInterceptor.class);

    public PluginInterceptor() {
        logger.info("PluginInterceptor set up");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        logger.debug("PluginInterceptor.preHandle()");
        return new PluginHelper(ctx).invokePluginPreHandle(req, res, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        new PluginHelper(ctx).invokePluginPostHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) throws Exception {
        new PluginHelper(ctx).invokeAfterCompletion(request, response, handler, exception); // To
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }


}
