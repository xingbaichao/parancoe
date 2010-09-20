#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controllers;

import javax.annotation.Resource;
import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.security.UserDao;
import org.parancoe.web.test.ControllerTest;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

public class AdminUsersControllerTest extends ControllerTest {

    @Resource
    private AdminUsersController controller;
    @Resource(
    name =
    "org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter${symbol_pound}0")
    private HandlerAdapter methodHandler;
    @Resource
    private UserDao userDao;

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Authority.class, User.class};
    }

    public void testNotNull() {
        assertNotNull(controller);
        assertNotNull(userDao);
    }

    public void testEditAndSaveUser() throws Exception {
        // Testing the method handler supports the controller
        assertTrue(methodHandler.supports(controller));
        // Retrieve a person for testing
        User user = userDao.findByUsername("parancoe");
        assertNotNull(user);
        // Test edit (showing form)
        resetRequestAndResponse();
        req.setMethod("GET");
        req.setRequestURI("/admin/users/" + user.getId() + "/edit");
        ModelAndView mv = methodHandler.handle(req, res, controller);
        assertEquals("admin/users/edit", mv.getViewName());
        assertTrue(mv.getModelMap().containsAttribute("userBean"));
        assertNotNull(req.getSession().getAttribute("userBean"));
        // Test store (posting form)
        resetRequestAndResponse();
        req.setMethod("POST");
        req.setRequestURI("/admin/users/" + user.getId());
        req.addParameter("user.username", "parancoe3");
        mv = methodHandler.handle(req, res, controller);
        assertEquals("redirect:", mv.getViewName());
        // Check on the DB
        user = userDao.get(user.getId());
        assertNotNull(user);
        assertEquals("parancoe3", user.getUsername());
    }

    public void testDelete() throws Exception {
        // Testing the method handler supports the controller
        assertTrue(methodHandler.supports(controller));
        // Retrieve a person for testing
        User user = userDao.findByUsername("parancoe");
        assertNotNull(user);
        // Test edit (showing form)
        resetRequestAndResponse();
        req.setMethod("DELETE");
        req.setRequestURI("/admin/users/" + user.getId());
        ModelAndView mv = methodHandler.handle(req, res, controller);
        assertEquals("redirect:", mv.getViewName());
        // Check on the DB
        user = userDao.findByUsername("parancoe");
        assertNull(user);
    }

}
