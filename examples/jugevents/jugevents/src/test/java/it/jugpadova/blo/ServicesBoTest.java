/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.JugEventsBaseTest;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.po.Jugger;
import it.jugpadova.po.ReliabilityRequest;

/**
 * @author Enrico
 * 
 */
public class ServicesBoTest extends JugEventsBaseTest {
	private ServicesBo servicesBo;

	private Daos daos;

	public ServicesBoTest() {

		servicesBo = (ServicesBo) ctx.getBean("servicesBo");
		daos = (Daos) ctx.getBean("daos");
	}

	public void testRequireReliability() {
		//TODO change the test
		/*
		JuggerDao juggerDao = daos.getJuggerDao();
		Jugger jugger = juggerDao.searchByUsername("enrico");
		servicesBo.requireReliabilityOnExistingJugger(
						"enricogiurin@gmail.com",
						"I am one of the leaders of JUG Padova, "
								+ "thereby I ask grant in order to edit informations about that JUG");
		jugger = juggerDao.searchByUsername("enrico");
		assertEquals(ReliabilityRequest.RELIABILITY_REQUIRED, jugger.getReliabilityRequest().getStatus());
		*/
	}

}
