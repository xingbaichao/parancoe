//TO BE COMPLETED
package org.parancoe.plugins.security;

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
    	
    	//retrieves all dao
    	
    	UserDao userDao = (UserDao) ctx.getBean("userDao");
    	UserAuthorityDao userAuthorityDao = (UserAuthorityDao) ctx.getBean("userAuthorityDao");
    	AuthorityDao authorityDao = (AuthorityDao) ctx.getBean("authorityDao");
    	
    	//creates  entities
    	User pippo = SecureUtility.newUserToValidate("pippo");
    	userDao.createOrUpdate(pippo); 
    	Authority parancoeAuthority = authorityDao.findByRole("ROLE_ADMIN").get(0);
    	
    	UserAuthority ua = new UserAuthority();
    	ua.setAuthority(parancoeAuthority);
    	ua.setUser(pippo);
    	
    	userAuthorityDao.createOrUpdate(ua);
    	assertNotNull(userDao.findByUsername("pippo").get(0));
    	assertNotNull(userAuthorityDao.findAll().size());
    	
    	
    	
    }


	

	@Override
	public Class[] getFixtureClasses() {
		// TODO Auto-generated method stub
		return new Class[]{User.class, Authority.class, UserAuthority.class};
	}



    
} //
