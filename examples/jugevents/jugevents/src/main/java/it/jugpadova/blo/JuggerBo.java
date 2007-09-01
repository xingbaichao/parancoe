/**
 * 
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.JUGDao;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.exception.UserAlreadyEnabledException;
import it.jugpadova.exception.UserAlreadyPresentsException;
import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.acegisecurity.Authentication;
import org.acegisecurity.providers.encoding.MessageDigestPasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;

import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.AuthorityDao;
import org.parancoe.plugins.security.SecureUtility;
import org.parancoe.plugins.security.UserAuthority;
import org.parancoe.plugins.security.UserAuthorityDao;
import org.parancoe.plugins.security.UserDao;
import org.parancoe.plugins.world.Continent;
import org.parancoe.plugins.world.Country;
import org.parancoe.plugins.world.CountryDao;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Defines business methods for Jugger entity.
 * @author Enrico Giurin
 *
 */
public class JuggerBo {
	private static final Logger logger = Logger.getLogger(JuggerBo.class);
	
	private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String confirmationSenderEmailAddress;
	
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
    public JuggerBo()
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
    public void save(Jugger jugger, String baseUrl) throws Exception {
    	
    	//retrieves all dao    	
    	CountryDao  countryDao = daos.getCountryDao();
    	UserDao userDao = daos.getUserDao();
    	AuthorityDao authorityDao = daos.getAuthorityDao();
    	UserAuthorityDao userAuthorityDao = daos.getUserAuthorityDao();
    	JuggerDao juggerDao = daos.getJuggerDao();	
    	JUGDao jugDao = daos.getJUGDao();
    	
    	String username = jugger.getUser().getUsername();
    
      //check if username is already presents  
      if((juggerDao.searchByUsername(username).size() > 0)
    	  || (userDao.findByUsername(username).size() > 0))
      {
    	 
    	  throw new UserAlreadyPresentsException("Jugger with username: "+username+ " already presents in the database!");
      }
      
     //set authority to jugger
      Authority authority = authorityDao.findByRole("ROLE_JUGGER").get(0);
      //create the user
      userDao.create(SecureUtility.newUserToValidate(username));
      UserAuthority ua = new UserAuthority();
      ua.setAuthority(authority);
      ua.setUser(userDao.findByUsername(jugger.getUser().getUsername()).get(0));
      userAuthorityDao.create(ua);
      
      //create or find JUG
      JUG jug = null;
      List<JUG> jugs = jugDao.findByName(jugger.getJug().getName());
      if(jugs.size() ==  0)
      {
    	  //create the JUG instance
    	  jug = new JUG();
    	  jug.setName(jugger.getJug().getName());    	 
    	  jug.setCountry(countryDao.findByEnglishName(jugger.getJug().getCountry().getEnglishName()));
    	  jugDao.create(jug);
      }
      else
      {
    	  //get the value selected
    	  jug = jugs.get(0);
    	  jug.setCountry(countryDao.findByEnglishName(jugger.getJug().getCountry().getEnglishName()));
          jugDao.createOrUpdate(jug);
      }
      
      //assign values to jugger
      jugger.setJug(jug);      
      jugger.setUser(userDao.findByUsername(jugger.getUser().getUsername()).get(0));      
      jugger.setConfirmationCode(generateConfirmationCode(jugger));
      juggerDao.createOrUpdate(jugger);
      sendConfirmationEmail(jugger, baseUrl);
      logger.info("Jugger ("+username+") created with success");    	
    }
    
    
    private String generateConfirmationCode(Jugger jugger) {
        return new MessageDigestPasswordEncoder("MD5", true).encodePassword(jugger.getFirstName() +
        		jugger.getLastName() +
        		jugger.getEmail(), new Date());
    }
    
    @Transactional
    public Jugger enableJugger(String confirmationCode, String password) throws UserAlreadyEnabledException
    {
    	 Jugger jugger = daos.getJuggerDao().findByConfirmationCode(confirmationCode).get(0);
         if((jugger.getConfirmed()!=null)&&(jugger.getConfirmed().booleanValue()))
         {
        	 throw new UserAlreadyEnabledException("User "+jugger.getUser().getUsername()+" already enabled");
         }
    	 jugger.getUser().setEnabled(true);
         jugger.setConfirmed(true);
         jugger.getUser().setPassword(password);
         daos.getJuggerDao().update(jugger);
         logger.info("Username "+jugger.getUser().getUsername()+" enabled to jugevents");
         return jugger;
    }
    
    
    //send mail to new jugger for application
    private void sendConfirmationEmail(final Jugger jugger, final String baseUrl) {
        MimeMessagePreparator preparator =
                new MimeMessagePreparator() {

            @SuppressWarnings(value = "unchecked")
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message =
                        new MimeMessageHelper(mimeMessage);
                message.setTo(jugger.getEmail());
                message.setFrom(confirmationSenderEmailAddress);
                message.setSubject("Please confirm jugger registration");
                Map model = new HashMap();
                model.put("jugger", jugger);
                model.put("baseUrl", baseUrl);
                model.put("confirmationCode",
                        URLEncoder.encode(jugger.getConfirmationCode(),
                        "UTF-8"));
                model.put("email",
                        URLEncoder.encode(jugger.getEmail(), "UTF-8"));
                String text =
                        VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "it/jugpadova/jugger-registration-confirmation.vm", model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
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
    public List findPartialCountry(String partialCountry) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialCountry)) {
            try {
                List<Country> countries =
                        getDaos().getCountryDao().findByPartialEnglishName(partialCountry+"%");
                Iterator<Country> itCountries = countries.iterator();
                while (itCountries.hasNext()) {
                    Country country = itCountries.next();
                    result.add(country.getEnglishName());
                }
            } catch (Exception e) {
                logger.error("Error completing the country", e);
            }
        }
        return result;
    }
    
    
    @Transactional(readOnly = true)
    public List findPartialJugNameWithCountry(String partialJugName, String partialCountry) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialJugName)) {
            try {
                List<JUG> jugs =
                        getDaos().getJUGDao().findByPartialJugNameAndCountry(partialJugName+"%",partialCountry+"%");
                Iterator<JUG> itJugs = jugs.iterator();
                while (itJugs.hasNext()) {
                	JUG jug = itJugs.next();
                    result.add(jug.getName());
                }
            } catch (Exception e) {
                logger.error("Error completing the JUG Name", e);
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
                    result.add(jugger.getJug().getName());
                }
            } catch (Exception e) {
                logger.error("Error completing the JUG Name", e);
            }
        }
        return result;
    }
    
    @Transactional
    public void disableJugger(String username)
    {
    	JuggerDao juggerDao = daos.getJuggerDao();   	
    	Jugger jugger = juggerDao.searchByUsername(username).get(0);
    	jugger.getUser().setEnabled(false);
    	logger.info("User: "+username+" has been disabled");
    	
    }
    
    
    @Transactional
    public void enableJugger(String username)
    {
    	JuggerDao juggerDao = daos.getJuggerDao();   	
    	Jugger jugger = juggerDao.searchByUsername(username).get(0);
    	jugger.getUser().setEnabled(true);
    	logger.info("User: "+username+" has been enabled");
    	
    }

	public String getConfirmationSenderEmailAddress() {
		return confirmationSenderEmailAddress;
	}

	public void setConfirmationSenderEmailAddress(
			String confirmationSenderEmailAddress) {
		this.confirmationSenderEmailAddress = confirmationSenderEmailAddress;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
    
    
    
    
    
    
    
    
}
