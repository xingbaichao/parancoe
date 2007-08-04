/*
 * JCaptchaController.java
 *
 * Created on 3-ago-2007, 16.09.07
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.jugpadova.controllers;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import it.jugpadova.Blos;
import it.jugpadova.Daos;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.web.BaseMultiActionController;
import org.springframework.web.servlet.ModelAndView;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 *
 * @author lucio
 */
public abstract class JCaptchaController extends BaseMultiActionController {

    private static final Logger logger = Logger.getLogger(JCaptchaController.class);
    private ImageCaptchaService captchaService;

    public JCaptchaController() {
    }

    public ModelAndView image(HttpServletRequest req, HttpServletResponse res) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        // get the session id that will identify the generated captcha.
        //the same id must be used to validate the res, the session id is a good candidate!
        String captchaId = req.getSession().getId();

        // call the ImageCaptchaService getChallenge method
        BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, req.getLocale());

        // a jpeg encoder
        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
        jpegEncoder.encode(challenge);

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the res
        res.setHeader("Cache-Control", "no-store");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setContentType("image/jpeg");
        ServletOutputStream resOutputStream = res.getOutputStream();
        resOutputStream.write(captchaChallengeAsJpeg);
        resOutputStream.flush();
        resOutputStream.close();
        return null;
    }

    public ImageCaptchaService getCaptchaService() {
        return captchaService;
    }

    public void setCaptchaService(ImageCaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * You don't have to implement this. 
     *
     * @return The provider of DAOs
     */
    protected abstract Daos dao();

    /**
     * You don't have to implement this. 
     *
     * @return The provider of business logic objects
     */
    protected abstract Blos blo();
}
