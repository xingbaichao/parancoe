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
 * Controller for jugger administration functionalities.
 * @author Enrico Giurin
 *
 */
public abstract class JuggerAdminController extends BaseMultiActionController {
	 private static Logger logger = Logger.getLogger(JuggerAdminController.class);

	/* (non-Javadoc)
	 * @see org.parancoe.web.BaseMultiActionController#getLogger()
	 */
	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}
	
	/**
	 * List all juggers.
	 * @param req
	 * @param res
	 * @return
	 */
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res){
        ModelAndView mv = new ModelAndView("jugger/admin/list");
        mv.addObject("juggers", blo().getJuggerBO().retrieveJuggers());
        return mv;
    }
	
	
	/**
	 * Jugger details
	 * @param req
	 * @param res
	 * @return
	 */
	public ModelAndView viewJugger(HttpServletRequest req, HttpServletResponse res){
        String username = req.getParameter("username");        
		ModelAndView mv = new ModelAndView("jugger/admin/viewJugger");
        mv.addObject("jugger", dao().getJuggerDao().searchByUsername(username).get(0));
        return mv;
    }
	
	
	public ModelAndView enableJugger(HttpServletRequest req, HttpServletResponse res){
		String username = req.getParameter("username");       
        blo().getJuggerBO().enableJugger(username);
        ModelAndView mv = new ModelAndView("jugger/admin/list");
		mv.addObject("juggers", blo().getJuggerBO().retrieveJuggers());
        return mv;
    }
	
	
	public ModelAndView disableJugger(HttpServletRequest req, HttpServletResponse res){
		String username = req.getParameter("username");
		blo().getJuggerBO().disableJugger(username);
		ModelAndView mv = new ModelAndView("jugger/admin/list");
		mv.addObject("juggers", blo().getJuggerBO().retrieveJuggers());
        return mv;
    }
	
	
	
	
	
	
	
		
	
	
   
  

	
	 	protected abstract Daos dao();
	    protected abstract Blos blo();
}
