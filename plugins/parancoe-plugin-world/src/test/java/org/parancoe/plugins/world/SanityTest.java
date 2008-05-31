package org.parancoe.plugins.world;

import java.util.List;
import org.parancoe.web.test.PluginTest;
import org.parancoe.web.plugin.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends PluginTest {

    @Autowired
    @Qualifier("pluginWorldConfig")
    private Plugin plugin;
    
    @Autowired
    CountryDao countryDao;
    
    @Autowired
    ContinentDao continentDao;

    public SanityTest() {
    }

    
    public void testPlugin() throws Exception {
        assertEquals(2, plugin.getFixtureClasses().size());
        assertEquals(Continent.class, plugin.getFixtureClasses().get(0));
        assertEquals(Country.class, plugin.getFixtureClasses().get(1));
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
