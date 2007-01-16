package org.parancoe.web;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LanguageInterceptor extends HandlerInterceptorAdapter {
    public static final Logger logger = Logger.getLogger(LanguageInterceptor.class);


    public LanguageInterceptor() {
        logger.info("LanguageInterceptor set up");
    }

    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                             Object object) throws Exception {
        RequestContext rc = new RequestContext(req);
        req.setAttribute("requestContext",rc);
        req.setAttribute("lang", rc.getLocale());
        return true;
    }
}
