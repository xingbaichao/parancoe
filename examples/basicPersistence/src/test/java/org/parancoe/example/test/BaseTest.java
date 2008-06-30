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
package org.parancoe.example.test;

import java.util.Map;

import org.parancoe.example.po.Book;
import org.parancoe.example.po.Person;
import org.parancoe.persistence.dao.DaoUtils;
import org.parancoe.test.DBTest;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.context.ApplicationContext;

/**
 * A base class for basic persistence example tests.
 * 
 * @author lucio
 */
public abstract class BaseTest extends DBTest {

    @SuppressWarnings(value = "unchecked")
    protected ApplicationContext getTestContext() {
        BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance(
                "beanRefFactory_test.xml");
        BeanFactoryReference bf = bfl.useBeanFactory("org.parancoe.example");
        ApplicationContext lctx = (ApplicationContext) bf.getFactory();
        Map daoMap = (Map) lctx.getBean("daoMap");
        Map ldaos = DaoUtils.getDaos(lctx);
        daoMap.putAll(ldaos);
        return lctx;
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Person.class};
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                    "classpath:org/parancoe/persistence/dao/generic/genericDao.xml",
                    "classpath:database_test.xml",
                    "classpath:dao.xml",
                    "classpath:applicationContext.xml"
                };
    }
}
