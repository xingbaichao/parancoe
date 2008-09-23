package org.parancoe.web;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.parancoe.util.BaseConf;
import org.parancoe.web.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends BaseTest {

    @Autowired
    private BaseConf developmentConfiguration;
    
    
    public void testSanity() throws Exception {
        assertNotNull(developmentConfiguration);
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{ };
    }
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:org/parancoe/persistence/dao/generic/genericDao.xml",
                             "classpath:org/parancoe/persistence/applicationContextBase.xml",
                             "classpath:org/parancoe/web/parancoeBase.xml", 
                             "classpath:spring-test.xml"};
    }
    
}
