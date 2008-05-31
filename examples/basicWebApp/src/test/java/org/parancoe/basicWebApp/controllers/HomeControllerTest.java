package org.parancoe.basicWebApp.controllers;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.mock.web.MockHttpServletRequest;
import org.parancoe.web.test.ControllerTest;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeControllerTest extends ControllerTest {

    @Autowired
    private HomeController controller;

    public void testNotNull() {
        assertNotNull(controller);
    }

    public void testWelcome() throws Exception {
        req = new MockHttpServletRequest("GET", "/home/welcome.html");
        ModelAndView mv = controller.handleRequest(req, res);
        assertEquals("welcome", mv.getViewName());
        assertNotNull(mv.getModel().get("something"));
    }
}
