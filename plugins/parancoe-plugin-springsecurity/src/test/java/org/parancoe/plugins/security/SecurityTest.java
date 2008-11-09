// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.plugins.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test based on PluginTest.
 * 
 * @author Enrico Giurin
 * 
 */
public class SecurityTest extends PluginTest {

	@Autowired
    @Qualifier("applicationContextpluginSecurityConfig")
    private ApplicationContextPlugin plugin;
    @Resource
    UserDao userDao;
    @Resource
    AuthorityDao authorityDao;

    public SecurityTest() {
    }

    public void testPlugin() throws Exception {
        assertEquals(2, getFixtureClasses().length);

    }

    @Transactional
    public void testInsertUser() {
        // creates entities
        User pippo = SecureUtility.newUserToValidate("pippo");
        userDao.store(pippo);
        Authority parancoeAuthority = authorityDao.findByRole("ROLE_ADMIN");
        List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(parancoeAuthority);
        pippo.setAuthorities(authorities);
        userDao.store(pippo);
        assertNotNull(userDao.findByUsername("pippo"));
    }

    @Transactional
    public void testDeleteUser()
    {
    	Authority rp = authorityDao.findByRole("ROLE_PARANCOE");   	
    	assertEquals(rp.getUsers().size(), 2);          	 
    	userDao.delete(userDao.findByUsername("parancoe")); 
    	
    	//TODO understand why if we remove this row the test at the last
    	//row of the code fails, 2 instead of 1
    	assertNull(userDao.findByUsername("parancoe"));
    	//need to evict rp to unbind it from hibernate session (thanks to Lucio)
    	userDao.getHibernateTemplate().getSessionFactory().getCurrentSession().evict(rp);    	
    	assertEquals(authorityDao.findByRole("ROLE_PARANCOE").getUsers().size(), 1);   	
    }
    @Transactional
    public void testFindAuthoritiesByPartialUsername()
    {
    	List<Authority> authorities = userDao.findAuthoritiesByPartialUsername("%pecI%");
    	assertEquals(authorities.size(), 2);
    	authorities = userDao.findAuthoritiesByPartialUsername("admin");
    	assertEquals(authorities.size(), 1);
    }
    @Transactional
    public void testFindByPartialUsername() {
    	List<User> user = userDao.findByPartialUsername("%aranc%");
    	assertEquals(1, user.size());    	
    }
    
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{ Authority.class, User.class, };
    }
}
