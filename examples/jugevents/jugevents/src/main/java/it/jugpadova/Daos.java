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
package it.jugpadova;

import it.jugpadova.dao.EventDao;
import it.jugpadova.dao.JUGDao;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.dao.ParticipantDao;
import it.jugpadova.dao.PersonDao;

import org.parancoe.persistence.dao.DaoProvider;
import org.parancoe.plugins.security.AuthorityDao;
import org.parancoe.plugins.security.UserAuthorityDao;
import org.parancoe.plugins.security.UserDao;
import org.parancoe.plugins.world.ContinentDao;
import org.parancoe.plugins.world.CountryDao;


/**
 * Interface for the DAO Provider. Doesn't require an implementation.
 * Simply add methods for the DAOs you need to use.
 * The convention for the methods is get<dao_bean_id>.
 */
public interface Daos extends DaoProvider {

    public PersonDao getPersonDao();    
    public EventDao getEventDao();    
    public ParticipantDao getParticipantDao();
    public JuggerDao getJuggerDao();
    public UserDao getUserDao();
    public AuthorityDao getAuthorityDao();    
    public CountryDao getCountryDao();
    public ContinentDao getContinentDao();
    public UserAuthorityDao getUserAuthorityDao();
    public JUGDao getJUGDao();
}
