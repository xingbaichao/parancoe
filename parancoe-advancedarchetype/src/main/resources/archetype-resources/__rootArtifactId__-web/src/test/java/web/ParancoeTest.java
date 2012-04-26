#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.web;

import org.parancoe.web.test.BaseTest;

public class ParancoeTest extends BaseTest {

    /* test everything has been loaded properly */
    public void testSanity(){
        assertNotNull(getApplicationContext().getBean("dataSource"));
        assertNotNull(getApplicationContext().getBean("transactionManager"));
        assertNotNull(getApplicationContext().getBean("conf"));
        assertNotNull(getApplicationContext().getBean("sessionFactory"));
        assertNotNull(getApplicationContext().getBean("messageSource"));
        assertNotNull(getApplicationContext().getBean("hibernateGenericDaoInstrumentationAspect"));

        assertNotNull(getApplicationContext().getBean("viewResolver"));
        assertNotNull(getApplicationContext().getBean("exceptionResolver"));
        assertNotNull(getApplicationContext().getBean("multipartResolver"));
    }
}