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

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.parancoe.test.EnhancedTestCase;
import org.parancoe.util.BaseConf;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.ArrayList;

/**
 * E' la classe base per tutti i test.
 * Carica all'avvio tutti i bean configurati in modo da renderli
 * disponibili ai test specifici senza la necessità di tirarli su di volta in volta con
 * getCtx().getBean()
 */
public abstract class BaseTest extends EnhancedTestCase {

    private static final Logger log = Logger.getLogger(BaseTest.class);

    protected static WebApplicationContext ctx = getTestContext();

    protected BaseConf conf;

    protected DataSource dataSource;

    protected HandlerMapping handlerMapping;

    public void setUp() throws Exception {
        super.setUp();
        conf = (BaseConf) ctx.getBean("conf");
        dataSource = (DataSource) ctx.getBean("dataSource");
        handlerMapping = (HandlerMapping) ctx.getBean("handlerMapping");
    }

    /**
     * Creo un webApplicationContext finto
     *
     * @return
     */
    private static WebApplicationContext getTestContext() {
        List<String> config = new ArrayList<String>();
        //generici
        config.add("classpath:org/parancoe/persistence/dao/generic/genericDao.xml");
        config.add("classpath:org/parancoe/web/parancoeBase.xml");

        // application specific
        config.add("src/main/webapp/WEB-INF/parancoe-servlet.xml");
        config.add("src/main/webapp/WEB-INF/database.xml");

        // test specific
        config.add("src/test/resources/spring-test.xml");

         // load plugin configurations
        config.add("classpath*:parancoe-plugin.xml");

        FileSystemResourceLoader rl = new FileSystemResourceLoader();
        ServletContext servletContext = new MockServletContext(rl);
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setServletContext(servletContext);

        ctx.setConfigLocations(config.toArray(new String[config.size()]));
        ctx.refresh();
        return ctx;
    }
}