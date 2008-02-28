/*
 *  Copyright 2008 jacopo.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.parancoe.web.controller;


import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.parancoe.web.WebUtils;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.UrlPathHelper;

/**
 *
 * @author jacopo
 */
public class DefaultMethodNameResolver implements MethodNameResolver {
    
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private static final Logger logger = Logger.getLogger(DefaultMethodNameResolver.class);

    public String getHandlerMethodName(HttpServletRequest request) throws NoSuchRequestHandlingMethodException {
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);
        logger.debug("URL: "+lookupPath);
        String[] splitPath = lookupPath.split("/");
        String lastPart = splitPath[splitPath.length - 1];
        logger.debug("URL LAST: "+lastPart);
        String rowMethodUrl = lastPart.split("\\.")[0];
        
        return WebUtils.camelizeMethod(rowMethodUrl);
    }

}
