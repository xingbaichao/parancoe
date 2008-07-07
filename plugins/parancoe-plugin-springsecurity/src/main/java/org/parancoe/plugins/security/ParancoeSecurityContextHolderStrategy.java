// Copyright 2006-2007 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.parancoe.plugins.security;

import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolderStrategy;
import org.springframework.security.context.SecurityContextImpl;
import org.apache.log4j.Logger;


/**
 * A Parancoe <code>ThreadLocal</code>-based implementation of {@link
 * org.springframework.security.context.SecurityContextHolderStrategy}.
 * This class is similar to ThreadLocalSecurityContextHolderStrategy but
 * the body of the method clearContext() is empty. *
 * @author Enrico Giurin
 *
 * @see java.lang.ThreadLocal
 * @see org.springframework.security.context.HttpSessionContextIntegrationFilter
 */
public class ParancoeSecurityContextHolderStrategy implements SecurityContextHolderStrategy {
	private static Logger logger = Logger.getLogger(ParancoeSecurityContextHolderStrategy.class);


    private static ThreadLocal contextHolder = new ThreadLocal();
    
    //~ Methods ========================================================================================================

    public void clearContext() {
        logger.debug("This method has void implementation!");
    }

    public SecurityContext getContext() {
        if (contextHolder.get() == null) {
            contextHolder.set(new SecurityContextImpl());
        }

        return (SecurityContext) contextHolder.get();
    }

    public void setContext(SecurityContext context) {
       
        contextHolder.set(context);
    }
}
