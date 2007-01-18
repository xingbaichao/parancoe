package org.parancoe.web;

import org.apache.log4j.Logger;
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
    }

    public void contextDestroyed(ServletContextEvent evt) {
        log.info("### Shutting down Parancoe in " + BaseConf.getEnv() + " mode.");
    }
}
