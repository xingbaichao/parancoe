/**
 * 
 */
package it.jugpadova.bean;

import it.jugpadova.po.Jugger;

import java.io.Serializable;

import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Expression;

/**
 * Bean for editing jugger.
 * @author Enrico Giurin
 *
 */
public class EditJugger implements Serializable {
	@CascadeValidation
	private Jugger jugger;
	@Expression("confirmPassword == jugger.user.password")
	private String confirmPassword;
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Jugger getJugger() {
		return jugger;
	}
	public void setJugger(Jugger jugger) {
		this.jugger = jugger;
	}

}