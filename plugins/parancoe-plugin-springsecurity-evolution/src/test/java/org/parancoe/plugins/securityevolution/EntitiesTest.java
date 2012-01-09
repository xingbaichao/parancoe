/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security Evolution.
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
package org.parancoe.plugins.securityevolution;

import javax.annotation.Resource;

import org.junit.Test;
import org.parancoe.web.test.PluginTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author EGiurin
 *
 */
public class EntitiesTest extends PluginTest {
	@Resource
	private GroupDao groupDao;
	
	@Test
	public void testGroup() {		
		Group groupBasic = groupDao.findByName("basic");
		assertEquals(3, groupBasic.getUsers().size());
		Group groupAdmin = groupDao.findByName("admin");
		assertEquals(1, groupAdmin.getUsers().size());
	}

	@Override
	public Class[] getFixtureClasses() {
		// TODO Auto-generated method stub
		return new Class[]{Group.class, User.class};
	}

}
