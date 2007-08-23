/**
 * 
 */
package it.jugpadova.bean;

import java.io.Serializable;

import org.hibernate.validator.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

/**
 * @author Enrico Giurin
 *
 */
public class EnableJugger implements Serializable {
	//thanks to gtrev :-)
	
	@NotBlank
	@Length(min=4, max=40)
	private String password;
	
	@Expression("confirmPassword == password")
	private String confirmPassword;
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
