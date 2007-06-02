package org.parancoe.plugins.sample;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author paolo.dona@seesaw.it
 */
public class SampleContextListener extends ContextLoaderListener {

    private static final Logger log = Logger.getLogger(SampleContextListener.class);

    private ServletContext servletContext;

    private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent evt) {
      log.info("SamplePlugin contextInitialized"); 
    }
}
