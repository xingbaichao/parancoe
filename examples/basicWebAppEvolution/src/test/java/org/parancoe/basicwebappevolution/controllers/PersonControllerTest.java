/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic WebApp Evolution.
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
package org.parancoe.basicwebappevolution.controllers;

import java.util.List;
import org.junit.Test;
import org.lambico.test.spring.hibernate.junit4.FixtureSet;
import org.parancoe.basicwebappevolution.dao.PersonDao;
import org.parancoe.basicwebappevolution.po.Person;
import org.parancoe.web.test.junit4.AbstractControllerTest;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.ModelAndViewAssert.*;

/**
 * test person controller
 *
 * @author michele franzin <michele at franzin.net>
 */
@FixtureSet(modelClasses = {Person.class})
public class PersonControllerTest extends AbstractControllerTest {

    @Autowired
    private PersonController controller;
    @Autowired
    private HandlerAdapter handler;
    @Autowired
    private PersonDao personDao;

    @Test
    public void sanity() {
        assertThat(controller, is(notNullValue()));
        assertThat(personDao, is(notNullValue()));
        assertThat(handler.supports(controller), is(true));
    }

    @Test
    public void editAndSavePerson() throws Exception {
        // Retrieve a person for testing
        List<Person> people = personDao.findByLastName("Rossi");
        assertThat(people, is(not(empty())));
        Person person = people.get(0);

        // Test edit (showing form)
        request.setMethod("GET");
        request.setRequestURI("/person/" + person.getId() + "/edit");
        ModelAndView mv = handler.handle(request, response, controller);
        assertViewName(mv, "person/edit");
        assertModelAttributeAvailable(mv, "person");
        assertThat(request.getSession().getAttribute("person"), is(notNullValue()));

        // Test store (posting form)
        resetRequestAndResponse();
        request.setMethod("POST");
        request.setRequestURI("/person/" + person.getId());
        request.addParameter("firstName", "Marione");
        request.addParameter("lastName", "Rossi");
        request.addParameter("birthDate", "25/04/1971");
        mv = handler.handle(request, response, controller);
        assertViewName(mv, "redirect:");

        // Check on the DB
        people = personDao.findByLastName("Rossi");
        assertThat(people, hasSize(1));
        person = people.get(0);
        assertThat(person.getFirstName(), equalTo("Marione"));
    }

    @Test
    public void delete() throws Exception {
        // Retrieve a person for testing
        List<Person> people = personDao.findAll();
        int initialSize = people.size();
        assertThat(people, is(not(empty())));
        Person person = people.get(0);

        // Test edit (showing form)
        request.setMethod("DELETE");
        request.setRequestURI("/person/" + person.getId());
        ModelAndView mv = handler.handle(request, response, controller);
        assertViewName(mv, "redirect:");

        // Check on the DB
        people = personDao.findByLastName("Rossi");
        assertThat(people, hasSize(initialSize - 1));
    }
}
