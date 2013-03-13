/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Web.
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
package org.parancoe.web.test;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.lambico.test.spring.hibernate.DBTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * E' la classe base per tutti i test.
 * Carica all'avvio tutti i bean configurati in modo da renderli
 * disponibili ai test specifici senza la necessità di tirarli su di volta in volta con
 * getCtx().getBean()
 */
public abstract class BaseTest extends DBTest {

    private static final Logger log = Logger.getLogger(BaseTest.class);
//    @Autowired
//    protected BaseConf conf;

    @Override
    protected String[] getConfigLocations() {
        String parancoeServlet = null;
        String applicationContext = null;
        try {
            parancoeServlet =
                    new File("./src/main/webapp/WEB-INF/parancoe-servlet.xml").getCanonicalPath();
            applicationContext =
                    new File("./src/main/webapp/WEB-INF/applicationContext.xml").getCanonicalPath();
        } catch (IOException ex) {
            throw new RuntimeException("Unable to get parancoe-servlet", ex);
        }
        return new String[]{
                    "classpath:org/lambico/spring/dao/hibernate/genericDao.xml",
                    "classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml",
                    "classpath:org/parancoe/core/applicationContextBase.xml",
                    "classpath:org/parancoe/web/parancoeBase.xml", "classpath:database-test.xml",
                    "file:" + applicationContext, "file:" + parancoeServlet,
                    "classpath*:applicationContext-plugin.xml", "classpath*:parancoe-plugin.xml", "classpath:spring-test.xml"
                };
    }

    @Override
    protected ConfigurableApplicationContext createApplicationContext(
            String[] locations) {
        FileSystemResourceLoader rl = new FileSystemResourceLoader();
        MockServletContext servletContext = new MockServletContext(rl);
        servletContext.setMinorVersion(4);
        servletContext.registerContext("/test", servletContext);
        servletContext.setServletContextName("/test");
        servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
                arrayToString(locations));
        ContextLoader loader = new ContextLoader();
        WebApplicationContext context = loader.initWebApplicationContext(servletContext);
        return (ConfigurableApplicationContext) context;
    }

    private String arrayToString(String[] locations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < locations.length - 1; i++) {
            sb.append(locations[i]).append(',');
        }
        if (locations.length > 0) {
            sb.append(locations[locations.length - 1]);
        }
        return sb.toString();
    }
    
}