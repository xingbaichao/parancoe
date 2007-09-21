/**
 *
 */
package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.JuggerDao;
import it.jugpadova.exception.EmailAlreadyPresentException;
import it.jugpadova.exception.ParancoeAccessDeniedException;
import it.jugpadova.exception.UserAlreadyEnabledException;
import it.jugpadova.exception.UserAlreadyPresentsException;
import it.jugpadova.exception.UserNotEnabledException;
import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.acegisecurity.Authentication;
import org.acegisecurity.providers.encoding.MessageDigestPasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.proxy.scriptaculous.Effect;
import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.AuthorityDao;
import org.parancoe.plugins.security.SecureUtility;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.security.UserAuthority;
import org.parancoe.plugins.security.UserAuthorityDao;
import org.parancoe.plugins.security.UserDao;
import org.parancoe.plugins.world.Continent;
import org.parancoe.plugins.world.Country;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Defines business methods for Jugger entity.
 *
 * @author Enrico Giurin
 *
 */
public class JuggerBo {
	/**
	 * min value for threshold access.
	 */
	public static final double MIN_THRESHOLD_ACCESS = 0d;
	/**
	 * Max value for threshold access
	 */
	public static final double MAX_THRESHOLD_ACCESS = 1d;

    private static final Logger logger =
            Logger.getLogger(JuggerBo.class);

    private JavaMailSender mailSender;

    private VelocityEngine velocityEngine;

    private String confirmationSenderEmailAddress;
    
    private  double thresholdAccess;

    private Daos daos;

    private JugBo jugBo;

    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
    }

    public JugBo getJugBo() {
        return jugBo;
    }

    public void setJugBo(JugBo jugBo) {
        this.jugBo = jugBo;
    }

    private Jugger getCurrentJugger() {
        JuggerDao juggerDao = daos.getJuggerDao();
        Jugger result = null;
        Authentication authentication =
                org.acegisecurity.context.SecurityContextHolder.getContext().
                getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            result = juggerDao.searchByUsername(name);
        }
        return result;
    }

    /**
     * Creates and persists new Jugger.
     *
     * @param jugger
     * @param baseUrl
     *            for confirmation mail
     * @throws Exception
     */
    @Transactional
    public void newJugger(Jugger jugger, String baseUrl) throws EmailAlreadyPresentException, UserAlreadyPresentsException {
        // retrieves dao
        JuggerDao juggerDao = daos.getJuggerDao();
        // check if it exists yet a jugger with the same email
        Jugger prevJugger = juggerDao.findByEmail(jugger.getEmail());
        if (prevJugger != null) {
            throw new EmailAlreadyPresentException("An user tried to register with an email that exists yet");
        }
        // creates or updated jug associated to jugger
        JUG jug = jugBo.saveJUG(jugger,thresholdAccess);
        // assign values to jugger
        jugger.setJug(jug);
        jugger.setUser(newUser(jugger.getUser().getUsername()));
        jugger.setConfirmationCode(generateConfirmationCode(jugger));
        juggerDao.create(jugger);
        sendEmail(jugger, baseUrl, "Please Confirm your Jugger registration",
                jugger.getConfirmationCode(),
                "it/jugpadova/jugger-registration-confirmation.vm");
        logger.info("Jugger (" + jugger.getUser().getUsername() +
                ") has been created with success");
    }

    /**
     * Send mail to the user for changing password.
     *
     * @param jugger
     * @param baseUrl
     * @throws Exception
     */
    @Transactional
    public void passwordRecovery(Jugger jugger, String baseUrl) throws Exception {
        jugger.setChangePasswordCode(generateConfirmationCode(jugger));
        daos.getJuggerDao().update(jugger);
        // send mail to user
        sendEmail(jugger, baseUrl, "Password Recovery",
                jugger.getChangePasswordCode(),
                "it/jugpadova/password-recovery.vm");
    }

    private String generateConfirmationCode(Jugger jugger) {
        return new MessageDigestPasswordEncoder("MD5", true).encodePassword(jugger.getFirstName() +
                jugger.getLastName() + jugger.getEmail(), new Date());
    }

    @Transactional
    public Jugger enableJugger(Jugger jugger, String password) throws UserAlreadyEnabledException {
        if (jugger.getUser().isEnabled()) {
            throw new UserAlreadyEnabledException("User " +
                    jugger.getUser().getUsername() + " already enabled");
        }
        jugger.getUser().setEnabled(true);
        jugger.getUser().setPassword(password);
        // one way code...so regenerate it
        jugger.setConfirmationCode(generateConfirmationCode(jugger));
        daos.getJuggerDao().update(jugger);
        logger.info("Username " + jugger.getUser().getUsername() +
                " enabled to jugevents");
        return jugger;
    }

    @Transactional
    public Jugger changePassword(Jugger jugger, String password) throws UserNotEnabledException {
        if (!jugger.getUser().isEnabled()) {
            throw new UserNotEnabledException("User " +
                    jugger.getUser().getUsername() + " is not enabled");
        }
        jugger.getUser().setPassword(password);
        // one way code...so regenerate it
        jugger.setChangePasswordCode(generateConfirmationCode(jugger));
        daos.getJuggerDao().update(jugger);
        logger.info("User " + jugger.getUser().getUsername() +
                " changed its password");
        return jugger;
    }

    @Transactional(readOnly = true)
    public List findPartialContinent(String partialContinent) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialContinent)) {
            try {
                List<Continent> continents =
                        getDaos().getContinentDao().
                        findByPartialName("%" + partialContinent + "%");
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
    public List findPartialCountryWithContinent(String partialCountry,
            String partialContinent) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialCountry)) {
            try {
                List<Country> countries =
                        getDaos().getCountryDao().
                        findByPartialLocalNameAndContinent("%" + partialCountry +
                        "%", "%" + partialContinent + "%");
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
                        getDaos().getCountryDao().
                        findByPartialEnglishName(partialCountry + "%");
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
    public List findPartialJugNameWithCountry(String partialJugName,
            String partialCountry) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialJugName)) {
            try {
                List<JUG> jugs =
                        getDaos().getJUGDao().
                        findByPartialJugNameAndCountry("%" + partialJugName + "%",
                        "%" + partialCountry + "%");
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
    public List findPartialJugNameWithCountryAndContinent(String partialJugName,
            String partialCountry, String partialContinent) {
        List<String> result = new ArrayList<String>();
        if (!StringUtils.isBlank(partialJugName)) {
            try {
                List<JUG> jugs =
                        getDaos().getJUGDao().
                        findByPartialJugNameAndCountryAndContinent("%" +
                        partialJugName + "%", "%" + partialCountry + "%",
                        "%" + partialContinent + "%");
                for (JUG jug : jugs) {
                    result.add(jug.getName());
                }
            } catch (Exception e) {
                logger.error("Error completing the JUG Name", e);
            }
        }
        return result;
    }

    @Transactional
    public void disableJugger(String username) {
        JuggerDao juggerDao = daos.getJuggerDao();
        Jugger jugger = juggerDao.searchByUsername(username);
        jugger.getUser().setEnabled(false);
        logger.info("User: " + username + " has been disabled");
    }

    @Transactional
    public void enableJugger(String username) {
        JuggerDao juggerDao = daos.getJuggerDao();
        Jugger jugger = juggerDao.searchByUsername(username);
        jugger.getUser().setEnabled(true);
        logger.info("User: " + username + " has been enabled");
    }

    public String getConfirmationSenderEmailAddress() {
        return confirmationSenderEmailAddress;
    }

    public void setConfirmationSenderEmailAddress(String confirmationSenderEmailAddress) {
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

    @Transactional(readOnly = true)
    public void populateJugFields(String jugName) {
        JUG jug = daos.getJUGDao().findByName(jugName);
        if (jug != null) {
            WebContext wctx = WebContextFactory.get();
            ScriptSession session = wctx.getScriptSession();
            Util util = new Util(session);
            Effect effect =
                    new Effect(session);
            Country country = jug.getCountry();
            if (country != null) {
                util.setValue("jugger.jug.country.englishName",
                        country.getEnglishName());
                effect.highlight("jugger.jug.country.englishName");
            }
            util.setValue("jugger.jug.webSite", jug.getWebSite());
            effect.highlight("jugger.jug.webSite");
            if (jug.getLongitude() != null) {
                util.setValue("jugger.jug.longitude",
                        jug.getLongitude().toString());
                effect.highlight("jugger.jug.longitude");
            }
            if (jug.getLatitude() != null) {
                util.setValue("jugger.jug.latitude",
                        jug.getLatitude().toString());
                effect.highlight("jugger.jug.latitude");
            }
            if (jug.getInfos() != null) {
                util.setValue("jugger.jug.infos",
                        jug.getInfos());
                effect.highlight("jugger.jug.infos");
            }
        }
    }

    /**
     * Updates Jugger and its childs.
     *
     * @param jugger
     */
    @Transactional
    public void update(Jugger jugger) {

        JuggerDao juggerDao = daos.getJuggerDao();

        User newUser = updateUser(jugger.getUser());
        
        JUG newJUG = jugBo.saveJUG(jugger, thresholdAccess);
        jugger.setJug(newJUG);
        jugger.setUser(newUser);
        juggerDao.update(jugger);
        logger.info("Updated Jugger with id " + jugger.getId());
    }

    @Transactional
    public User newUser(String username) throws UserAlreadyPresentsException {
        UserDao userDao = daos.getUserDao();
        AuthorityDao authorityDao = daos.getAuthorityDao();
        UserAuthorityDao userAuthorityDao = daos.getUserAuthorityDao();

        Authority authority = authorityDao.findByRole("ROLE_JUGGER");
        User userToValidate = null;
        Long id = null;
        UserAuthority ua = new UserAuthority();

        // check if username is already presents
        if (userDao.findByUsername(username).size() > 0) {

            throw new UserAlreadyPresentsException("User with username: " +
                    username + " already presents in the database!");
        }

        // set authority to jugger
        userToValidate =
                SecureUtility.newUserToValidate(username);
        // create the user
        id = userDao.create(userToValidate);
        userToValidate.setId(id);
        ua.setAuthority(authority);
        ua.setUser(userToValidate);
        userAuthorityDao.create(ua);

        return userToValidate;
    }

    @Transactional
    public User updateUser(User newUser) {
        UserDao userDao = daos.getUserDao();

        User user = userDao.findByUsername(newUser.getUsername()).get(0);
        if (user.getPassword().equals(newUser.getPassword())) {
            // we only
// update the
            // password
            return user;
        }
        user.setPassword(newUser.getPassword());
        userDao.update(user);
        logger.info("User " + user.getUsername() + " has been updated");
        return user;
    } // end of method

    @Transactional
    public void checkAuthorization(String username) {

        Authentication authentication =
                org.acegisecurity.context.SecurityContextHolder.getContext().
                getAuthentication();
        String name = null;

        if (authentication != null && authentication.isAuthenticated()) {
            name = authentication.getName();
            if (username.equals(name)) {
                return;
            }
        } // end of if
        // is the role_admin role?
        boolean isAdmin = false;
        User currentUser = daos.getUserDao().findByUsername(name).get(0);
        List<UserAuthority> userAuthorities = currentUser.getUserAuthority();
        for (UserAuthority userAuthority : userAuthorities) {
            if ("ROLE_ADMIN".equals(userAuthority.getAuthority().getRole())) {
                isAdmin = true;
                break;
            }
        }
        if (!isAdmin) {
            throw new ParancoeAccessDeniedException("You are not authorized on this Jugger.");
        } // end of if
    } // end of method

    @Transactional
    public void delete(String username) {
        Jugger jugger = daos.getJuggerDao().searchByUsername(username);

        if (jugger == null) {
            throw new IllegalArgumentException("No jugger with username " +
                    username);
        }
        User user = jugger.getUser();
        // remove user and all user authority
        List<UserAuthority> list = user.getUserAuthority();
        for (UserAuthority ua : list) {
            daos.getUserAuthorityDao().delete(ua);
        }
        daos.getUserDao().delete(user);
        daos.getJuggerDao().delete(jugger);
        // verify if jugger has been deleted
        jugger = daos.getJuggerDao().searchByUsername(username);
        if (jugger == null) {
            logger.info("Jugger with username: " + username + " has been deleted");
            return;
        }
        throw new RuntimeException("There were some problems deleting jugger: " +
                username);
    }

    /**
     * General jugger mail sender
     *
     * @param jugger
     * @param baseUrl
     * @param subject
     * @param oneWayCode
     * @param template
     */
    private void sendEmail(final Jugger jugger, final String baseUrl,
            final String subject, final String oneWayCode,
            final String template) {
        MimeMessagePreparator preparator =
                new MimeMessagePreparator() {

            @SuppressWarnings(value = "unchecked")
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message =
                        new MimeMessageHelper(mimeMessage);
                message.setTo(jugger.getEmail());
                message.setFrom(confirmationSenderEmailAddress);
                message.setSubject(subject);
                Map model = new HashMap();
                model.put("jugger", jugger);
                model.put("baseUrl", baseUrl);
                model.put("oneWayCode",
                        URLEncoder.encode(oneWayCode, "UTF-8"));
                model.put("username",
                        URLEncoder.encode(jugger.getUser().getUsername(),
                        "UTF-8"));
                String text =
                        VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        template, model);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

	public  double getThresholdAccess() {
		return thresholdAccess;
	}

	public  void setThresholdAccess(double thresholdAccess) {		
		this.thresholdAccess = thresholdAccess;
	}
	
	
} // end of class
