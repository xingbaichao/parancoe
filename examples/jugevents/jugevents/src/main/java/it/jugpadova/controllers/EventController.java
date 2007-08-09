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

import org.parancoe.web.BaseMultiActionController;
import it.jugpadova.Daos;
import it.jugpadova.Blos;
import it.jugpadova.po.Event;
import it.jugpadova.po.Participant;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class EventController extends BaseMultiActionController {
    private static Logger logger = Logger.getLogger(EventController.class);
    
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res){
        ModelAndView mv = new ModelAndView("event/list");
        mv.addObject("events", blo().getEventBo().retrieveEvents());
        return mv;
    }
    
    public ModelAndView delete(HttpServletRequest req, HttpServletResponse res){
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Event event = dao().getEventDao().read(id);
            if (event == null) throw new IllegalArgumentException("No event with id "+id);
            dao().getEventDao().delete(event);
        } catch(Exception e){
            return genericError(e);
        }
        return new ModelAndView("redirect:list.html");
    }
    
    public ModelAndView participants(HttpServletRequest req, HttpServletResponse res){
        ModelAndView mv = new ModelAndView("event/participants");
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Event event = blo().getEventBo().retrieveEvent(id);
            if (event == null) throw new IllegalArgumentException("No event with id "+id);
            List<Participant> participants = dao().getParticipantDao().findConfirmedParticipantsByEventId(event.getId());
            mv.addObject("event", event);
            mv.addObject("participants", participants);
        } catch(Exception e){
            return genericError(e);
        }
        return mv;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    protected abstract Daos dao();
    protected abstract Blos blo();
}
