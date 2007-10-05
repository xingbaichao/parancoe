/**
 * 
 */
package it.jugpadova.bean;

import it.jugpadova.po.Jugger;
import it.jugpadova.util.JCaptchaValidable;
import it.jugpadova.util.JCaptchaValidator;

import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Validator;

import com.octo.captcha.service.CaptchaService;

/**
 * @author Enrico Giurin
 * 
 */
@Validator(value = JCaptchaValidator.class)
public class JuggerCaptcha implements JCaptchaValidable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3966418613209250360L;

	@CascadeValidation
	private Jugger jugger = new Jugger();

	// fields for captcha porpouse
	private CaptchaService captchaService;

	private String captchaResponse;

	private String captchaId;

	private boolean requireReliability = false;

	private String comment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.jugpadova.util.JCaptchaValidable#getCaptchaId()
	 */
	public String getCaptchaId() {
		// TODO Auto-generated method stub
		return captchaId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.jugpadova.util.JCaptchaValidable#getCaptchaResponse()
	 */
	public String getCaptchaResponse() {
		// TODO Auto-generated method stub
		return captchaResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.jugpadova.util.JCaptchaValidable#getCaptchaService()
	 */
	public CaptchaService getCaptchaService() {
		// TODO Auto-generated method stub
		return captchaService;
	}

	public Jugger getJugger() {
		return jugger;
	}

	public void setJugger(Jugger jugger) {
		this.jugger = jugger;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	public void setCaptchaResponse(String captchaResponse) {
		this.captchaResponse = captchaResponse;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isRequireReliability() {
		return requireReliability;
	}

	public void setRequireReliability(boolean requireReliability) {
		this.requireReliability = requireReliability;
	}

}
