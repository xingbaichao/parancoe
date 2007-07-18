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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.parancoe.persistence.dao.DaoUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Holder for the default Spring context used for the tests.
 * The returned context is initialized only one time.
 * 
 * @author lucio
 */
@SuppressWarnings("unchecked")
public class DefaultTestContextHolder {
    private static final WebApplicationContext context;
    
    static {
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
        // Setup the daomap (done in a contextlistener out of tests)
        Map daoMap = (Map) ctx.getBean("daoMap");
        Map daos = DaoUtils.getDaos(ctx);
        daoMap.putAll(daos);
        context = ctx;
    }
    
    private DefaultTestContextHolder() {
    }

    public static WebApplicationContext getTestContext() {
        return context;
    }
}
