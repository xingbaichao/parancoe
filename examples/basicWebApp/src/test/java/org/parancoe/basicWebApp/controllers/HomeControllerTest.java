package org.parancoe.basicWebApp.controllers;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.mock.web.MockHttpServletRequest;
import org.parancoe.web.test.ControllerTest;

public class HomeControllerTest extends ControllerTest {

    // non si riesce a iniettare dinamicamente?
    private HomeController controller;

    public void setUp() throws Exception {
        super.setUp();    // non togliere questa riga
        controller = (HomeController) ctx.getBean("homeController");
    }

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
