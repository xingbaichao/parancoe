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

import java.util.List;
import javax.annotation.Resource;
import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.web.test.ControllerTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;

public class PeopleEditControllerTest extends ControllerTest {

    @Resource
    private PeopleEditController controller;
    @Resource
    private PersonDao personDao;

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Person.class};
    }

    public void testNotNull() {
        assertNotNull(controller);
        assertNotNull(personDao);
    }

    public void testSavePerson() {
        List<Person> people = personDao.findByLastName("Rossi");
        assertNotEmpty(people);
        Person person = people.get(0);
        BindingResult result = new BeanPropertyBindingResult(person, "person");
        SessionStatus status = new SimpleSessionStatus();
        String direction = controller.update(person, result, status);
        assertEquals("redirect:list.html", direction);
    }

    public void testEdit() {
        Model model = new ExtendedModelMap();
        controller.edit(1L, model);
    }
}