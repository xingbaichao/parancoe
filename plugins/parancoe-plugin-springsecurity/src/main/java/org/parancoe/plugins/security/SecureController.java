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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Controller for secure porpouse.
 * @author Enrico Giurin
 *
 */
public  class SecureController extends MultiActionController {
    private static Logger logger = Logger.getLogger(SecureController.class);
    
    
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res){
        return new ModelAndView("acegilogin");
    }
    
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res){
        return new ModelAndView("accessDenied");
    }
    
    
    public ModelAndView securityCheck(HttpServletRequest req, HttpServletResponse res){
        return null;
    }

    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res){
        return null;
    }
    
        /* (non-Javadoc)
         * @see org.parancoe.web.BaseMultiActionController#getLogger()
         */
   
    
   
    
    
    
}
