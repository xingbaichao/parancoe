/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.EventDao;
import it.jugpadova.po.Event;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.Participant;

import java.util.List;

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
    
    
}
