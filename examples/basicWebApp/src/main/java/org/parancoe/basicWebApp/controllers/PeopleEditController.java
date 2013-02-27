/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic WebApp.
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
package org.parancoe.basicWebApp.controllers;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.basicWebApp.po.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/people/*.form")
@SessionAttributes("person")
public class PeopleEditController {

    @Resource
    private PersonDao personDao;
    @Resource
    private Validator validator;

    private final static Logger logger = LoggerFactory.getLogger(PeopleEditController.class);

    /* questo viene chiamato solo in caso di una post a people/edit.form */
    @RequestMapping
    protected String update(@ModelAttribute("person") @Valid Person person,
            BindingResult result, SessionStatus status) {
        try {
            if (result.hasErrors()) {
                logger.error("Result of validation has errors (" +
                        result.getAllErrors().toString() + ")");
                return "people/edit";
            } else {
                personDao.store(person);
                logger.info("Stored the person (" + person + ")");
                status.setComplete();
                return "redirect:list.html";
            }
        } catch (Exception e) {
            logger.error("Problema salvando Utente " + person, e);
            result.reject("error.generic");
            return "people/edit";
        }
    }

    /* Se passo il param id, carico la persona da db e mostro il form prepopolato */
    @RequestMapping
    protected String edit(@RequestParam(value="id", required=false) Long id, Model model) {
        Person p = null;
        if (id == null) {
            p = new Person();
        } else {
            p = personDao.read(id);
            if (p == null) {
                p = new Person();
            }
        }
        model.addAttribute("person", p);
        return "people/edit";
    }
}
