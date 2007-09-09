/**
 * 
 */
package it.jugpadova.util;

import org.acegisecurity.Authentication;

/**
 * Defines methods for security porpouse
 * 
 * @author Enrico Giurin
 * 
 */
public class SecurityUtilities {
	/**
	 * Returns username of authenticated user.
	 * 
	 * @return
	 */
	public static String getUserAuthentichated() {
		Authentication authentication = org.acegisecurity.context.SecurityContextHolder
				.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		return null;
	}// end of method

}
