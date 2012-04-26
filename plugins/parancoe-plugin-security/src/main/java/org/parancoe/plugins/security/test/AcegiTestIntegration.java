/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Security.
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.parancoe.plugins.security.test;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.TestingAuthenticationProvider;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.springframework.context.ApplicationContext;

public class AcegiTestIntegration {
	public AcegiTestIntegration(ApplicationContext applicationContext) {
		List providerList = new ArrayList();
		providerList.add(new TestingAuthenticationProvider());
		
		// Override the regular spring configuration 
		ProviderManager providerManager = (ProviderManager) applicationContext.getBean("authenticationManager");
		providerManager.setProviders(providerList);
	}

	public void setAuthenticationToken(String username, String password, GrantedAuthority[] authority) {
                
                // Create and store the Acegi SecureContext into the ContextHolder. 
		SecurityContextImpl secureContext = new SecurityContextImpl();
		secureContext.setAuthentication(new TestingAuthenticationToken(username, password, authority));
		SecurityContextHolder.setContext(secureContext);
	}
}
