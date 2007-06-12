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
package org.parancoe.plugins.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * A BO for UserProfile table.
 * 
 * @author <a href="mailto:enrico.giurin@gmail.com">Enrico Giurin</a>
 * @author <a href="mailto:michele.franzin@seesaw.it">Michele Franzin</a>
 * @version $Revision$
 */
public class UserBO {

    static final String USER_ADMIN = "admin";

    static final String USER_PARANCOE = "parancoe";

    private static final Logger logger = Logger.getLogger(UserBO.class);

    private UserDao dao;

    /**
         * Creates a new instance of UserBO
         */
    public UserBO() {
    }

    public UserDao getDao() {
	return dao;
    }

    public void setDao(UserDao dao) {
	this.dao = dao;
    }

    @Transactional()
    public void populateTable() {

	List<UserProfile> searches = null;
	searches = dao.findByUsernameAndPassword(USER_ADMIN, USER_ADMIN);
	if (searches.isEmpty()) {
	    UserProfile user = new UserProfile();
	    user.setEnabled(true);
	    user.setUsername(USER_ADMIN);
	    user.setPassword(USER_ADMIN);
	    dao.create(user);
	}
	searches = dao.findByUsernameAndPassword(USER_PARANCOE, USER_PARANCOE);
	if (searches.isEmpty()) {
	    UserProfile user = new UserProfile();
	    user.setEnabled(true);
	    user.setUsername(USER_PARANCOE);
	    user.setPassword(USER_PARANCOE);
	    dao.create(user);
	}
	logger.info("Created and populated table UserProfile");

    }

}
