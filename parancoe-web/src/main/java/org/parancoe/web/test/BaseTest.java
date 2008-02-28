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
package org.parancoe.web.test;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.parancoe.util.BaseConf;
import org.springframework.web.servlet.HandlerMapping;
import org.parancoe.test.DBTest;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * E' la classe base per tutti i test.
 * Carica all'avvio tutti i bean configurati in modo da renderli
 * disponibili ai test specifici senza la necessità di tirarli su di volta in volta con
 * getCtx().getBean()
 */
public abstract class BaseTest extends DBTest {

    private static final Logger log = Logger.getLogger(BaseTest.class);

    protected BaseConf conf;

    protected DataSource dataSource;

    protected HandlerMapping handlerMapping;
    
    protected SessionFactory sessionFactory = null;

    public void setUp() throws Exception {
        super.setUp();
        conf = (BaseConf) ctx.getBean("conf");
        dataSource = (DataSource) ctx.getBean("dataSource");
        handlerMapping = (HandlerMapping) ctx.getBean("handlerMapping");
        sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session)); 
    }

    /**
     * Creo un webApplicationContext finto
     *
     * @return
     */
    @Override
    protected ApplicationContext getTestContext() {
        return DefaultTestContextHolder.getTestContext();
    }
    
    protected void tearDown() throws Exception {
       SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
       SessionFactoryUtils.closeSession(sessionHolder.getSession());        
       super.tearDown();
    }
}