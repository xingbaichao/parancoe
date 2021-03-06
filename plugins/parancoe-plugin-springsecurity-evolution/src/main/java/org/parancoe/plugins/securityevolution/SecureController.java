/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security Evolution.
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
package org.parancoe.plugins.securityevolution;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for secure purpose.
 * @author Enrico Giurin
 *
 */
@Controller
@RequestMapping("/*.html")

public  class SecureController  {
    private static Logger logger = LoggerFactory.getLogger(SecureController.class);


    @Resource(name="loginView")
    private String loginView;

    @Resource(name="accessDeniedView")
    private String accessDeniedView;

    @RequestMapping
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res){
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("Forwarding view to "+loginView);
    	}
        return new ModelAndView(loginView);
    }
    @RequestMapping
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res){
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("Forwarding view to "+accessDeniedView);
    	}
        return new ModelAndView(accessDeniedView);
    }

    @RequestMapping
    public ModelAndView securityCheck(HttpServletRequest req,
            HttpServletResponse res) {
        return null;
    }

    /*@RequestMapping
    public ModelAndView j_spring_security_check(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
    @RequestMapping
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res){
        return null;
    }*/


}
