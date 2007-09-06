package it.jugpadova.blo;

import it.jugpadova.Daos;
import it.jugpadova.dao.JUGDao;
import it.jugpadova.po.JUG;
import java.util.ArrayList;
import java.util.List;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
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

    private static final Logger logger =
            Logger.getLogger(JugBo.class);
    private Daos daos;
    private String defaultKmlUrl;

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
     * @param kmlUrl The URL of the KML file. If null, it's used the defaultKmlUrl.
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
        Document doc =
                parser.build(kmlUrl);
        Element kml = doc.getRootElement();
        Element document =
                kml.getFirstChildElement("Document",
                "http://earth.google.com/kml/2.1");
        Elements continents =
                document.getChildElements("Folder",
                "http://earth.google.com/kml/2.1");
        for (int i = 0; i < continents.size(); i++) {
            Element continent = continents.get(i);
            String continentName =
                    continent.getFirstChildElement("name",
                    "http://earth.google.com/kml/2.1").getValue();
            if ("Oceania".equals(continentName)) {
                continentName = "Australia";
                logger.info("Substituted continent name: Oceania with Australia");
            }
            Continent continentPo = continentDao.findByName(continentName);
            if (continentPo != null) {
                logger.info("Loading " + continentName + " countries.");
                Elements countries =
                        continent.getChildElements("Folder",
                        "http://earth.google.com/kml/2.1");
                for (int j = 0; j < countries.size(); j++) {
                    Element country = countries.get(j);
                    String countryName =
                            country.getFirstChildElement("name",
                            "http://earth.google.com/kml/2.1").getValue();
                    Country countryPo = countryDao.findByEnglishName(countryName);
                    if (countryPo != null) {
                        logger.info("Loading " + countryName + " JUGs.");
                        Elements placemarks =
                                country.getChildElements("Placemark",
                                "http://earth.google.com/kml/2.1");
                        for (int k = 0; k < placemarks.size(); k++) {
                            Element placemark = placemarks.get(k);
                            String jugName =
                                    placemark.getFirstChildElement("name",
                                    "http://earth.google.com/kml/2.1").
                                    getValue();
                            String description =
                                    placemark.getFirstChildElement("description",
                                    "http://earth.google.com/kml/2.1").
                                    getValue();
                            Element point =
                                    placemark.getFirstChildElement("Point",
                                    "http://earth.google.com/kml/2.1");
                            String coordinatesStr =
                                    point.getFirstChildElement("coordinates",
                                    "http://earth.google.com/kml/2.1").
                                    getValue();
                            String[] coordinatesArr = coordinatesStr.split(",");
                            Double longitude =
                                    Double.parseDouble(coordinatesArr[0]);
                            Double latitude =
                                    Double.parseDouble(coordinatesArr[1]);
                            JUG jug = jugDao.findByName(jugName);
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
                            jugDao.createOrUpdate(jug);
                        }
                    } else {
                        logger.warn("Country " + countryName +
                                " not found in the database. Not loading its JUGs.");
                    }
                }
            } else {
                logger.warn("Continent " + continentName +
                        " not found in the database. Not loading its JUGs.");
            }
        }
        logger.info("Update JUG List completed...");
    }
    
    @Transactional
    public JUG save(JUG newJUG)
    {   
    	JUGDao jugDao = daos.getJUGDao();
    	CountryDao countryDao = daos.getCountryDao();
//    	create or find JUG
        JUG jug = jugDao.findByName(newJUG.getName());
        if (jug == null) 
        	{
            //create the JUG instance
            jug = new JUG();                     
        	}
            jug.setName(newJUG.getName());
            jug.setCountry(countryDao.findByEnglishName(newJUG.getCountry().getEnglishName()));
            jug.setWebSite(newJUG.getWebSite());
            jug.setLongitude(newJUG.getLongitude());
            jug.setLatitude(newJUG.getLatitude());
            Long id = jug.getId(); 
            if(id == null)
            {     id = jugDao.create(jug);
                  jug.setId(id);
            	  logger.info("JUG with name "+jug.getName()+" has been created");
            }
            else
            {   jugDao.update(jug);
            	logger.info("JUG with name "+jug.getName()+" has been updated");
            }
            return jug;
          
    		}//end of method
    
}//end of class
