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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parancoe.basicWebApp.Blos;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.web.BaseFormController;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

@UrlMapping("/people/edit.form")
public abstract class PeopleEditController extends BaseFormController {

    private final static Logger logger = Logger.getLogger(PeopleEditController.class);

    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
    }

    /* questo viene chiamato solo in caso di una post a people/edit.form */
    protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
        Person person = null;
        try {
            person = (Person) command;
            dao().getPersonDao().createOrUpdate(person);
            return onSubmit(command, errors); // restituisce succesView
        } catch (Exception e) {
            logger.error("Problema salvando Utente " + person, e);
                     
            errors.reject("error.generic");
             return showForm(req, res, errors);
        }
    }

    /* Se passo il param id, carico la persona da db e mostro il form prepopolato */
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Person p = dao().getPersonDao().read(id);
            if (p==null) throw new Exception();
            return p;
        } catch(Exception e){
            return new Person();
        }
    }

    public Logger getLogger() {
        return logger;
    }
    protected abstract Daos dao();
    protected abstract Blos blo();
}
