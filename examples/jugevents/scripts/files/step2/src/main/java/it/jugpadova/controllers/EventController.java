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

import org.parancoe.web.BaseMultiActionController;
import it.jugpadova.Daos;
import it.jugpadova.Blos;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class EventController extends BaseMultiActionController {
    private static Logger logger = Logger.getLogger(EventController.class);
    
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res){
    	// *DEMO TODO* Complete the controller method
    }
        
    public Logger getLogger() {
        return logger;
    }
    
    protected abstract Daos dao();
    protected abstract Blos blo();
}
