/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Configuration.
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
package org.parancoe.plugin.configuration;

import java.util.ArrayList;
import java.util.List;
import org.parancoe.web.ContextListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Context listener for the test web application.
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
public class TestContextListener extends ContextListener {

    @Override
    protected void loadApplicationContext() {
        List<String> config = new ArrayList<String>();
        // generici
        config.add("classpath:org/lambico/spring/dao/hibernate/genericDao.xml");
        config.add("classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml");
        config.add("classpath:org/parancoe/web/parancoeBase.xml");
        config.add("classpath:database-test-server.xml");
        config.add("classpath:applicationContext-test.xml");
        config.add("classpath*:parancoe-plugin.xml");
        config.add("classpath*:applicationContext-plugin.xml");
        config.add("classpath:org/parancoe/plugin/configuration/restful-services.xml");
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.setConfigLocations(config.toArray(new String[config.size()]));
        ctx.refresh();

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ctx);
        applicationContext = ctx;

        populateDaoMap(ctx);
    }


}
