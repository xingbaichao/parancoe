// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package it.jugpadova.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.po.Event;
import org.parancoe.web.BaseFormController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public abstract class EventEditController extends BaseFormController {

    private static final Logger logger = Logger.getLogger(EventEditController.class);

    @Override
    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
        Event event = null;
        try {
            event = (Event) command;
            blo().getEventBo().save(event);
            return onSubmit(command, errors); // restituisce succesView
        } catch (Exception e) {
            errors.reject("error.generic");
            logger.error("Problema salvando l'evento " + event, e);
            return showForm(req, res, errors);
        }
    }

    /* If the id is passed, load the event and prepopulate the form */
    @Override
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Event event = dao().getEventDao().read(id);
            if (event == null) {
                throw new Exception();
            }
            return event;
        } catch (Exception e) {
            Event event = new Event();
            return event;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    protected abstract Daos dao();

    protected abstract Blos blo();
}
