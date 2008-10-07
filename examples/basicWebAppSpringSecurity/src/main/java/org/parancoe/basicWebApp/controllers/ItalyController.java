package org.parancoe.basicWebApp.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/italy/*.html")
public class ItalyController {
    private static final Logger logger = Logger.getLogger(ItalyController.class);

    @RequestMapping
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("italy/index", null);
    }

    public Logger getLogger() {
        return logger;
    }
}
