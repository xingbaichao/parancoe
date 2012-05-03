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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;


import org.parancoe.web.test.PluginTest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author enricogiurin
 *
 */
public class ParancoeUserDetailsServiceTest  extends PluginTest {
	public static final String USERNAME = "parancoe";
	public static final String INVALID_USERNAME = "invalid";
	public static final String PWD = "parancoe";
	
	@Resource
	private ParancoeUserDetailsService parancoeUserDetailsService;
	

	/**
	 * Test method for {@link org.parancoe.plugins.securityevolution.ParancoeUserDetailsService#loadUserByUsername(java.lang.String)}.
	 * Trying using TDD approach
	 */

	public void testLoadUserByUsername() {
		UserDetails parancoeUD = parancoeUserDetailsService.loadUserByUsername(USERNAME);
		assertEquals(USERNAME, parancoeUD.getUsername());
		//assertEquals(PWD, enricoUD.getPassword());
		assertTrue(parancoeUD.isEnabled());
		
		List<GrantedAuthority> listGA = new ArrayList<GrantedAuthority>(parancoeUD.getAuthorities());	
		assertEquals(1, listGA.size());
		assertEquals("ROLE_USER", listGA.get(0).getAuthority());
	}
	

	public void testLoadUserByUsernameWithNoExisingUser() {
		try {
			parancoeUserDetailsService.loadUserByUsername(INVALID_USERNAME);
			fail();
		} catch (UsernameNotFoundException e) {
			assertEquals("username "+INVALID_USERNAME+" not found in the system", e.getMessage());
		}
		
		
	}
	
	@Override
	public Class[] getFixtureClasses() {
		// TODO Auto-generated method stub
		return new Class[]{Authority.class, Group.class, User.class};
	}

}
