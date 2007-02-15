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
package org.parancoe.basicWebApp.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.parancoe.web.BaseMultiActionController;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.Blos;

public abstract class HomeController extends BaseMultiActionController {
    private static final Logger logger = Logger.getLogger(HomeController.class);

    // the simplest possible action
    public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        params.put("something", new Object());
        return new ModelAndView("welcome", params);
    }

    public ModelAndView page1(HttpServletRequest req, HttpServletResponse res){
        return new ModelAndView("page1", null);
    }

    // how to handle exceptions
    public ModelAndView pageThatRaiseAnException(HttpServletRequest req, HttpServletResponse res) {
        try {
            throw new RuntimeException("BOOOM!!!");
        } catch (Exception e) {
            return genericError(e);
        }
    }

    public ModelAndView pageThatRaiseAnUnHandledException(HttpServletRequest req, HttpServletResponse res){
        if (1 == 1){
            throw new RuntimeException("UNHANDLED BOOM!!!");
        }
        return null;
    }

    public Logger getLogger() {return logger;}
    protected abstract Daos dao();
    protected abstract Blos blo();
}
