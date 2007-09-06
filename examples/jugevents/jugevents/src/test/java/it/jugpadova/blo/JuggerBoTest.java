/**
 * 
 */
package it.jugpadova.blo;

import org.parancoe.plugins.security.User;

import it.jugpadova.Daos;
import it.jugpadova.JugEventsBaseTest;
import it.jugpadova.dao.JUGDao;
import it.jugpadova.exception.UserAlreadyEnabledException;

/**
 * @author Enrico Giurin
 *
 */
public class JuggerBoTest extends JugEventsBaseTest {
	private JuggerBo juggerBo;

    public JuggerBoTest() {
    	juggerBo = (JuggerBo) ctx.getBean("juggerBo");        
    }
    public void testNewUser() 
    {
    	try {
    		User user =juggerBo.newUser("new user");    		
    		assertEquals("new user", user.getUsername());
		} catch (Exception e) {
			assertTrue(e instanceof UserAlreadyEnabledException);
		}
    	
    }//end of method

}
