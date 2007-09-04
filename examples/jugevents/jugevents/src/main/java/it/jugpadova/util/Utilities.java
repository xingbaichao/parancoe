/**
 * 
 */
package it.jugpadova.util;

import org.parancoe.plugins.security.User;
import org.parancoe.plugins.world.Country;

import it.jugpadova.bean.JuggerCaptcha;
import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;

/**
 * Defines useful functions.
 * @author Enrico Giurin
 *
 */
public class Utilities {
	/**
	 * Returns an instance of JuggerCaptcha with beans attributes set.
	 * @return
	 */
	public static JuggerCaptcha newJuggerCaptcha()
	{
		JuggerCaptcha jc = new JuggerCaptcha();	       
        jc.getJugger().setUser(new User());    
        jc.getJugger().setJug(new JUG());
        jc.getJugger().getJug().setCountry(new Country());
        return jc;
       
	}

}
