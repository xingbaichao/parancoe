// Copyright 2006 The Parancoe Team
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

import java.util.Map;
import org.apache.log4j.Logger;
import org.parancoe.persistence.dao.DaoUtils;
import org.parancoe.util.MemoryAppender;
import org.parancoe.util.BaseConf;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;
/**
 * Context Listener of parancoe web application.
 * @author Paolo Dona
 *
 */
/**
 * A context listener for initializing the Spring context.
 *
 * @author <a href="mailto:paolo.dona@jugpadova.it">Paolo Donà</a>
 * @author <a href="mailto:lucio.benfante@jugpadova.it">Lucio Benfante</a>
 * @version $Revision$
 */
public class ContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ContextListener.class);
    private ServletContext servletContext;

    public void contextInitialized(ServletContextEvent evt) {
        this.servletContext = evt.getServletContext();

        MemoryAppender.clean();

        log.info("loading custom Spring WebApplicationContext");
        loadApplicationContext();

        log.info("### Starting up Parancoe in " + BaseConf.getEnv() + " mode.");
    }

    /**
     * load the ApplicationContext mixing the base parancoe
     * files and the application specific configuration
     */
    private void loadApplicationContext() {
        String[] config = new String[4];
        // generici
        config[0] = "classpath:org/parancoe/persistence/dao/generic/genericDao.xml";
        config[1] = "classpath:org/parancoe/web/parancoeBase.xml";
        // application specific
        config[2] = "WEB-INF/parancoe-servlet.xml";
        config[3] = "WEB-INF/database.xml";

        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.setConfigLocations(config);
        ctx.refresh();
        
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ctx);
        
        populateDaoMap(ctx);
    }

    public void contextDestroyed(ServletContextEvent evt) {
        log.info("### Shutting down Parancoe in " + BaseConf.getEnv() + " mode.");
    }

    /**
     * Populate the "daoMap" bean with the DAOs defined in the context.
     */
    private void populateDaoMap(XmlWebApplicationContext ctx) {
        Map daoMap = (Map)ctx.getBean("daoMap");
        Map daos = DaoUtils.getDaos(ctx);
        daoMap.putAll(daos);
    }
}
