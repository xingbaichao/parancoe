/**
 * 
 */
package org.parancoe.plugins.security;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * Mock secure interceptor for test purpose. 
 * @author egiurin
 *
 */
public class MockSecureInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger =
        Logger.getLogger(MockSecureInterceptor.class);
	
	private Filter parancoeDelegate;
	public Filter getParancoeDelegate() {
		return parancoeDelegate;
	}
	public void setParancoeDelegate(Filter parancoeDelegate) {
		this.parancoeDelegate = parancoeDelegate;
	}
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle() empty method called");
		return true;
	}

}
