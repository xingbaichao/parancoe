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
package it.jugpadova.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.parancoe.web.BaseMultiActionController;
import it.jugpadova.Daos;
import it.jugpadova.Blos;

/**
 * 
 */
public abstract class HomeController extends BaseMultiActionController {
    private static final Logger logger = Logger.getLogger(HomeController.class);

    /**
     * 
     */
    public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        params.put("something", new Object());
        return new ModelAndView("welcome", params);
    }

    /**
     * Login action
     */
    public ModelAndView acegilogin(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        return new ModelAndView("acegilogin", params);
    }

    /**
     * Access denied
     */
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        return new ModelAndView("accessDenied", params);
    }
    

    /**
     * 
     */
    public ModelAndView pageThatRaiseAnException(HttpServletRequest req, HttpServletResponse res) {
        try {
            throw new RuntimeException("BOOOM!!!");
        } catch (Exception e) {
            return genericError(e);
        }
    }

    /**
     * 
     */
    public ModelAndView pageThatRaiseAnUnHandledException(HttpServletRequest req, HttpServletResponse res){
        if (1 == 1){
            throw new RuntimeException("UNHANDLED BOOM!!!");
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    public Logger getLogger() {return logger;}

    /**
     * You don't have to implement this. 
     *
     * @return The provider of DAOs
     */
    protected abstract Daos dao();

    /**
     * You don't have to implement this. 
     * 
     * @return The provider of business logic objects
     */
    protected abstract Blos blo();
}
