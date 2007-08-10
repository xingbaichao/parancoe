/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Enrico Giurin
 *
 */
public class SecurityBo {
	
	private static final Logger logger = Logger.getLogger(SecurityBo.class);
    private Daos daos;
    
    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
    }
    @Transactional
    public void newUser(String username) throws Exception
    {
    	/*
    	  	AuthorityDao authoritiesDao = daos.getAuthorityDao();
	        UserDao userDao = daos.getUserDao();
	        User user = (userDao.findByUsername(username)).get(0);
	        if(user == null)
	        {
	        	user = new User();
	        	user.setEnabled(false);
	        	user.setPassword(username);
	        	user.setUsername(username);	        
		        user.setAuthorities(authoritiesDao.findByRole("ROLE_PARANCOE"));
		        userDao.createOrUpdate(user);
		        logger.info("New User "+username+" created with success");
		        return;
	        }
	        throw new Exception("User :"+username+" already presents");    
	        */    	
        
    }

}
