package org.parancoe.plugins.security;

import org.parancoe.web.test.PluginTest;



public class SecureUtilityTest extends PluginTest {

	public void setUp() throws Exception {
		super.setUp();
	}
 
	public void testNewUserToValidate() {
		
		User guest = SecureUtility.newUserToValidate("enrico");
		System.out.println(guest);
		assertEquals("enrico", guest.getPassword());
		
		
	}

}
