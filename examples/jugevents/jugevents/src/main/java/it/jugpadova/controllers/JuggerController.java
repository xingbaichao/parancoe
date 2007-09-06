/**
 * 
 */
package it.jugpadova.controllers;

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.blo.EventBo;
import it.jugpadova.blo.JuggerBo;
import it.jugpadova.exception.UserAlreadyEnabledException;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.Participant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parancoe.web.BaseMultiActionController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Admin
 *
 */
public abstract class JuggerController extends BaseMultiActionController {
	 private static Logger logger = Logger.getLogger(JuggerController.class);

	/* (non-Javadoc)
	 * @see org.parancoe.web.BaseMultiActionController#getLogger()
	 */
	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}
	
	
	
	
		/**
	    * Message after jugger registration. Called with redirect, passing
	    * juggerId parameter. Copied by similar done by Lucio.
	    */
	   public ModelAndView sendMail(HttpServletRequest req,
	           HttpServletResponse res) {
	   	
	       Long juggerId =
	               new Long(req.getParameter("juggerId"));
	       Jugger jugger = dao().getJuggerDao().read(juggerId);       
	       ModelAndView mv =
	               new ModelAndView("jugger/registration/sentMail");
	       mv.addObject("jugger", jugger);
	       return mv;    	
	   }
	
	
	/**
	 * Sends to form for setting password
	 * @param req
	 * @param res
	 * @return
	 */
   public ModelAndView sendToFormPWD(HttpServletRequest req,
           HttpServletResponse res) {
	   
       ModelAndView result = null;
       String confirmationCode = req.getParameter("code");
       logger.info("confirmationCode: "+confirmationCode);
       Jugger jugger = dao().getJuggerDao().findByConfirmationCode(confirmationCode).get(0);
       //if(jugger ==)
       result =new ModelAndView("jugger/registration/setpwd");
       result.addObject("jugger", jugger);       
       return result;
   }
   
   
 /**
  * Rerieves password and enable jugger.
  * @param req
  * @param res
  * @return
  */
  public ModelAndView enableJugger(HttpServletRequest req,
          HttpServletResponse res) {
	   
      
      String confirmationCode = req.getParameter("confirmationCode");
      String password = req.getParameter("password");
      logger.info("confirmationCode: "+confirmationCode);
      try {
    	  blo().getJuggerBO().enableJugger(confirmationCode, password);
	} catch (Exception e) {
		logger.error(e,e);
		return new ModelAndView("redirect:/jugger/failed.html");
	}
      
     return new ModelAndView("redirect:/jugger/OK.html");      
     
  }
  
  
  public ModelAndView OK(HttpServletRequest req,
          HttpServletResponse res) {
	  return new ModelAndView("jugger/registration/ok");
  }
  
  public ModelAndView failed(HttpServletRequest req,
          HttpServletResponse res) {
	  return new ModelAndView("jugger/registration/failed");
  }
  
  public ModelAndView already(HttpServletRequest req,
          HttpServletResponse res) {
	  return new ModelAndView("jugger/registration/alreadyRegistered");
  }
  
  public ModelAndView confirmUpdateJugger(HttpServletRequest req,
          HttpServletResponse res) {
	  return new ModelAndView("jugger/confirmUpdateJugger");
  }
   
   
   
   
   
   
   
   
  

	
	 	protected abstract Daos dao();
	    protected abstract Blos blo();
}
