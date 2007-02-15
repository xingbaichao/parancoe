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
package org.parancoe.web;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResolver implements HandlerExceptionResolver {
    private static final Logger log = Logger.getLogger(ExceptionResolver.class);

    /**
     * sends unhandled exceptions to genericError.jsp
     */
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res,
            Object object, Exception e) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("messageCode", "error.generic");
        params.put("exception", e);
        return new ModelAndView("genericError", params);
    }
}
