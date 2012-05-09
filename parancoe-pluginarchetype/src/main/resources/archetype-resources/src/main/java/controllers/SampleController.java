#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/plugin/sample")
public class SampleController {

    private static final Logger logger = Logger.getLogger(SampleController.class);

    // EXAMPLE: the simplest possible action
    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res, Model model) {
        logger.info("Executing index in the SampleController");
        model.addAttribute("something", new Object());
        return "plugin/sample/index";
    }

}
