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

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.parancoe.persistence.dao.DaoUtils;
import org.parancoe.persistence.security.AuthoritiesBO;
import org.parancoe.persistence.security.UserBO;
import org.parancoe.util.BaseConf;
import org.parancoe.util.MemoryAppender;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
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
        String[] config = new String[5];
        // generici
        config[0] = "classpath:org/parancoe/persistence/dao/generic/genericDao.xml";
        config[1] = "classpath:org/parancoe/web/parancoeBase.xml";
        // application specific
        config[2] = "WEB-INF/parancoe-servlet.xml";
        config[3] = "WEB-INF/database.xml";
        //acegi-configuration
        config[4] = "WEB-INF/parancoe-acegi-security.xml";

        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.setConfigLocations(config);
        ctx.refresh();
        
        
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ctx);
        
        populateDaoMap(ctx);
        setSecurity(ctx);
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
    /**
     * Populate and create if don't exist tables User and Authorities.
     * @param ctx
     */
    private void setSecurity(XmlWebApplicationContext ctx)
    {
    	 UserBO userBO = (UserBO)ctx.getBean("userBO");
         AuthoritiesBO authoritiesBO= (AuthoritiesBO)ctx.getBean("authoritiesBO");
         // Popoulating the database
         userBO.populateTable();
         authoritiesBO.populateTable();
    }
}
