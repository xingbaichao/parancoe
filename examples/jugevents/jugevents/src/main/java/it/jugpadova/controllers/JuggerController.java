/**
 * 
 */
package it.jugpadova.controllers;

import it.jugpadova.Blos;
import it.jugpadova.Daos;

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

	
	 	protected abstract Daos dao();
	    protected abstract Blos blo();
}
