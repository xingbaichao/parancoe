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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.parancoe.web.BaseMultiActionController;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Controller for secure porpouse.
 * @author Enrico Giurin
 *
 */
@Controller
@UrlMapping("/*.secure")
public  class SecureController {
    
    private static Logger logger = Logger.getLogger(SecureController.class);
    
    @RequestMapping
    public String login(HttpServletRequest req, HttpServletResponse res){
        return "acegilogin";
    }
    
    @RequestMapping
    public String accessDenied(HttpServletRequest req, HttpServletResponse res){
        return "accessDenied";
    }
    
    @RequestMapping
    public ModelAndView securityCheck(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
    
    @RequestMapping
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
    
        /* (non-Javadoc)
         * @see org.parancoe.web.BaseMultiActionController#getLogger()
         */    
}
