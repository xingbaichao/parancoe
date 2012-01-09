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

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author EGiurin
 * 
 */
public class ParancoeUserDetailsService implements UserDetailsService {
	@Resource
	private UserDao userDao;
	@Resource
	private AuthorityDao authorityDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(java.lang.String username)
			throws UsernameNotFoundException, DataAccessException {

		org.parancoe.plugins.securityevolution.User user = userDao
				.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("username " + username
					+ " not found in the system");
		List<Authority> authorities = authorityDao
				.findAllAuthoritiesAssociatedToUsername(username);
		return new User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, !user.isLocked(),
				authorities2GrantedAuthorities(authorities));
	}

	// TODO evaluate if there is a smarter way to convert a list...
	private List<GrantedAuthority> authorities2GrantedAuthorities(
			List<Authority> authorites) {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		if ((authorites == null) || (authorites.size() == 0))
			return result;
		for (Authority a : authorites) {
			result.add(new GrantedAuthorityImpl(a.getRole()));
		}
		return result;
	}

}
