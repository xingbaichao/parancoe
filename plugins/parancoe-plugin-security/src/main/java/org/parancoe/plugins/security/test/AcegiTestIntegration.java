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
import org.springframework.context.ConfigurableApplicationContext;

public class AcegiTestIntegration {
	public AcegiTestIntegration(ConfigurableApplicationContext applicationContext) {
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
