package org.parancoe.plugins.security;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author Lucio Benfante
 */
public class PopulateData extends ContextLoaderListener {

    private static final Logger log = Logger.getLogger(PopulateData.class);

    private ServletContext servletContext;

    private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent evt) {
      log.info("SecurityPlugin contextInitialized"); 
    }
}
