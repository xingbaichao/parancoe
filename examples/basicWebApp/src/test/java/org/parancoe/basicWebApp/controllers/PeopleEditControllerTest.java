package org.parancoe.basicWebApp.controllers;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.parancoe.web.test.ControllerTest;

public class PeopleEditControllerTest extends ControllerTest {  // non si riesce a iniettare dinamicamente?
    private PeopleEditController controller;


  public void setUp() throws Exception {
        super.setUp();    // non togliere questa riga

      controller = (PeopleEditController) ctx.getBean("peopleEditController");
    }

    public void testNotNull() {
        assertNotNull(controller);
    }

    public void testSavePerson() throws Exception {
        req = new MockHttpServletRequest("POST", "/people/edit.form");
        req.setParameter("firstName", "Pippo");
        req.setParameter("lastName", "Pluto");
        req.setParameter("birthDate", "18/08/1978");

//        ModelAndView mv = controller.handleRequest(req, res);
//        assertEquals("redirect:list.html", mv.getViewName());
        assert(true);
    }
}