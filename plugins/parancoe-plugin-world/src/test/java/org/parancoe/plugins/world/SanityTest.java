package org.parancoe.plugins.world;

import java.util.List;
import javax.annotation.Resource;
import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.test.PluginTest;
import org.parancoe.web.plugin.WebPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends PluginTest {

    @Autowired
    @Qualifier("pluginWorldConfig")
    private WebPlugin plugin;
    
    @Autowired
    @Qualifier("applicationContextPluginWorldConfig")
    private ApplicationContextPlugin contextPlugin;
    
    @Resource
    CountryDao countryDao;
    
    @Resource
    ContinentDao continentDao;

    public SanityTest() {
    }

    
    public void testPlugin() throws Exception {
        assertEquals(2, contextPlugin.getFixtureClasses().size());
        assertEquals(Continent.class, contextPlugin.getFixtureClasses().get(0));
        assertEquals(Country.class, contextPlugin.getFixtureClasses().get(1));
    }

    public void testContinentAssigned() {
        List<Country> countries = countryDao.findAll();
        assertNotEmpty(countries);
        for (Country c : countries) {
            assertNotNull(c.getContinent());
        }
    }

    public void testContinentSize() {
        List<Continent> continents = continentDao.findAll();
        assertSize(7, continents);
    }


    public void testFindAllOrderedByEnglishNameAsc() {
        List<Country> countries = countryDao.findByOrderByEnglishName();
        assertEquals(countries.get(0).getEnglishName(), "Albania");
    }


    public void testCountryFindByIsocode() {
        Country country = countryDao.findByIsoCode("IQ");
        assertEquals(country.getEnglishName(), "Iraq");
    }

    public void testCountrySize() {
        List<Country> countries = countryDao.findAll();
        assertSize(89, countries);
    }

    public void testContinentFindByName() {
        Continent europe = continentDao.findByName("Europe");
        assertNotNull(europe);
        assertEquals("Europe", europe.getName());
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Continent.class, Country.class};
    }
}
