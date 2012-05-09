#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;

/**
 * A sample context listener for the plugin
 *
 * @author paolo.dona@seesaw.it
 * @author lucio.benfante@jugpadova.it
 */
@Component("${artifactId}SampleContextListener")
public class SampleContextListener extends ContextLoaderListener {

    private static final Logger log = Logger.getLogger(SampleContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("${artifactId} contextInitialized");
    }
}
