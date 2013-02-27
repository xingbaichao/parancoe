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

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.lambico.spring.dao.DaoUtils;
import org.parancoe.util.BaseConf;
import org.parancoe.util.MemoryAppender;
import org.parancoe.web.plugin.PluginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * A context listener for initializing the Spring context.
 *
 * @author paolo.dona@seesaw.it
 * @author <a href="mailto:lucio.benfante@jugpadova.it">Lucio Benfante</a>
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 * @version $Revision$
 */
public class ContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);
    protected ServletContext servletContext;
    protected XmlWebApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        try {
            this.servletContext = evt.getServletContext();

            MemoryAppender.clean();

            log.info("loading custom Spring WebApplicationContext");
            loadApplicationContext();
            PluginHelper helper = new PluginHelper(applicationContext);
            helper.initApplicationContextPlugins(evt); // deve essere DOPO loadApplicationContext()
            helper.invokePluginContextInitialized(evt);
            log.info("### Starting up Parancoe in " + BaseConf.getEnv() + " mode.");
        } catch (Exception e) {
            log.error("Error in base ContextListener.contextInitialized", e);
        }
    }


    /**
     * load the ApplicationContext mixing the base parancoe
     * files and the application specific configuration
     */
    protected void loadApplicationContext() {
        List<String> config = new ArrayList<String>();
        // generici
        config.add("classpath:org/lambico/spring/dao/hibernate/genericDao.xml");
        config.add("classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml");
        config.add("classpath:org/parancoe/core/applicationContextBase.xml");
        config.add("WEB-INF/database.xml");
        // load all plugin configurations at once
        config.add("classpath*:applicationContext-plugin.xml");
        config.add("WEB-INF/applicationContext.xml");
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.setConfigLocations(config.toArray(new String[config.size()]));
        ctx.refresh();

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ctx);
        applicationContext = ctx;

        populateDaoMap(ctx);
    }

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
        new PluginHelper(applicationContext).invokePluginContextDestroyed(evt);
        log.info("### Shutting down Parancoe in " + BaseConf.getEnv() + " mode.");
    }

    /**
     * Populate the "daoMap" bean with the DAOs defined in the context.
     */
    @SuppressWarnings("unchecked")
    protected void populateDaoMap(XmlWebApplicationContext ctx) {
        Map daoMap = (Map) ctx.getBean("daoMap");
        Map daos = DaoUtils.getDaos(ctx);
        daoMap.putAll(daos);
    }

}
