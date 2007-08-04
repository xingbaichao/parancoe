package it.jugpadova.bean;

import com.octo.captcha.service.CaptchaService;
import it.jugpadova.po.Event;
import it.jugpadova.po.Participant;
import it.jugpadova.util.JCaptchaValidable;
import it.jugpadova.util.JCaptchaValidator;
import org.springmodules.validation.bean.conf.loader.annotation.handler.CascadeValidation;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Validator;

/**
 *
 * @author lucio
 */
@Validator(value=JCaptchaValidator.class)
public class Registration implements JCaptchaValidable {

    private Event event;
    @CascadeValidation
    private Participant participant;
    private CaptchaService captchaService;
    @NotBlank
    private String captchaResponse;
    private String captchaId;

    public Registration() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
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

    public String getCaptchaId() {
        return this.captchaId;
    }

    public String getCaptchaResponse() {
        return this.captchaResponse;
    }

    public CaptchaService getCaptchaService() {
        return this.captchaService;
    }
        
}
