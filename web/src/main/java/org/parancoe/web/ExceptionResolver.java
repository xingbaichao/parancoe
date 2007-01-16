package org.parancoe.web;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResolver implements HandlerExceptionResolver {
    private static final Logger log = Logger.getLogger(ExceptionResolver.class);

    /*
     * gira tutti gli errori non gestiti a genericError.jsp
     */
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res,
            Object object, Exception e) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("messageCode", "error.generic");
        params.put("exception", e);
        return new ModelAndView("genericError", params);
    }
}
