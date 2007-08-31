package org.parancoe.plugins.world;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lucio.benfante@jugpadova.it
 */
public class WorldInterceptor extends HandlerInterceptorAdapter {

    public static final Logger logger = Logger.getLogger(WorldInterceptor.class);

    public WorldInterceptor() {
        logger.info("WorldInterceptor set up");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object object) throws Exception {
        logger.debug("WorldPlugin interceptor preHandle");
        return true;
    }
}