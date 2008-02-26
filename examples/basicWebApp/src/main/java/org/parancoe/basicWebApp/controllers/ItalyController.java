package org.parancoe.basicWebApp.controllers;

import org.parancoe.web.BaseMultiActionController;
import org.parancoe.util.MemoryAppender;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.Blos;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@UrlMapping("/italy/*.html")
public abstract class ItalyController extends BaseMultiActionController {
    private static final Logger logger = Logger.getLogger(ItalyController.class);
    
    @RequestMapping
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("italy/index", null);
    }

    public Logger getLogger() {
        return logger;
    }

}
