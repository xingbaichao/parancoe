package it.jugpadova.dao;

import it.jugpadova.Daos;
import it.jugpadova.JugEventsBaseTest;
import it.jugpadova.po.Jugger;
import java.util.List;

/**
 *
 * @author lucio
 */
public class JuggerDaoTest extends JugEventsBaseTest {

    private JuggerDao juggerDao;

    public JuggerDaoTest() {
        Daos daos = (Daos) ctx.getBean("daos");
        juggerDao = daos.getJuggerDao();
    }

    public void testFindByPartialJugNameAndCountryAndContinent() {
        List<Jugger> juggers = juggerDao.findByPartialJugNameAndCountryAndContinent("%J%", "%Ital%", "%Eur%");
        assertSize(2, juggers);
    }

    public void testFindAll() {
        List<Jugger> juggers = juggerDao.findAll();
        assertSize(2, juggers);
    }
}
