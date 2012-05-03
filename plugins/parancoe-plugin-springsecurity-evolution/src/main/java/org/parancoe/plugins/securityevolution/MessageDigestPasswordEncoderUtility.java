/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security Evolution.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security Evolution.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
