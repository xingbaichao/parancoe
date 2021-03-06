#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for secure porpouse.
 * @author Enrico Giurin
 *
 */
@Controller
@RequestMapping("/*.secure")
public class SecureController {

    private static Logger logger = LoggerFactory.getLogger(SecureController.class);

    @RequestMapping
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("login");
    }

    @RequestMapping
    public ModelAndView accessDenied(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("accessDenied");
    }

    @RequestMapping
    public ModelAndView j_spring_security_check(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }

    @RequestMapping
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }
}
