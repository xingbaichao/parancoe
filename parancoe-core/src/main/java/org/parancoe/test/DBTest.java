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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.parancoe.persistence.dao.DaoUtils;
import org.parancoe.persistence.dao.generic.GenericDaoBase;
import org.parancoe.util.FixtureHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class DBTest extends EnhancedTestCase {

    private static final Logger logger = Logger.getLogger(DBTest.class);
    protected Map<Class, Object[]> fixtures;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    @Qualifier("daoMap")
    HashMap daoMap;

    /**
     * Restituisce un array <strong>ordinato al contrario</strong> di model che
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
        Set<Class> result = new LinkedHashSet<Class>(classes.length);
        CollectionUtils.addAll(result, classes);
        return result;
    }

    /**
     * Cancello le righe dalle tabelle e le ripopolo
     */
    @Override
    public void onSetUpBeforeTransaction() throws Exception {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Attach transaction to thread
        TransactionSynchronizationManager.bindResource(sessionFactory,
                new SessionHolder(session));
        TransactionSynchronizationManager.initSynchronization();

        try {
            // erase everything
            for (Class model : getReverseOrderFixtureClasses()) {
                GenericDaoBase dao = DaoUtils.getDaoFor(model,
                        applicationContext);
                FixtureHelper.eraseDbForModel(model, dao);
            }
            // repopulate
            for (Class model : getFixtureClasses()) {
                GenericDaoBase dao = DaoUtils.getDaoFor(model,
                        applicationContext);
                FixtureHelper.populateDbForModel(model, fixtures.get(model), dao);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e);
            logger.debug("Rolling back the database transaction");
            session.getTransaction().rollback();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {/*do nothing*/

            }
            TransactionSynchronizationManager.unbindResource(sessionFactory);
            TransactionSynchronizationManager.clearSynchronization();
        }

    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                    "classpath:org/parancoe/persistence/dao/generic/genericDao.xml",
                    "classpath:database_test.xml",
                    "classpath:dao_test.xml",
                    "classpath:applicationContext_test.xml"
                };
    }

    @Override
    protected void prepareTestInstance() throws Exception {
        super.prepareTestInstance();

        Map ldaos = DaoUtils.getDaos(applicationContext);
        daoMap.putAll(ldaos);

        if (fixtures == null) {
            // carico le fixture se non sono già presenti
            Set<Class> fixtureClasses = getFixtureClassSet();
            if (fixtureClasses != null && fixtureClasses.size() > 0) {
                try {
                    fixtures = FixtureHelper.loadFixturesFromResource(
                            (ClassPathResource) applicationContext.getResource(
                            "classpath:/fixtures/"), fixtureClasses);
                    logger.info("Loaded fixtures for classes " +
                            fixtures.keySet().toString());
                } catch (Exception e) {
                    logger.warn("I can't load all fixture for classes " +
                            fixtureClasses.toString(), e);
                }
            } else {
                logger.info("No fixtures to load");
            }
        }
    }
}
