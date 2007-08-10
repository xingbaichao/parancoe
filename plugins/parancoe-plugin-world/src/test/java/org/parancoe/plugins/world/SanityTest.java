package org.parancoe.plugins.world;

import java.util.List;
import org.parancoe.web.test.PluginTest;
import org.parancoe.web.plugin.Plugin;

public class SanityTest extends PluginTest {

    private Plugin plugin;

    public SanityTest() {
        super();
    }
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        plugin = (Plugin) ctx.getBean("pluginWorldConfig");
    }

    public void testPlugin() throws Exception {
        assertEquals(2, plugin.getFixtureClasses().size());
        assertEquals(Continent.class, plugin.getFixtureClasses().get(0));
        assertEquals(Country.class, plugin.getFixtureClasses().get(1));
    }

    public void testContinentAssigned() {
        CountryDao countryDao = (CountryDao) this.ctx.getBean("countryDao");
        List<Country> countries = countryDao.findAll();
        assertNotEmpty(countries);
        for (Country c : countries) {
            assertNotNull(c.getContinent());
        }
    }
    
    public void testContinentSize() {
        ContinentDao continentDao = (ContinentDao) this.ctx.getBean("continentDao");
        List<Continent> continents = continentDao.findAll();
        assertSize(7, continents);
    }
    
    
    public void testCountryFindByIsocode() {
    	CountryDao countryDao = (CountryDao) this.ctx.getBean("countryDao");
    	 List<Country> countries = countryDao.findByIsoCode("IQ");
    	
        assertEquals(countries.get(0).getEnglishName(), "Iraq");
    }
    public void testCountrySize() {
        CountryDao countryDao = (CountryDao) this.ctx.getBean("countryDao");
        List<Country> countries = countryDao.findAll();
        assertSize(87, countries);
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[] {Continent.class, Country.class};
    }
}
