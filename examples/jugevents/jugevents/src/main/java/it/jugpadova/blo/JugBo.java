package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.JUGDao;
import it.jugpadova.po.JUG;
import it.jugpadova.po.Jugger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parancoe.plugins.world.Continent;
import org.parancoe.plugins.world.ContinentDao;
import org.parancoe.plugins.world.Country;
import org.parancoe.plugins.world.CountryDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author lucio
 */
public class JugBo {

    private static final Logger logger = Logger.getLogger(JugBo.class);
    private static final String EARTH_NAMESPACE = "http://earth.google.com/kml/2.1";
    private Daos daos;
    private String defaultKmlUrl;
    private ServicesBo servicesBo;

    public JugBo() {
    }

    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
    }

    public String getDefaultKmlUrl() {
        return defaultKmlUrl;
    }

    public void setDefaultKmlUrl(String defaultKmlUrl) {
        this.defaultKmlUrl = defaultKmlUrl;
    }

    /**
	 * Update the list of JUGs taking data from a KML file.
	 * 
	 * @param kmlUrl
	 *            The URL of the KML file. If null, it's used the defaultKmlUrl.
	 * @return Log messages
	 */
    @Transactional
	public void updateJugList(String kmlUrl) throws Exception {
        logger.info("Update JUG List started...");
        logger.info("kmlUrl = " + kmlUrl);
        if (kmlUrl == null) {
            kmlUrl = this.defaultKmlUrl;
            logger.info("Using defaultKmlUrl: " + this.defaultKmlUrl);
        }
        ContinentDao continentDao = getDaos().getContinentDao();
        CountryDao countryDao = getDaos().getCountryDao();
        JUGDao jugDao = getDaos().getJUGDao();
        Builder parser = new Builder();
        Document doc = parser.build(kmlUrl);
        Element kml = doc.getRootElement();
        Element document = kml.getFirstChildElement("Document", EARTH_NAMESPACE);
        Elements continents = document.getChildElements("NetworkLink",
                EARTH_NAMESPACE);
        for (int i = 0; i < continents.size(); i++) {
            Element continent = continents.get(i);
            String continentName = continent.getFirstChildElement("name",
                    EARTH_NAMESPACE).getValue();
            if ("Oceania".equals(continentName)) {
                continentName = "Australia";
                logger.info("Substituted continent name: Oceania with Australia");
            }
            Continent continentPo = continentDao.findByName(continentName);
            if (continentPo != null) {
                String continentUrl = continent.getFirstChildElement("Link", EARTH_NAMESPACE)
                        .getFirstChildElement("href", EARTH_NAMESPACE).getValue();                        
                logger.info("Loading " + continentName + " countries from "+continentUrl+".");
                Builder continentParser = new Builder();
                continentUrl = continentUrl.replaceFirst("http:", "https:");
                Document continentDoc = continentParser.build(continentUrl);
                Element continentKml = continentDoc.getRootElement();
                Elements countries = continentKml.getChildElements("Folder",
                        EARTH_NAMESPACE);
                for (int j = 0; j < countries.size(); j++) {
                    Element country = countries.get(j);
                    String countryName = country.getFirstChildElement("name",
                            EARTH_NAMESPACE).getValue();
                    Country countryPo = countryDao.findByEnglishName(countryName);
                    if (countryPo != null) {
                        logger.info("Loading " + countryName + " JUGs.");
                        Elements placemarks = country.getChildElements("Placemark", EARTH_NAMESPACE);
                        for (int k = 0; k < placemarks.size(); k++) {
                            Element placemark = placemarks.get(k);
                            String jugName = placemark.getFirstChildElement("name", EARTH_NAMESPACE).getValue();
                            String description = placemark.getFirstChildElement("description",
                                    EARTH_NAMESPACE).getValue();
                            Element point = placemark.getFirstChildElement("Point", EARTH_NAMESPACE);
                            String coordinatesStr = point.getFirstChildElement("coordinates", EARTH_NAMESPACE).getValue();
                            String[] coordinatesArr = coordinatesStr.split(",");
                            Double longitude = Double.parseDouble(coordinatesArr[0]);
                            Double latitude = Double.parseDouble(coordinatesArr[1]);
                            JUG jug = jugDao.findByName(jugName);
                            if (jug != null && jug.isModifiedKmlData() != null && jug.isModifiedKmlData().booleanValue()) {
                                logger.info("Skipping updating of " + jugName + " kml data, because yet modified through JUG Events.");
                            } else {
                                if (jug == null) {
                                    logger.info("Creating a new JUG: " + jugName);
                                    jug = new JUG();
                                    jug.setName(jugName);
                                } else {
                                    logger.info("Updating a JUG: " + jugName);
                                }
                                jug.setCountry(countryPo);
                                jug.setLongitude(longitude);
                                jug.setLatitude(latitude);
                                jug.setInfos(description);
                                jug.setModifiedKmlData(Boolean.FALSE);
                                jugDao.createOrUpdate(jug);
                            }
                        }
                    } else {
                        logger.warn("Country " + countryName + " not found in the database. Not loading its JUGs.");
                    }
                }
            } else {
                logger.warn("Continent " + continentName + " not found in the database. Not loading its JUGs.");
            }
        }
        logger.info("Update JUG List completed...");
    }

    @Transactional
	public Document buildKml() {
        Element kml = new Element("kml", EARTH_NAMESPACE);
        Element document = new Element("Document", EARTH_NAMESPACE);
        kml.appendChild(document);
        Element documentName = new Element("name", EARTH_NAMESPACE);
        documentName.appendChild("Java User Group International");
        Element documentDescription = new Element("description",
                EARTH_NAMESPACE);
        documentDescription.appendChild("\nGeographic location, leaders and web site information for JUGs from\n" + "around the world. For convenience, they are grouped by continent." + "Instructions for submitting new JUG entries can be found" + "<a href=\"http://wiki.java.net/bin/view/JUGs/JUG-MAP\">here</a>." + "<br/>&nbsp;<br/><img src=\"http://sv-web-jug.dev.java.net/images/jug_leaders_large.gif\"><br/>&nbsp;");
        document.appendChild(documentName);
        document.appendChild(documentDescription);
        List<Continent> continents = getDaos().getContinentDao().findByPartialName("%");
        for (Continent continent : continents) {
            Element continentFolder = null;
            List<Country> countries = continent.getCountries();
            for (Country country : countries) {
                Element countryFolder = null;
                List<JUG> jugs = daos.getJUGDao().findByPartialJugNameAndCountry("%",
                        country.getEnglishName());
                for (JUG jug : jugs) {
                    if (jug.getLongitude() != null && jug.getLatitude() != null) {
                        if (continentFolder == null) {
                            continentFolder = new Element("Folder",
                                    EARTH_NAMESPACE);
                            Element continentName = new Element("name",
                                    EARTH_NAMESPACE);
                            String continentNameText = continent.getName();
                            if ("Australia".equals(continentNameText)) {
                                continentNameText = "Oceania";
                            }
                            continentName.appendChild(continentNameText);
                            Element continentOpen = new Element("open",
                                    EARTH_NAMESPACE);
                            continentOpen.appendChild("1");
                            continentFolder.appendChild(continentName);
                            continentFolder.appendChild(continentOpen);
                            document.appendChild(continentFolder);
                        }
                        if (countryFolder == null) {
                            countryFolder = new Element("Folder",
                                    EARTH_NAMESPACE);
                            Element countryName = new Element("name",
                                    EARTH_NAMESPACE);
                            countryName.appendChild(country.getEnglishName());
                            Element countryOpen = new Element("open",
                                    EARTH_NAMESPACE);
                            countryOpen.appendChild("0");
                            countryFolder.appendChild(countryName);
                            countryFolder.appendChild(countryOpen);
                            continentFolder.appendChild(countryFolder);
                        }
                        Element placemark = new Element("Placemark",
                                EARTH_NAMESPACE);
                        Element jugName = new Element("name", EARTH_NAMESPACE);
                        jugName.appendChild(jug.getName());
                        Element jugDescription = new Element("description",
                                EARTH_NAMESPACE);
                        jugDescription.appendChild("\n" + jug.getInfos() + "\n");
                        Element point = new Element("Point", EARTH_NAMESPACE);
                        Element coordinates = new Element("coordinates",
                                EARTH_NAMESPACE);
                        coordinates.appendChild(jug.getLongitude() + "," + jug.getLatitude() + ",0");
                        point.appendChild(coordinates);
                        placemark.appendChild(jugName);
                        placemark.appendChild(jugDescription);
                        placemark.appendChild(point);
                        countryFolder.appendChild(placemark);
                    }
                }
            }
        }
        return new Document(kml);
    }

    @Transactional
	public JUG saveJUG(Jugger jugger) {
        JUG newJUG = jugger.getJug();
        JUGDao jugDao = daos.getJUGDao();
        CountryDao countryDao = daos.getCountryDao();
        // create or find JUG
        JUG jug = jugDao.findByName(newJUG.getName());
        if (jug == null) {
            // create the JUG instance
            jug = new JUG();
        } else {
            // check if this jugger could update the JUG attribute
            if (!servicesBo.isJuggerReliable(jugger.getReliability())) {
                logger.warn("Jugger " + jugger.getUser().getUsername() + " is not reliable!");
                return jug;
            }// end of if
        }// end of if

        jug.setModifiedKmlData(evaluateModifiedKmlDate(newJUG, jug));
        jug.setName(newJUG.getName());
        jug.setCountry(countryDao.findByEnglishName(newJUG.getCountry().getEnglishName()));
        jug.setWebSite(newJUG.getWebSite());
        jug.setLongitude(newJUG.getLongitude());
        jug.setLatitude(newJUG.getLatitude());
        jug.setInfos(newJUG.getInfos());
        Long id = jug.getId();
        if (id == null) {
            id = jugDao.create(jug);
            jug.setId(id);
            logger.info("JUG with name " + jug.getName() + " has been created");
        } else {
            jugDao.update(jug);
            logger.info("JUG with name " + jug.getName() + " has been updated");
        }
        return jug;
    }

    private Boolean evaluateModifiedKmlDate(JUG newJUG, JUG oldJUG) {
        return (newJUG.getLongitude() != null && !newJUG.getLongitude().equals(oldJUG.getLongitude())) || (newJUG.getLatitude() != null && !newJUG.getLatitude().equals(oldJUG.getLatitude())) || (newJUG.getInfos() != null && !newJUG.getInfos().equals(oldJUG.getInfos()));
    }

    public ServicesBo getServicesBo() {
        return servicesBo;
    }

    public void setServicesBo(ServicesBo servicesBo) {
        this.servicesBo = servicesBo;
    }
}
