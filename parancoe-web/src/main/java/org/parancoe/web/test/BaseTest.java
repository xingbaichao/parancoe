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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.parancoe.util.BaseConf;
import org.springframework.web.servlet.HandlerMapping;
import org.parancoe.test.DBTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    protected BaseConf conf;
    
    @Override
    protected String[] getConfigLocations() {
        String parancoeServlet=null;
        try {
                 parancoeServlet = new File("./src/main/webapp/WEB-INF/parancoe-servlet.xml").getCanonicalPath();
        } catch (IOException ex) {
                throw new RuntimeException("Unable to get parancoe-servlet", ex);
        }
        return new String[] {"classpath:org/parancoe/persistence/dao/generic/genericDao.xml","classpath:org/parancoe/web/parancoeBase.xml", "file:" + parancoeServlet, "classpath:spring-test.xml", "classpath*:parancoe-plugin.xml"};
    }
}