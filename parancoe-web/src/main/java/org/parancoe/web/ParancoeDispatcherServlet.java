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

import javax.servlet.ServletException;
import org.parancoe.web.plugin.PluginHelper;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class ParancoeDispatcherServlet extends DispatcherServlet {

    @Override
    protected void initFrameworkServlet() throws ServletException, BeansException {
        super.initFrameworkServlet();
        PluginHelper helper = new PluginHelper(getWebApplicationContext());
        helper.initWebPlugins();
    }

    @Override
    protected WebApplicationContext createWebApplicationContext(WebApplicationContext ctx) throws BeansException {
        if (getContextConfigLocation() == null) {
            setContextConfigLocation("classpath:org/parancoe/web/parancoeBase.xml,classpath*:parancoe-plugin.xml,WEB-INF/"+getServletName()+"-servlet.xml");
        }
        return super.createWebApplicationContext(ctx);
    }

    
    
}
