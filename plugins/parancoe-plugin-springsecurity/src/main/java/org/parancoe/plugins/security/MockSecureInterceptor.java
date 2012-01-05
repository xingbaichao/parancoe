/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
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
