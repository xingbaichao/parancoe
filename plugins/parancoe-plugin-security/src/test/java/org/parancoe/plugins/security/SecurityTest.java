//TO BE COMPLETED
package org.parancoe.plugins.security;


import java.util.List;


import org.parancoe.web.plugin.Plugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.transaction.annotation.Transactional;



/**
 * Test based on PluginTest.
 * @author Enrico Giurin
 *
 */
public class SecurityTest extends PluginTest {
	 private Plugin plugin;
	 
	 
	public SecurityTest() {
        super();
    }
	
	
	@Override
    public void setUp() throws Exception {
		super.setUp();
        plugin = (Plugin) ctx.getBean("pluginSecurityConfig");      
    }
	
	
	 public void testPlugin() throws Exception {
	        assertEquals(2, plugin.getFixtureClasses().size());
	        assertEquals(Authorities.class, plugin.getFixtureClasses().get(0));
	        assertEquals(User.class, plugin.getFixtureClasses().get(1));
	      
	    }
	 @Transactional
	 public void testUser() {
	        AuthoritiesDao authoritiesDao = (AuthoritiesDao) this.ctx.getBean("authoritiesDao");
	        UserDao userDao = (UserDao) this.ctx.getBean("userDao");
	        User admin = (userDao.findByUsername("admin")).get(0);
	        admin.setAuthorities(authoritiesDao.findAll());
	        userDao.createOrUpdate(admin);
	        
	        
	    }
	


	@Override
    public Class[] getFixtureClasses() {
        return new Class[] { Authorities.class, User.class};
    }
	
	
	

	
	
}//
