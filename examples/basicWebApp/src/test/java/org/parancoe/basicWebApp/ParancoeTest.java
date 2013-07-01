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
package org.parancoe.basicWebApp;

import org.parancoe.basicWebApp.blo.PersonBo;
import org.parancoe.basicWebApp.controllers.HomeController;
import org.parancoe.basicWebApp.controllers.PeopleController;
import org.parancoe.plugins.italy.ComuneDao;
import org.parancoe.web.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class ParancoeTest extends BaseTest {

    /* test everything has been loaded properly */
    public void testSanity() {
        assertNotNull(getApplicationContext().getBean("dataSource"));
        assertNotNull(getApplicationContext().getBean("transactionManager"));
        assertNotNull(getApplicationContext().getBean("conf"));
        assertNotNull(getApplicationContext().getBean("sessionFactory"));
        assertNotNull(getApplicationContext().getBean("messageSource"));
        assertNotNull(getApplicationContext().getBean(
                "hibernateGenericDaoInstrumentationAspect"));

        assertNotNull(getApplicationContext().getBean("viewResolver"));
        assertNotNull(getApplicationContext().getBean("exceptionResolver"));
    }
    @Autowired
    ComuneDao comuneDao;

    public void testItalyPlugin() {
        assertNotNull(comuneDao);
    }
    @Autowired
    PersonBo personBo;

    public void testBo() {
        assertNotNull(personBo);
    }
    
    @Autowired
    HomeController homeController;
    
    @Autowired
    PeopleController peopleController;
    
    public void testController() {
        assertNotNull(homeController);
        assertNotNull(peopleController);
    }
}