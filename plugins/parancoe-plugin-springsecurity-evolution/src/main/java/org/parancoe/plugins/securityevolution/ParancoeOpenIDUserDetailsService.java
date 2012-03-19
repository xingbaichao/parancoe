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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

/**
 * @author enrico
 *
 */
public class ParancoeOpenIDUserDetailsService implements
		AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	@Resource
	private UserDetailsService userDetailsService;
	
	 public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
	        String id = token.getIdentityUrl();
	        String email = null;
	        
	        List<OpenIDAttribute> attributes = token.getAttributes();
	        for (OpenIDAttribute attribute : attributes) {
	            if (attribute.getName().equals("email")) {
	                email = attribute.getValues().get(0);
                     break;
	            }	            
	        }
	        if(email == null)
	        {
	        	throw new UsernameNotFoundException("There is no email associated to this openID token: "+id);
	        }
	        
	        return userDetailsService.loadUserByUsername(email);

}
	 } 
