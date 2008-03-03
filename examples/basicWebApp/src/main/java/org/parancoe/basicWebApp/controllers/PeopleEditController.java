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
import org.hibernate.Transaction;
import org.parancoe.basicWebApp.Blos;
import org.parancoe.basicWebApp.Conf;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.web.BaseFormController;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@UrlMapping("/people/*.form")
public class PeopleEditController {
    
    @Autowired
    @Qualifier("conf")
    Conf conf;
    
    @Autowired
    private Daos daos;
    
    @Autowired(required=false)
    private Blos blos;
    
    @Autowired
    Validator validator;
    
    private final static Logger logger = Logger.getLogger(PeopleEditController.class);

    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
    }

    /* questo viene chiamato solo in caso di una post a people/edit.form */
    @Transactional
    @RequestMapping
    protected String update(@ModelAttribute Person person, BindingResult result, SessionStatus status) throws Exception {
        try {
            validator.validate(person,result);
            if (result.hasErrors()) {
                return "people/edit"; 
            }
            else {
                daos.getPersonDao().store(person);
                return "redirect:people/list.html"; // restituisce succesView
            }
        } catch (Exception e) {
            logger.error("Problema salvando Utente " + person, e);
            result.reject("error.generic");
            return "people/edit";
        }
    }

    /* Se passo il param id, carico la persona da db e mostro il form prepopolato */
    @Transactional(readOnly = true)
    @RequestMapping
    protected String edit(@RequestParam("id") Long id, Model model) throws Exception {
        try {
            Person p = daos.getPersonDao().read(id);
            if (p==null) throw new Exception();
            model.addAttribute(p);
        } catch(Exception e){
            model.addAttribute(new Person());
        }
        return "people/edit";
    }

    public Logger getLogger() {
        return logger;
    }
   
}
