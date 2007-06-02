package org.parancoe.web.plugin;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Questa classe fa da proxy per tutti gli interceptor configurati nei plugins.
 * Parancoe chiama questo interceptor che poi fa il dispatch a tutti gli interceptor dei plugin.
 * @author paolo.dona@seesaw.it
 */
public class PluginInterceptor extends HandlerInterceptorAdapter implements ApplicationContextAware {
    private ApplicationContext ctx;
    private static final Logger logger = Logger.getLogger(PluginInterceptor.class);

    public PluginInterceptor() {
        logger.info("PluginInterceptor set up");
    }

    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
                             Object handler) throws Exception {
        logger.debug("PluginInterceptor.preHandle()");
        return new PluginHelper(ctx).invokePluginPreHandle(req,res,handler);
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       new PluginHelper(ctx).invokePluginPostHandle(request,response,handler,modelAndView);
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        new PluginHelper(ctx).invokeAfterCompletion(request, response, handler, exception);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
