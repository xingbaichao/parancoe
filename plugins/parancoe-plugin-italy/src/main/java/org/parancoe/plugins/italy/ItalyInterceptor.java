package org.parancoe.plugins.italy;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author paolo.dona@seesaw.it
 */
public class ItalyInterceptor extends HandlerInterceptorAdapter {
  public static final Logger logger = Logger.getLogger(ItalyInterceptor.class);

  public ItalyInterceptor() {
    logger.info("ItalyInterceptor set up");
  }

  public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                           Object object) throws Exception {
    logger.debug("ItalyInterceptor preHandle");
    return true;
  }
}