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
public class TrustBoTest extends JugEventsBaseTest {
	private TrustBo trustBo;

	private Daos daos;

	public TrustBoTest() {

		trustBo = (TrustBo) ctx.getBean("trustBo");
		daos = (Daos) ctx.getBean("daos");
	}

	public void testRequireReliability() {
		JuggerDao juggerDao = daos.getJuggerDao();
		Jugger jugger = juggerDao.searchByUsername("enrico");
		trustBo
				.requireReliability(
						jugger,
						"I am one of the leaders of JUG Padova, "
								+ "thereby I ask grant in order to edit informations about that JUG");
		jugger = juggerDao.searchByUsername("enrico");
		assertEquals(ReliabilityRequest.RELIABILITY_REQUIRED, jugger.getReliabilityRequest().getStatus());
	}

}
