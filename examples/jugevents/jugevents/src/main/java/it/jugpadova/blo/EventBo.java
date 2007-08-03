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
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.EventDao;
import it.jugpadova.dao.JuggerDao;
import org.springframework.transaction.annotation.Transactional;
import it.jugpadova.po.Event;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.Participant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.acegisecurity.Authentication;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.proxy.scriptaculous.Effect;

public class EventBo {

    private static final Logger logger = Logger.getLogger(EventBo.class);
    private Daos daos;

    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
    }

    @Transactional(readOnly = true)
    public List<Event> retrieveEvents() {
        List<Event> events = getDaos().getEventDao().findCurrentEvents();
        for (Event event : events) {
            event.getParticipants().size();
        }
        return events;
    }

    @Transactional
    public void save(Event event) {
        if (event.getOwner() == null) {
            Jugger jugger = getCurrentJugger();
            event.setOwner(jugger);
        }
        EventDao eventDao = getDaos().getEventDao();
        eventDao.createOrUpdate(event);
    }

    private Jugger getCurrentJugger() {
        Jugger result = null;
        Authentication authentication = org.acegisecurity.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            JuggerDao juggerDao = getDaos().getJuggerDao();
            List<Jugger> juggers = juggerDao.findByUsername(name);
            if (juggers.size() > 0) {
                result = juggerDao.findByUsername(name).get(0);
                if (juggers.size() > 1) {
                    logger.warn("MOre than a JUG with the '" + name + "' username");
                }
            } else {
                logger.error("No jugger with the '" + name + "' username");
            }
        }
        return result;
    }

    @Transactional
    public void register(Event event, Participant participant) {
        EventDao eventDao = getDaos().getEventDao();
        event = eventDao.read(event.getId());
        getDaos().getParticipantDao().createOrUpdate(participant);
        event.addParticipant(participant);
        eventDao.createOrUpdate(event);
    }

    @Transactional(readOnly = true)
    public Event retrieveEvent(Long id) {
        Event event = getDaos().getEventDao().read(id);
        event.getParticipants().size();
        return event;
    }

    @Transactional(readOnly = true)
    public List findPartialLocation(String partialLocation, String username) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialLocation) && !StringUtils.isBlank(username)) {
            try {
                List<Event> events = getDaos().getEventDao().findEventByPartialLocationAndOwner("%" + partialLocation + "%", username);
                Iterator<Event> itEvents = events.iterator();
                while (itEvents.hasNext()) {
                    Event event = itEvents.next();
                    result.add(event.getLocation() + "<div class=\"informal\">" + event.getDirections() + "</div>" + "<div class=\"informal hidden\">" + event.getId() + "</div>");
                }
            } catch (Exception e) {
                logger.error("Error completing the location", e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public void copyDirectionsFromEvent(long eventId) {
        WebContext wctx = WebContextFactory.get();
        ScriptSession session = wctx.getScriptSession();
        Util util = new Util(session);
        Event event = daos.getEventDao().read(Long.valueOf(eventId));
        if (event != null) {
            util.setValue("location", event.getLocation());
            util.setValue("directions", event.getDirections());
            util.setValue("directionsPreview", FilterBo.filterText(event.getDirections(), event.getFilter(), false));
            Effect effect = new Effect(session);
            effect.highlight("location");
            effect.highlight("directions");
            effect.highlight("directionsPreview");
        }
    }
}
