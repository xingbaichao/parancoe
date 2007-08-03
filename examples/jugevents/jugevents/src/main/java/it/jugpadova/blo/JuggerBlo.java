/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.EventDao;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.po.Event;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.Participant;

import java.util.List;
import org.acegisecurity.Authentication;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 *
 */
public class JuggerBlo {
	
private Daos daos;
    
    public Daos getDaos() {
        return daos;
    }
    
    public void setDaos(Daos daos) {
        this.daos = daos;
    }
    
    @Transactional(readOnly=true)
    public List<Jugger> retrieveJuggers() {
        List<Jugger> juggers = getDaos().getJuggerDao().findAll();
        
        return juggers;
    }
    
    private Jugger getCurrentJugger() {
        Jugger result = null;
        Authentication authentication = org.acegisecurity.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            JuggerDao juggerDao = getDaos().getJuggerDao();
            result = juggerDao.findByUsername(name).get(0);
        }
        return result;
    }
    
}
