/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin World.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.plugins.world;

import java.util.List;
import javax.annotation.Resource;
import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.test.PluginTest;
import org.parancoe.web.plugin.WebPlugin;

public class SanityTest extends PluginTest {

    @Resource(name = "pluginWorldConfig")
    private WebPlugin plugin;
    @Resource(name = "applicationContextPluginWorldConfig")
    private ApplicationContextPlugin contextPlugin;
    @Resource
    private CountryDao countryDao;
    @Resource
    private ContinentDao continentDao;

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
