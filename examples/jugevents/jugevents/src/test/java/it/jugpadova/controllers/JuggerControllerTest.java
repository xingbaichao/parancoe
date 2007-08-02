/**
 * 
 */
package it.jugpadova.controllers;

import org.parancoe.web.test.ControllerTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import junit.framework.TestCase;

/**
 * @author Admin
 *
 */
public class JuggerControllerTest extends ControllerTest {
	
	 
    private JuggerController controller;

	@Override
    public void setUp() throws Exception {
        super.setUp();    // non togliere questa riga
        controller = (JuggerController) ctx.getBean("juggerController");
    }
	/**
	 * Test method for {@link it.jugpadova.controllers.JuggerController#list(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 */
	public void testList() throws Exception  {
		 	req = new MockHttpServletRequest("GET", "/jugger/list.html");
	        ModelAndView mv = controller.handleRequest(req, res);
	        
	}

}
