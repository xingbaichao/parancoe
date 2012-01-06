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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author EGiurin
 *
 */
public class Util {
	private static MessageDigest md = null;
	/**
	 * Convenient method copied from <a href="http://code.google.com/p/jugevents/">jugevents</a>
	 * to encode password based on username (salt).
	 * @param rawPass
	 * @param username
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException 
	 */
	public static String encodePassword(String rawPass, String username) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest((rawPass+"{" + username + "}").getBytes("UTF-8"));
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            result.append(Integer.toHexString(((char)digest[i]) & 0xFF));
        }
        return result.toString();
    }
	//usage of the class
	public static void main(String[] args) {
		try {
			System.out.println(encodePassword("parancoe", "parancoe"));
			System.out.println(encodePassword("locked", "locked"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
