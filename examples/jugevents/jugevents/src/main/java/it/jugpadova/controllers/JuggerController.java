/**
 * 
 */
package it.jugpadova.controllers;

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.blo.EventBo;
import it.jugpadova.blo.JuggerBlo;
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
		return null;
	}
	
	
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res){
        ModelAndView mv = new ModelAndView("jugger/list");
        mv.addObject("juggers", blo().getJuggerBO().retrieveJuggers());
        return mv;
    }
	
	
	
	/**
    *
    */
   public ModelAndView registration(HttpServletRequest req,
           HttpServletResponse res) {
	   
       ModelAndView result = null;
       String confirmationCode = req.getParameter("code");
       logger.info("confirmationCode: "+confirmationCode);
       Jugger jugger = dao().getJuggerDao().findByConfirmationCode(confirmationCode).get(0);
       result =new ModelAndView("jugger/registration/setpwd");
       result.addObject("jugger", jugger);       
       return result;
   }
   
   
   
   /**
   *
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
		return new ModelAndView("jugger/registration/failed");
	}
      
     return new ModelAndView("jugger/registration/ok");
       
     
  }

	
	 	protected abstract Daos dao();
	    protected abstract Blos blo();
}
