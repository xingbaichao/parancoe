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
import org.springframework.transaction.annotation.Transactional;
import it.jugpadova.po.Event;
import it.jugpadova.po.Participant;

import java.util.List;

public class EventBo {
    private Daos daos;
    
    public Daos getDaos() {
        return daos;
    }
    
    public void setDaos(Daos daos) {
        this.daos = daos;
    }
    
    @Transactional(readOnly=true)
    public List<Event> retrieveEvents() {
        List<Event> events = getDaos().getEventDao().findCurrentEvents();
        for (Event event : events) {
            event.getParticipants().size();
        }
        return events;
    }
    
    @Transactional
    public void register(Event event, Participant participant) {
        EventDao eventDao = getDaos().getEventDao();
        event = eventDao.read(event.getId());
        getDaos().getParticipantDao().createOrUpdate(participant);
        event.addParticipant(participant);
        eventDao.createOrUpdate(event);
    }
    
    @Transactional(readOnly=true)
    public Event retrieveEvent(Long id) {
        Event event = getDaos().getEventDao().read(id);
        event.getParticipants().size();
        return event;
    }
    
}

