/**
 * 
 */
package it.jugpadova.controllers;

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.blo.EventBo;
import it.jugpadova.blo.JuggerBlo;
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
       JuggerBlo juggerBo = blo().getJuggerBO();
      // Jugger jugger = juggerBo
       /**
       EventBo eventBo = blo().getEventBo();
       Participant participant =
               eventBo.confirmParticipant(req.getParameter("email"),
               req.getParameter("code"));
       if (participant != null) {
           result =new ModelAndView("redirect:/confirm/ok.html");
           result.addObject("participantId", participant.getId());
       } else {
           result =new ModelAndView("redirect:/confirm/failed.html");
       }
       **/
       return result;
   }

	
	 	protected abstract Daos dao();
	    protected abstract Blos blo();
}
