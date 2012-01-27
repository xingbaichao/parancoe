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
import org.mockito.Mock;
import org.parancoe.web.test.PluginTest;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.mockito.Mockito.*;

/**
 * @author EGiurin
 *
 */
public class AuthenticationManagerTest extends PluginTest {
	@Resource
	private AuthenticationManager authenticationManager;
	
	private Authentication authentication;

	/**
	 * Test method for {@link org.springframework.security.authentication.AuthenticationManager#authenticate(org.springframework.security.core.Authentication)}.
	 */
	@Test
	public void testAuthenticate() {		
		authentication =  new UsernamePasswordAuthenticationToken("parancoe", "parancoe");
		Authentication result = authenticationManager.authenticate(authentication);
		assertTrue(result.isAuthenticated());	
	}
	
	@Test
	public void testAuthenticateWithUserLocked() {		
		authentication =  new UsernamePasswordAuthenticationToken("locked", "locked");		
		try {
			authenticationManager.authenticate(authentication);
			fail("User locked is locked!");
		} catch (AuthenticationException e) {
			assertTrue(e instanceof LockedException);
		}			
	}
	
	@Test
	public void testAuthenticateWithBadCredentials() {		
		authentication =  new UsernamePasswordAuthenticationToken("parancoe", "wrongPassword");		
		try {
			authenticationManager.authenticate(authentication);
			fail("Provided password is wrong");
		} catch (AuthenticationException e) {
			assertTrue(e instanceof BadCredentialsException);
		}			
	}
	
	@Override
    public Class[] getFixtureClasses() {
        return new Class[]{User.class,};
    }


}
