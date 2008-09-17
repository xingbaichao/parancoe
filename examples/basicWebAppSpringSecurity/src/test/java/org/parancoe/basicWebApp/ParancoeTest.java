package org.parancoe.basicWebApp;

import org.parancoe.basicWebApp.blo.PersonBo;
import org.parancoe.basicWebApp.controllers.HomeController;
import org.parancoe.basicWebApp.controllers.PeopleController;
import org.parancoe.plugins.italy.ComuneDao;
import org.parancoe.web.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class ParancoeTest extends BaseTest {

    /* test everything has been loaded properly */
    public void testSanity() {
        assertNotNull(getApplicationContext().getBean("dataSource"));
        assertNotNull(getApplicationContext().getBean("transactionManager"));
        assertNotNull(getApplicationContext().getBean("conf"));
        assertNotNull(getApplicationContext().getBean("sessionFactory"));
        assertNotNull(getApplicationContext().getBean("handlerMapping"));
        assertNotNull(getApplicationContext().getBean("messageSource"));
        assertNotNull(getApplicationContext().getBean(
                "hibernateGenericDaoInstrumentationAspect"));

        assertNotNull(getApplicationContext().getBean("viewResolver"));
        assertNotNull(getApplicationContext().getBean("exceptionResolver"));
        assertNotNull(getApplicationContext().getBean("multipartResolver"));
    }
    @Autowired
    ComuneDao comuneDao;

    public void testItalyPlugin() {
        assertNotNull(comuneDao);
    }
    @Autowired
    PersonBo personBo;

    public void testBo() {
        assertNotNull(personBo);
    }
    
    @Autowired
    HomeController homeController;
    
    @Autowired
    PeopleController peopleController;
    
    public void testController() {
        assertNotNull(homeController);
        assertNotNull(peopleController);
    }
}