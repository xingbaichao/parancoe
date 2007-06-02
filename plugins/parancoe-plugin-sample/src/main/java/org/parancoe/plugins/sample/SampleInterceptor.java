package org.parancoe.plugins.sample;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author paolo.dona@seesaw.it
 */
public class SampleInterceptor extends HandlerInterceptorAdapter {
  public static final Logger logger = Logger.getLogger(SampleInterceptor.class);

  public SampleInterceptor() {
    logger.info("ItalyInterceptor set up");
  }

  public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                           Object object) throws Exception {
    logger.info("SamplePlugin interceptor preHandle");
    return true;
  }
}