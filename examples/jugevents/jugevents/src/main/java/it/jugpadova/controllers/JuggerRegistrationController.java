// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package it.jugpadova.controllers;

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.bean.EnableJugger;
import it.jugpadova.bean.JuggerCaptcha;
import it.jugpadova.exception.UserAlreadyPresentsException;
import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;
import it.jugpadova.util.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parancoe.plugins.security.SecureUtility;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.world.Country;
import org.parancoe.web.BaseFormController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.service.CaptchaService;

/**
 * 
 * @author Enrico Giurin
 *
 */
public abstract class JuggerRegistrationController extends BaseFormController {
    
    private final static Logger logger = Logger.getLogger(JuggerRegistrationController.class);
    //captcha
    private CaptchaService captchaService;

    
    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"),true));
    }
    
    @Override
	protected void onBind(HttpServletRequest request, Object command) throws Exception {
		JuggerCaptcha jc = (JuggerCaptcha)command;
		jc.getJugger().getUser().setPassword("xxx");
		
    	
	}
    
    /* questo viene chiamato solo in caso di una post a jugger/edit.form */
   
    protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command, BindException errors) throws Exception {
    	String baseUrl =
            "http://" + req.getServerName() + ":" + req.getServerPort() +
            req.getContextPath();
    	JuggerCaptcha jc = (JuggerCaptcha) command;
        try {
        	  blo().getJuggerBO().save(jc.getJugger(), baseUrl); 
		} catch (UserAlreadyPresentsException e) {
			
			 errors.rejectValue("jugger.user.username", "useralreadypresents", e.getMessage());
			 logger.error(e);
	         return showForm(req, res, errors);
			
		}
      
        ModelAndView mv = onSubmit(command, errors);
        mv.addObject("juggerId",jc.getJugger().getId());
        return mv;
        
        
    }
    
    
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        //set list of countries into request
    	List<Country> list =  dao().getCountryDao().findByOrderByEnglishName();
        req.setAttribute("countries", list);
        JuggerCaptcha jc = Utilities.newJuggerCaptcha();        
        jc.setCaptchaId(req.getSession().getId());
        jc.setCaptchaService(captchaService);
    	return jc;
    }

    public Logger getLogger() {
        return logger;
    }
    protected abstract Daos dao();
    protected abstract Blos blo();

	public CaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	
}
