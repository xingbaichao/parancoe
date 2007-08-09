/**
 * 
 */
package org.parancoe.plugins.security;

/**
 * Defines useful fuctions for security porpouse
 * @author Enrico Giurin
 *
 */
public class SecureUtility {
	/**
	 * Builds a new User with enable set to false and password equals to username.
	 * @param username
	 * @return
	 */
	public static User newUserToValidate(String username)
	{
		User user = new User();
		user.setEnabled(false);
		user.setUsername(username);
		user.setPassword(username);
		return user;
	}

}
