#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home/*.html")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    // EXAMPLE: the simplest possible action
    @RequestMapping
    public ModelAndView welcome(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        params.put("something", new Object());
        return new ModelAndView("welcome", params);
    }

    /**
     * Login action
     */
    @RequestMapping
    public ModelAndView acegilogin(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        return new ModelAndView("acegilogin", params);
    }

    /**
     * Access denied
     */
    @RequestMapping
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        return new ModelAndView("accessDenied", params);
    }

    @RequestMapping
    public String pageNotFound() {
        return "404";
    }

    @RequestMapping
    public String internalServerError() {
        return "500";
    }

    // EXAMPLE
    @RequestMapping
    public ModelAndView pageThatRaiseAnUnHandledException(HttpServletRequest req,
            HttpServletResponse res) {
        if (1 == 1) {
            throw new RuntimeException("UNHANDLED BOOM!!!");
        }
        return null;
    }

}
