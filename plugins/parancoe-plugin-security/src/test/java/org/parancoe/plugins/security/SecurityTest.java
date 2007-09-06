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
import org.springframework.transaction.annotation.Transactional;

/**
 * Test based on PluginTest.
 * 
 * @author Enrico Giurin
 * 
 */
public class SecurityTest extends PluginTest {

	private Plugin plugin;

	public SecurityTest() {
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		plugin = (Plugin) ctx.getBean("pluginSecurityConfig");
	}

	public void testPlugin() throws Exception {
		assertEquals(3, getFixtureClasses().length);

	}

	@Transactional
	public void testInsertUser() {
		UserDao userDao = (UserDao) ctx.getBean("userDao");
		UserAuthorityDao userAuthorityDao = (UserAuthorityDao) ctx
				.getBean("userAuthorityDao");
		AuthorityDao authorityDao = (AuthorityDao) ctx.getBean("authorityDao");

		// creates entities
		User pippo = SecureUtility.newUserToValidate("pippo");
		userDao.createOrUpdate(pippo);
		Authority parancoeAuthority = authorityDao.findByRole("ROLE_ADMIN");
				

		UserAuthority ua = new UserAuthority();
		ua.setAuthority(parancoeAuthority);
		ua.setUser(pippo);

		userAuthorityDao.createOrUpdate(ua);
		assertNotNull(userDao.findByUsername("pippo").get(0));
		assertNotNull(userAuthorityDao.findAll().size());
	}

	@Override
	public Class[] getFixtureClasses() {
		return new Class[] { User.class, Authority.class, UserAuthority.class };
	}

}
