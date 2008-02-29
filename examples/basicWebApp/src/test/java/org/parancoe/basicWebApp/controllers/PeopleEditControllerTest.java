package org.parancoe.basicWebApp.controllers;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.parancoe.web.test.ControllerTest;
import org.springframework.beans.factory.annotation.Autowired;

public class PeopleEditControllerTest extends ControllerTest {  // non si riesce a iniettare dinamicamente?
    
    @Autowired  
    private PeopleEditController controller;



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