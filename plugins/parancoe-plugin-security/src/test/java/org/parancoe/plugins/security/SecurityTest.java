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

import org.parancoe.web.plugin.Plugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test based on PluginTest.
 * 
 * @author Enrico Giurin
 * 
 */
public class SecurityTest extends PluginTest {

        @Autowired
        @Qualifier("pluginSecurityConfig")
	private Plugin plugin;
        
        @Autowired
        UserDao userDao;
        
        @Autowired
        UserAuthorityDao userAuthorityDao;
        
        @Autowired
        AuthorityDao authorityDao;
        

	public SecurityTest() {
	}
        
	public void testPlugin() throws Exception {
		assertEquals(3, getFixtureClasses().length);

	}

	@Transactional
	public void testInsertUser() {
		
		// creates entities
		User pippo = SecureUtility.newUserToValidate("pippo");
		userDao.store(pippo);
		Authority parancoeAuthority = authorityDao.findByRole("ROLE_ADMIN");
				

		UserAuthority ua = new UserAuthority();
		ua.setAuthority(parancoeAuthority);
		ua.setUser(pippo);

		userAuthorityDao.store(ua);
		assertNotNull(userDao.findByUsername("pippo").get(0));
		assertNotNull(userAuthorityDao.findAll().size());
	}

	@Override
	public Class[] getFixtureClasses() {
		return new Class[] { User.class, Authority.class, UserAuthority.class };
	}

}
