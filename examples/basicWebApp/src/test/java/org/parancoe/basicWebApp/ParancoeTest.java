package org.parancoe.basicWebApp;

import org.parancoe.web.test.BaseTest;

public class ParancoeTest extends BaseTest {

  /* test everything has been loaded properly */
  public void testSanity() {
    assertNotNull(ctx.getBean("dataSource"));
    assertNotNull(ctx.getBean("transactionManager"));
    assertNotNull(ctx.getBean("conf"));
    assertNotNull(ctx.getBean("sessionFactory"));
    assertNotNull(ctx.getBean("handlerMapping"));
    assertNotNull(ctx.getBean("messageSource"));
    assertNotNull(ctx.getBean("hibernateGenericDaoInstrumentationAspect"));

    assertNotNull(ctx.getBean("viewResolver"));
    assertNotNull(ctx.getBean("exceptionResolver"));
    assertNotNull(ctx.getBean("multipartResolver"));
  }

//  public void testItalyPlugin() {
//    assertNotNull(ctx.getBean("comuneDao"));
//  }
}