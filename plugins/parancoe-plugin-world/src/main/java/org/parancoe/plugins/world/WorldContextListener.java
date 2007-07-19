package org.parancoe.plugins.world;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author lucio.benfante@jugpadova.it
 */
public class WorldContextListener extends ContextLoaderListener {

    private static final Logger log = Logger.getLogger(WorldContextListener.class);

    private ServletContext servletContext;

    private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent evt) {
      log.info("WorldPlugin contextInitialized"); 
    }
}
