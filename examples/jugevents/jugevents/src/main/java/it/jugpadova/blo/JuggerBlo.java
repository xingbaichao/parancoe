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
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import org.acegisecurity.Authentication;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.AuthorityDao;
import org.parancoe.plugins.security.UserAuthority;
import org.parancoe.plugins.security.UserAuthorityDao;
import org.parancoe.plugins.security.UserDao;
import org.parancoe.plugins.world.Continent;
import org.parancoe.plugins.world.Country;
import org.parancoe.plugins.world.CountryDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Admin
 *
 */
public class JuggerBlo {
	private static final Logger logger = Logger.getLogger(JuggerBlo.class);
	
    private Daos daos;
    
    public Daos getDaos() {
        return daos;
    }
    
    public void setDaos(Daos daos) {
        this.daos = daos;
    }
    
    /**
     * Constructor. Retrieves all daos
     *
     */
    public JuggerBlo()
    {
    	 
    }
    
    @Transactional(readOnly=true)
    public List<Jugger> retrieveJuggers() {
    	JuggerDao juggerDao = daos.getJuggerDao();	
        List<Jugger> juggers = juggerDao.findAll();
        
        return juggers;
    }
    
    private Jugger getCurrentJugger() {
    	JuggerDao juggerDao = daos.getJuggerDao();	
        Jugger result = null;
        Authentication authentication = org.acegisecurity.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();            
            result = juggerDao.searchByUsername(name).get(0);
        }
        return result;
    }
    
    @Transactional
    public void save(Jugger jugger) throws Exception {
    	
    	
    	
    	CountryDao  countryDao = daos.getCountryDao();
    	UserDao userDao = daos.getUserDao();
    	AuthorityDao authorityDao = daos.getAuthorityDao();
    	UserAuthorityDao userAuthorityDao = daos.getUserAuthorityDao();
    	JuggerDao juggerDao = daos.getJuggerDao();	
    	String username = jugger.getUser().getUsername();
    
      
      if(juggerDao.searchByUsername(username).size() > 0)
      {
    	  //username already in use
    	  throw new Exception("Jugger with username: "+username+ " already presents in the database!");
      }
      if(userDao.findByUsername(username).size() > 0)
      {
    	  //username already in use
    	  throw new Exception("username: "+username+ " already in use!");
      }
     
      //retrive country selected
      Country country = countryDao.findByIsoCode(jugger.getCountry().getIsoCode()).get(0);
      Authority authority = authorityDao.findByRole("ROLE_JUGGER").get(0);
      userDao.create(jugger.getUser());
      UserAuthority ua = new UserAuthority();
      ua.setAuthority(authority);
      ua.setUser(userDao.findByUsername(jugger.getUser().getUsername()).get(0));
      userAuthorityDao.create(ua);
      
      jugger.setCountry(country);
      jugger.setUser(userDao.findByUsername(jugger.getUser().getUsername()).get(0));
      
      
      juggerDao.createOrUpdate(jugger);
      logger.info("Jugger ("+username+") created with success");
    	
    }

    @Transactional(readOnly = true)
    public List findPartialContinent(String partialContinent) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialContinent)) {
            try {
                List<Continent> continents =
                        getDaos().getContinentDao().findByPartialName("%"+partialContinent+"%");
                Iterator<Continent> itContinents = continents.iterator();
                while (itContinents.hasNext()) {
                    Continent continent = itContinents.next();
                    result.add(continent.getName());
                }
            } catch (Exception e) {
                logger.error("Error completing the continent", e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List findPartialCountryWithContinent(String partialCountry, String partialContinent) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialCountry)) {
            try {
                List<Country> countries =
                        getDaos().getCountryDao().findByPartialLocalNameAndContinent("%"+partialCountry+"%", "%"+partialContinent+"%");
                Iterator<Country> itCountries = countries.iterator();
                while (itCountries.hasNext()) {
                    Country country = itCountries.next();
                    result.add(country.getLocalName());
                }
            } catch (Exception e) {
                logger.error("Error completing the country", e);
            }
        }
        return result;
    }
    
    @Transactional(readOnly = true)
    public List findPartialJugNameWithCountryAndContinent(String partialJugName, String partialCountry, String partialContinent) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialJugName)) {
            try {
                List<Jugger> juggers =
                        getDaos().getJuggerDao().findByPartialJugNameAndCountryAndContinent("%"+partialJugName+"%","%"+partialCountry+"%", "%"+partialContinent+"%");
                Iterator<Jugger> itJuggers = juggers.iterator();
                while (itJuggers.hasNext()) {
                    Jugger jugger = itJuggers.next();
                    result.add(jugger.getJugName());
                }
            } catch (Exception e) {
                logger.error("Error completing the JUG Name", e);
            }
        }
        return result;
    }
    
}
