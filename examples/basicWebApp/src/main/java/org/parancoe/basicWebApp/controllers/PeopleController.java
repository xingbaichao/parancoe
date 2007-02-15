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

import java.text.ParseException;
import org.parancoe.web.BaseMultiActionController;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.Blos;
import org.parancoe.basicWebApp.po.Person;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

public abstract class PeopleController extends BaseMultiActionController {
    private static Logger logger = Logger.getLogger(PeopleController.class);

    public ModelAndView list(HttpServletRequest req, HttpServletResponse res){
        Map params = new HashMap();
        params.put("people", dao().getPersonDao().findAll());
        return new ModelAndView("people/list", params);
    }

    public ModelAndView populate(HttpServletRequest req, HttpServletResponse res){
        Map params = new HashMap();
        try {
            blo().person.populateArchive();
        } catch (ParseException ex) {
            return genericError(ex);
        }
        params.put("people", dao().getPersonDao().findAll());
        return new ModelAndView("people/list", params);
    }

    public ModelAndView show(HttpServletRequest req, HttpServletResponse res){
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            logger.debug("got id "+id);
            Map params = new HashMap();
            Person p = dao().getPersonDao().read(id);

            params.put("person",p);
            return new ModelAndView("people/show", params);
        } catch(Exception e){
            return genericError("Persona non trovata");
        }
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    protected abstract Daos dao();
    protected abstract Blos blo();
}
