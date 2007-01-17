package org.parancoe.basicWebApp.controllers;

import java.text.ParseException;
import org.parancoe.web.BaseMultiActionController;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.Blos;
import org.parancoe.basicWebApp.po.Person;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

public abstract class PeopleController extends BaseMultiActionController {
    private static Logger logger = Logger.getLogger(PeopleController.class);

    public ModelAndView list(HttpServletRequest req, HttpServletResponse res){
        Map params = new HashMap();
        params.put("people", dao().person.findAll());
        return new ModelAndView("people/list", params);
    }

    public ModelAndView populate(HttpServletRequest req, HttpServletResponse res){
        Map params = new HashMap();
        try {
            blo().person.populateArchive();
        } catch (ParseException ex) {
            return genericError(ex);
        }
        params.put("people", dao().person.findAll());
        return new ModelAndView("people/list", params);
    }

    public ModelAndView show(HttpServletRequest req, HttpServletResponse res){
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            logger.debug("got id "+id);
            Map params = new HashMap();
            Person p = dao().person.read(id);

            params.put("person",p);
            return new ModelAndView("people/show", params);
        } catch(Exception e){
            return genericError("Persona non trovata");
        }
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    protected abstract Daos dao();
    protected abstract Blos blo();
}
