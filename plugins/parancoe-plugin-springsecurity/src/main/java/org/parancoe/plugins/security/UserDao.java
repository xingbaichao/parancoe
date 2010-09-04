/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.plugins.security;

import java.util.List;
import org.lambico.dao.generic.Dao;
import org.lambico.dao.generic.GenericDao;

/**
 * The DAO interface for the UserProfile entity.
 * 
 * @author <a href="mailto:enrico.giurin@gmail.com">Enrico Giurin</a>
 * @author <a href="mailto:michele.franzin@seesaw.it">Michele Franzin</a>
 * @version $Revision$
 */
@Dao(entity = User.class)
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Returns the unique user identified by username and password.
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * Returns the unique user identified by username.
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * Returns all the users identified by partial username.
     * @param username
     * @return
     */
    List<User> findByPartialUsername(String username);

    /**
     *  Returns all the authorities associated to a partial username.
     * @param username compare upper-case and like mode
     * @return
     */
    List<Authority> findAuthoritiesByPartialUsername(String username);
}
