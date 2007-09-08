/**
 * 
 */
package it.jugpadova.dao;

import it.jugpadova.po.Jugger;

import java.util.List;

import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * @author Admin
 * 
 */
@Dao(entity = Jugger.class)
public interface JuggerDao extends GenericDao<Jugger, Long> {

	public List<Jugger> findByPartialJugNameAndCountryAndContinent(
			String juggerName, String countryLocalName, String continent);

	Jugger searchByUsername(String username);

	Jugger findByConfirmationCode(String confirmationCode);

	List<Jugger> findAllOrderByUsername();
}
