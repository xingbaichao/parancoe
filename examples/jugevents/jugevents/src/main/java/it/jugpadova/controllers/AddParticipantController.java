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
import it.jugpadova.bean.Registration;
import it.jugpadova.po.Event;
import it.jugpadova.po.Participant;
import java.util.List;
import org.parancoe.web.BaseFormController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public abstract class AddParticipantController extends BaseFormController {

    private static final Logger logger =
            Logger.getLogger(AddParticipantController.class);

    @Override
    protected void initBinder(HttpServletRequest req,
            ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest req, Object command,
            BindException errors) throws Exception {
        if (errors.hasErrors()) {
            Registration registration = (Registration) command;
            Event event = blo().getEventBo().retrieveEvent(registration.getEvent().getId());
            List<Participant> participants =
                    dao().getParticipantDao().
                    findConfirmedParticipantsByEventId(event.getId());
            req.setAttribute("event", event);
            req.setAttribute("participants", participants);            
            req.setAttribute("showAddNewPartecipantDiv", "true");            
        }
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest req,
            HttpServletResponse res, Object command,
            BindException errors) throws Exception {
        Registration registration = (Registration) command;
        if (registration.getEvent().getId() == null) {
            return genericError("No valid event");
        }
        List<Participant> prevParticipant =
                dao().getParticipantDao().
                findParticipantByEmailAndEventId(registration.getParticipant().
                getEmail(), registration.getEvent().getId());
        if (prevParticipant.size() == 0) {
            blo().getEventBo().addParticipant(registration.getEvent(),
                    registration.getParticipant());
        } else {
            Participant p = prevParticipant.get(0);
            p.setConfirmed(Boolean.TRUE);
            dao().getParticipantDao().createOrUpdate(p);
            logger.info("Confirmed participant with id=" + p.getId());
        }
        ModelAndView mv = new ModelAndView("redirect:participants.html?id="+registration.getEvent().getId());
        return mv;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        Registration result = new Registration();
        result.setParticipant(new Participant());
        String sid = req.getParameter("event.id");
        if (sid != null) {
            Event event = dao().getEventDao().
                    read(Long.parseLong(sid));
            if (event != null) {
                result.setEvent(event);
                // for event showing fragment
                req.setAttribute("event", event);
            } else {
                throw new IllegalArgumentException("No event with id " + sid);
            }
        } else {
            result.setEvent(new Event());
        }
        return result;
    }

    public Logger getLogger() {
        return logger;
    }

    protected abstract Daos dao();

    protected abstract Blos blo();
}
