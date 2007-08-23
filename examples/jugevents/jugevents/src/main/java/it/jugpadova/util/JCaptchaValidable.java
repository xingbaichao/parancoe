package it.jugpadova.util;

import java.io.Serializable;

import com.octo.captcha.service.CaptchaService;

/**
 * An interface for supporting JCaptcha validation
 * @author lucio
 */
public interface JCaptchaValidable extends Serializable{
    CaptchaService getCaptchaService();
    String getCaptchaResponse();
    String getCaptchaId();
}
