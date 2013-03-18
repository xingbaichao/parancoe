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
package org.parancoe.basicwebappevolution;

import javax.annotation.Resource;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import org.parancoe.basicwebappevolution.blo.PersonBo;
import org.parancoe.basicwebappevolution.controllers.HomeController;
import org.parancoe.basicwebappevolution.controllers.PersonController;
import org.parancoe.plugins.italy.ComuneDao;
import org.parancoe.web.test.junit4.AbstractWebTest;

/**
 * test everything has been loaded properly
 *
 * @author michele franzin <michele at franzin.net>
 */
public class ParancoeTest extends AbstractWebTest {

    @Resource
    private ComuneDao comuneDao;
    @Resource
    private PersonBo personBo;
    @Resource
    private HomeController homeController;
    @Resource
    private PersonController personController;

    @Test
    public void sanity() {
        assertThat(applicationContext.getBean("dataSource"), is(notNullValue()));
        assertThat(applicationContext.getBean("transactionManager"), is(notNullValue()));
        assertThat(applicationContext.getBean("conf"), is(notNullValue()));
        assertThat(applicationContext.getBean("sessionFactory"), is(notNullValue()));
        assertThat(applicationContext.getBean("messageSource"), is(notNullValue()));
        assertThat(applicationContext.getBean("hibernateGenericDaoInstrumentationAspect"), is(notNullValue()));
        assertThat(applicationContext.getBean("viewResolver"), is(notNullValue()));
        assertThat(applicationContext.getBean("exceptionResolver"), is(notNullValue()));
        assertThat(applicationContext.getBean("multipartResolver"), is(notNullValue()));
    }

    public void italyPlugin() {
        assertThat(comuneDao, is(notNullValue()));
    }

    public void bo() {
        assertThat(personBo, is(notNullValue()));
    }

    public void controllers() {
        assertThat(homeController, is(notNullValue()));
        assertThat(personController, is(notNullValue()));
    }
}
