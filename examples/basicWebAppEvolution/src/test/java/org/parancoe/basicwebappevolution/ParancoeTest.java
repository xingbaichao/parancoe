package org.parancoe.basicwebappevolution;

import javax.annotation.Resource;

import org.parancoe.basicwebappevolution.blo.PersonBo;
import org.parancoe.basicwebappevolution.controllers.HomeController;
import org.parancoe.basicwebappevolution.controllers.PersonController;
import org.parancoe.plugins.italy.ComuneDao;
import org.parancoe.web.test.BaseTest;

public class ParancoeTest extends BaseTest {

    @Resource
    private ComuneDao comuneDao;
    @Resource
    private PersonBo personBo;
    @Resource
    private HomeController homeController;
    @Resource
    private PersonController personController;

    /** test everything has been loaded properly */
    public void testSanity() {
        assertNotNull(getApplicationContext().getBean("dataSource"));
        assertNotNull(getApplicationContext().getBean("transactionManager"));
        assertNotNull(getApplicationContext().getBean("conf"));
        assertNotNull(getApplicationContext().getBean("sessionFactory"));
        assertNotNull(getApplicationContext().getBean("messageSource"));
        assertNotNull(getApplicationContext().getBean(
                "hibernateGenericDaoInstrumentationAspect"));

        assertNotNull(getApplicationContext().getBean("viewResolver"));
        assertNotNull(getApplicationContext().getBean("exceptionResolver"));
        assertNotNull(getApplicationContext().getBean("multipartResolver"));
    }

    public void testItalyPlugin() {
        assertNotNull(comuneDao);
    }

    public void testBo() {
        assertNotNull(personBo);
    }

    public void testController() {
        assertNotNull(homeController);
        assertNotNull(personController);
    }
}
