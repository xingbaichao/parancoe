/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Web.
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
package org.parancoe.web;

import java.util.*;

import javax.servlet.ServletContextEvent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.lambico.dao.generic.GenericDaoBase;
import org.lambico.dao.spring.hibernate.GenericDaoHibernateSupport;
import org.lambico.data.YamlFixtureHelper;
import org.lambico.spring.dao.DaoUtils;
import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.plugin.PluginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Paolo Dona paolo.dona@seesaw.it
 * @author michele franzin <michele at franzin.net>
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class PopulateInitialDataContextListener extends ContextLoaderListener {

    private static final Logger log = LoggerFactory.getLogger(PopulateInitialDataContextListener.class);
    private ApplicationContext ctx;
    protected List<Class> clazzToPopulate = new ArrayList<Class>();

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        ctx = (ApplicationContext) evt.getServletContext().getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Set<Class> fixtureClasses = new LinkedHashSet<Class>(getFixtureClasses());
        if (fixtureClasses.isEmpty()) {
            log.info("Skipping initial data population (no models)");
            return;
        }

        Map<Class, List> fixtures = YamlFixtureHelper.loadFixturesFromResource("initialData/",
                fixtureClasses);
        log.info("Populating initial data for models...");

        SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Attach transaction to thread
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        TransactionSynchronizationManager.initSynchronization();

        try {
            for (Class clazz : fixtures.keySet()) {
                List modelFixtures = fixtures.get(clazz);
                if (modelFixtures.isEmpty()) {
                    log.warn("No data for {}, did you created the fixture file?",
                            YamlFixtureHelper.getModelName(clazz));
                    continue;
                }
                populateTableForModel(clazz, modelFixtures);
            }
            fixtures.clear();
            log.info("Populating initial data for models done!");
            session.getTransaction().commit();
            if (session.isOpen()) {
                session.close();
            }
        } catch (Exception e) {
            log.error("Error while populating initial data for models {}", e.getMessage(), e);
            log.debug("Rolling back the populating database transaction");
            session.getTransaction().rollback();
        } finally {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                /*do nothing*/
            }
            TransactionSynchronizationManager.unbindResource(sessionFactory);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    private List<Class> getFixtureClasses() {
        Collection<ApplicationContextPlugin> plugins = new PluginHelper(ctx).getApplicationContextPlugins();
        for (ApplicationContextPlugin plugin : plugins) {
            try {
                clazzToPopulate.addAll(plugin.getFixtureClasses());
            } catch (Exception e) {
                log.error("Impossibile reperire i nomi delle fixtures da caricare per il plugin {}",
                        plugin.getName());
            }
        }
        return clazzToPopulate;
    }

    private void populateTableForModel(final Class model, final List fixtures) {
        String fixtureName = YamlFixtureHelper.getModelName(model);
        GenericDaoBase dao = DaoUtils.getDaoFor(model, ctx);
        if (dao == null) {
            log.info("Dao not found for {} and po {}", fixtureName, model.getCanonicalName());
            return;
        }
        if (dao.count() == 0) {
            log.info("Populating {} with {} items...", fixtureName, fixtures.size());
            final HibernateTemplate template = ((GenericDaoHibernateSupport) dao).
                    getHibernateTemplate();
            try {
                for (Object entity : fixtures) {
                    template.saveOrUpdate(entity);
                }
                template.flush();
                template.clear();
            } catch (Exception e) {
                log.error("Error populating rows in {} table", fixtureName, e);
            }
            log.info("Population of {} done!", fixtureName);
        } else {
            log.info("Population of {} skipped (already populated)", fixtureName);
        }
    }
}