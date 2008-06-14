package org.parancoe.web;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.parancoe.web.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends BaseTest {

    @Autowired
    @Qualifier("developmentConfiguration")
    private PropertiesConfiguration developmentConfiguration;
    @Autowired
    @Qualifier("testConfiguration")
    private PropertiesConfiguration testConfiguration;
    @Autowired
    @Qualifier("productionConfiguration")
    private PropertiesConfiguration productionConfiguration;
    
    public void testSanity() throws Exception {
        assertNotNull(developmentConfiguration);
        assertNotNull(testConfiguration);
        assertNotNull(productionConfiguration);
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{ };
    }
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:org/parancoe/persistence/dao/generic/genericDao.xml",
                "classpath:org/parancoe/web/parancoeBase.xml", "classpath:spring-test.xml"};
    }
    
}
