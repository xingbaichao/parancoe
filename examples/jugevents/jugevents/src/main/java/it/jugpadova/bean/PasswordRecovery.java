/**
 * 
 */
package it.jugpadova.bean;

import java.io.Serializable;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * Password recovery bean.
 * 
 * @author Enrico Giurin
 * 
 */
public class PasswordRecovery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3887317602949754483L;

	@NotBlank
	@Email
	private String email;

	/*
	 * @Expression("(email!=null)||(username!=null)") private String username;
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}// end of class

