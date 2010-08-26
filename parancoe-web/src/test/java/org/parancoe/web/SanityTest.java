package org.parancoe.web;

//import javax.annotation.Resource;
//import org.parancoe.util.BaseConf;
import org.parancoe.web.test.BaseTest;

public class SanityTest extends BaseTest {

//    @Resource
//    private BaseConf developmentConfiguration;

    public void testSanity() throws Exception {
//        assertNotNull(developmentConfiguration);
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                    "classpath:org/lambico/spring/dao/hibernate/genericDao.xml",
                    "classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml",
                    "classpath:org/parancoe/web/parancoeBase.xml",
                    "classpath:spring-test.xml"};
    }
}
