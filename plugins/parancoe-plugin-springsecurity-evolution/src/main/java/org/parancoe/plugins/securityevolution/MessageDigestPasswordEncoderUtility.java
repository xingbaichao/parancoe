/**
 * 
 */
package org.parancoe.plugins.securityevolution;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

/**
 * Convenient method to encrypt password based on {@link Md5PasswordEncoder}
 * @author enrico
 *
 */
public class MessageDigestPasswordEncoderUtility {
	private static MessageDigestPasswordEncoder messageDigestPasswordEncoder = new Md5PasswordEncoder();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 2)
		{
			System.err.println("please specify password and salt");
		}
		final String password = args[0];
		final String salt = args[1];
		System.out.println("password: "+password+
				" - salt: "+salt+
				" - password encoded: "+messageDigestPasswordEncoder.encodePassword(password, salt));

	}

}
