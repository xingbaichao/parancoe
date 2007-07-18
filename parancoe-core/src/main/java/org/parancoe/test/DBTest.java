// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.parancoe.persistence.dao.generic.GenericDao;
import org.parancoe.util.FixtureHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

public abstract class DBTest extends EnhancedTestCase {

    private static final Logger logger = Logger.getLogger(DBTest.class);

    private static Map<Class, Object[]> fixtures;

    protected ApplicationContext ctx;
    
    public DBTest() {
        this.ctx = getTestContext();
        if (fixtures == null) {
            // carico le fixture se non sono già presenti
            Set<Class> fixtureClasses = getFixtureClassSet();
            if (fixtureClasses != null && fixtureClasses.size() > 0) {
                try {
                    fixtures = FixtureHelper.loadFixturesFromResource((ClassPathResource) this.ctx.getResource("classpath:/fixtures/"), fixtureClasses);
                } catch (Exception e) {
                    logger.warn("Non sono riuscito predisporre tutte le fixture delle classi " + fixtureClasses.toString(), e);
                }
                logger.info("Predisposte le fixture per le classi " + fixtures.keySet().toString());
            } else {
                logger.info("No fixtures to load");
            }
        }
    }

    /**
     * Restituisce un array <strong>ordinato al contrario</strng> di model che
     * devono essere caricati
     *
     * @return array di classi
     */
    public final Class[] getReverseOrderFixtureClasses() {
        Class[] models = getFixtureClasses();
        ArrayUtils.reverse(models);
        return models;
    }

    /**
     * Restituisce un array <strong>ordinato</strng> di model che devono essere
     * caricati
     *
     * NB: Deve sepre ritornare un nuovo oggetto, e non riferimenti a istanze
     * statiche o simili
     *
     * @return array di classi
     */
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }

    public final Set<Class> getFixtureClassSet() {
        Class[] classes = getFixtureClasses();
        Set<Class> result = new HashSet<Class>(classes.length);
        CollectionUtils.addAll(result, classes);
        return result;
    }

    /**
     * Cancello le righe dalle tabelle e le ripopolo
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        
        // erase everything
        for (Class model : getReverseOrderFixtureClasses()) {
            GenericDao dao = (GenericDao) this.ctx.getBean(FixtureHelper.getFixtureDaoId(model));
            FixtureHelper.eraseDbForModel(model, dao);
        }
        // repopulate
        for (Class model : getFixtureClasses()) {
            GenericDao dao = (GenericDao) this.ctx.getBean(FixtureHelper.getFixtureDaoId(model));
            FixtureHelper.populateDbForModel(model, fixtures.get(model), dao);
        }
    }

    /**
     * Return the application context.
     *
     * @return The application context
     */
    protected abstract ApplicationContext getTestContext();
}
