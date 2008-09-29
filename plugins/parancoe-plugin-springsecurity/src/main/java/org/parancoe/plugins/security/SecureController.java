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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for secure porpouse.
 * @author Enrico Giurin
 *
 */
@Controller
@RequestMapping("/security/*.html")
public  class SecureController {
    private static Logger logger = Logger.getLogger(SecureController.class);
    
    @RequestMapping
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res){
        return new ModelAndView("acegilogin");
    }
    @RequestMapping
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res){
        return new ModelAndView("accessDenied");
    }
    
    @RequestMapping
    public ModelAndView j_spring_security_check(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
    @RequestMapping
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
    
    
}