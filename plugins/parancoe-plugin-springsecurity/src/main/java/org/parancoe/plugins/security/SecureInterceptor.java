/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.plugins.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor for secure features based on springsecurity framework.
 * @author Enrico Giurin
 *
 */
public class SecureInterceptor extends HandlerInterceptorAdapter {

    public static final String USERNAME_MDC_KEY = "psec_username";
    private static final String STRATEGY_CLASS_NAME =
            "org.parancoe.plugins.security.ParancoeSecurityContextHolderStrategy";
    private Filter delegate;
    private static final Logger logger =
            LoggerFactory.getLogger(SecureInterceptor.class);

    /**
     * Costructor. In the costructor strategy of SecurityContextHolder
     * has set.
     *
     */
    public SecureInterceptor() {

        SecurityContextHolder.setStrategyName(STRATEGY_CLASS_NAME);
        logger.info("SecureInterceptor set up");

    }

    /**
     * Delegates request to filter chain.
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
            Object handler) throws Exception {
        delegate.doFilter(req, res, new ParancoeFilterChain());
        populateMDC();
        req.getSession(false);
        if (res.isCommitted()) {
            logger.debug("Response is committed!");
            return false;
        }
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest req,
            HttpServletResponse res, Object handler, Exception e) throws
            Exception {
        cleanMDC();
    }

    /**
     * Put in the Mapped Diagnostic Context (MDC) the infos on the logged user.
     * So these infos can be showed in the log, using the %X{key} format sequence in the log layout.
     */
    private void populateMDC() {
        String username = "unknown";
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }
        MDC.put(USERNAME_MDC_KEY, username);
    }

    /**
     * Remove from the Mapped Diagnostic Context (MDC) the infos on the logged user.
     */
    private void cleanMDC() {
        MDC.remove(USERNAME_MDC_KEY);
    }

    /**
     * Inner class for basic implementation of FilterChain.
     *
     */
    private class ParancoeFilterChain implements FilterChain {

        public ParancoeFilterChain() {
            logger.debug("Instantiated");
        }

        public void doFilter(ServletRequest arg0, ServletResponse arg1) throws
                IOException,
                ServletException {
            // TODO Auto-generated method stub
        }
    }//end of inner class

    public Filter getDelegate() {
        return delegate;
    }

    public void setDelegate(Filter delegate) {
        this.delegate = delegate;
    }
}//end of  class

