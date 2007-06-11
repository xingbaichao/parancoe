/**
 *
 */
package org.parancoe.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.BeanFactoryUtils;

import org.apache.log4j.Logger;

/**
 * Interceptor for secure features based on acegi framework.
 * @author Enrico Giurin
 *
 */
public class SecureInterceptor implements HandlerInterceptor {
    
    private Filter delegate;
    private static final Logger logger = Logger.getLogger(SecureInterceptor.class);

    public Filter getDelegate() {
        return delegate;
    }

    public void setDelegate(Filter delegate) {
        this.delegate = delegate;
    }
        
    // this code has copied by corrisponding method
    //of FilterToBeanProxy
    //TODO improve code
    private void doInit() {
    }
    
    /**
     * Costruttore.
     *
     */
    public SecureInterceptor() {
        
    }
    
        /* (non-Javadoc)
         * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
         */
    public void afterCompletion(HttpServletRequest req,
            HttpServletResponse res, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub        
    }
    
        /* (non-Javadoc)
         * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
         */
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub        
    }
    
        /* (non-Javadoc)
         * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
         */
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
            Object handler) throws Exception {
        delegate.doFilter(req, res, new StupidFilterChain());
        return true;
        
    }
    
    
    private class StupidFilterChain implements FilterChain {
        
        public StupidFilterChain() {
            logger.debug("Instantiated");
        }
        
        public void doFilter(ServletRequest arg0, ServletResponse arg1) throws IOException, ServletException {
            // TODO Auto-generated method stub
        }
    }
    
}//end of outermost class


