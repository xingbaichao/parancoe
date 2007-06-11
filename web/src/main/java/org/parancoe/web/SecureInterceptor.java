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

import org.acegisecurity.intercept.web.FilterInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interceptor for secure features based on acegi framework.
 * @author Enrico Giurin
 *
 */
public class SecureInterceptor implements HandlerInterceptor, ApplicationContextAware {
	
	
	private ApplicationContext context = null;
	
	String targetClassString = null;
	private Filter delegate;
	private boolean initialized = false;
	private static final Log logger = LogFactory.getLog(SecureInterceptor.class);
	
	// this code has copied by corrisponding method
	//of FilterToBeanProxy
	//TODO improve code
	private void doInit()
	{
		Class targetClass;
        String beanName = null;
        String targetClassString = "org.acegisecurity.util.FilterChainProxy";     
       
            
            try {
                targetClass = Thread.currentThread().getContextClassLoader().loadClass(targetClassString);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Class of type " + targetClassString + " not found in classloader");
            }

            Map beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, targetClass, true, true);

            if (beans.size() == 0) {
                throw new RuntimeException("Bean context must contain at least one bean of type " + targetClassString);
            }

            beanName = (String) beans.keySet().iterator().next();
        

        Object object = context.getBean(beanName);

        if (!(object instanceof Filter)) {
            throw new RuntimeException("Bean '" + beanName + "' does not implement javax.servlet.Filter");
        }

        delegate = (Filter) object;

        

        // Set initialized to true at the end of the synchronized method, so
        // that invocations of doFilter() before this method has completed will not
        // cause NullPointerException
        initialized = true;
	}
	
	/**
	 * Costruttore.
	 *
	 */
	public SecureInterceptor()
	{
		
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
		if(!initialized)
			doInit();
		delegate.doFilter(req, res, new StupidFilterChain());
		return true;
		
	}

	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		this.context = ac;
		
	}
	
	
	private class StupidFilterChain implements FilterChain
	{

		
		public StupidFilterChain()
		{
			System.out.println("Istanziato");
		}
		public void doFilter(ServletRequest arg0, ServletResponse arg1) throws IOException, ServletException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	

}//end of outermost class


