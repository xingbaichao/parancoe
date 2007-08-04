package it.jugpadova.util;

import com.octo.captcha.service.CaptchaService;

/**
 * An interface for supporting JCaptcha validation
 * @author lucio
 */
public interface JCaptchaValidable {
    CaptchaService getCaptchaService();
    String getCaptchaResponse();
    String getCaptchaId();
}
