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
 * A BO for Authorities table.
 *
 * @author <a href="mailto:enrico.giurin@gmail.com">Enrico Giurin</a>
 * @version $Revision$
 */
public class AuthoritiesBO {
	 static final String ROLE_ADMIN = "ROLE_ADMIN";
	 static final String ROLE_PARANCOE = "ROLE_PARANCOE";
	 
	 private static final Logger logger = Logger.getLogger(UserBO.class);	
	
    private AuthoritiesDao dao;
    
    /**
     * Creates a new instance of AuthoritiesBO
     */
    public AuthoritiesBO() {
    }
    
    public AuthoritiesDao getDao() {
        return dao;
    }
    
    public void setDao(AuthoritiesDao dao) {
        this.dao = dao;
    }
    
    
    @Transactional()
    public void populateTable() {
       
        List<Authorities> searches = null;
        searches = dao.findByUsernameAndAuthority(UserBO.USER_ADMIN, ROLE_ADMIN);
        if (searches.isEmpty()) {
           Authorities authorities = new Authorities();
           authorities.setUsername(UserBO.USER_ADMIN);
           authorities.setAuthority(ROLE_ADMIN);
           dao.create(authorities);
        }
        searches = dao.findByUsernameAndAuthority(UserBO.USER_PARANCOE, ROLE_PARANCOE);
        if (searches.isEmpty()) {
           Authorities authorities = new Authorities();
           authorities.setUsername(UserBO.USER_PARANCOE);
           authorities.setAuthority(ROLE_PARANCOE);
           dao.create(authorities);
        }
        logger.info("Created and populated table Authorities");
       
    }
    
    
  
 
}
