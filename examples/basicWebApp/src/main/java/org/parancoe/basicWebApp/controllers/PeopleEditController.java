package org.parancoe.basicWebApp.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.validation.BindException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.parancoe.web.BaseFormController;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.basicWebApp.Daos;
import org.parancoe.basicWebApp.Blos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class PeopleEditController extends BaseFormController {

    private final static Logger logger = Logger.getLogger(PeopleEditController.class);

    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
    }

    /* questo viene chiamato solo in caso di una post a people/edit.form */
    protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
        Person person = null;
        try {
            person = (Person) command;
            dao().getPersonDao().createOrUpdate(person);

            return onSubmit(command, errors); // restituisce succesView
        } catch (Exception e) {
            errors.reject("error.generic");
            logger.error("Problema salvando Utente " + person, e);
            return showForm(req, res, errors);
        }
    }

    /* Se passo il param id, carico la persona da db e mostro il form prepopolato */
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Person p = dao().getPersonDao().read(id);
            if (p==null) throw new Exception();
            return p;
        } catch(Exception e){
            return new Person();
        }
    }

    public Logger getLogger() {
        return logger;
    }
    protected abstract Daos dao();
    protected abstract Blos blo();
}
