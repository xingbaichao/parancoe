#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * A sample interceptor for the plugin.
 * 
 * @author paolo.dona@seesaw.it
 * @author lucio.benfante@seesaw.it
 */
@Component("${artifactId}SampleInterceptor")
public class SampleInterceptor extends HandlerInterceptorAdapter {
  public static final Logger logger = Logger.getLogger(SampleInterceptor.class);

  public SampleInterceptor() {
    logger.info("SampleInterceptor set up");
  }

  public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                           Object object) throws Exception {
    logger.info("${artifactId} Plugin interceptor preHandle");
    return true;
  }
}