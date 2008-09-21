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
package org.parancoe.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.*;

import javax.servlet.ServletContextEvent;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.parancoe.persistence.dao.DaoProvider;
import org.parancoe.persistence.dao.generic.BusinessDao;
import org.parancoe.persistence.dao.generic.GenericDao;
import org.parancoe.persistence.dao.generic.GenericDaoBase;
import org.parancoe.util.FixtureHelper;
import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.plugin.PluginHelper;
import org.parancoe.web.plugin.WebPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Paolo Dona paolo.dona@seesaw.it
 * @author Michele Franzin michele.franzin@seesaw.it
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class PopulateInitialDataContextListener extends ContextLoaderListener {

    private static final String DAO_PROVIDER_ID = "daos";
    
    private static final Logger log = Logger.getLogger(PopulateInitialDataContextListener.class);

    private ApplicationContext ctx;
    
    protected List<Class> clazzToPopulate = new ArrayList<Class>();

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        ctx = (ApplicationContext) evt.getServletContext().getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Set<Class> fixtureClasses = new LinkedHashSet<Class>(getFixtureClasses());
        if (CollectionUtils.isEmpty(fixtureClasses)) {
            log.info("Skipping initial data population (no models)");
            return;
        }

        Map<Class, Object[]> fixtures = FixtureHelper.loadFixturesFromResource("initialData/",
                fixtureClasses);
        log.info("Populating initial data for models...");
        String className = "";
        
        SessionFactory sessionFactory = (SessionFactory)ctx.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Attach transaction to thread
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        TransactionSynchronizationManager.initSynchronization();

        try{
            for (Class clazz : fixtures.keySet()) {
                className = clazz.getName();
                if (ArrayUtils.isEmpty(fixtures.get(clazz))) {
                    log.warn("Population of " + FixtureHelper.getModelName(clazz)
                            + " skipped (empty fixture file?)");
                    continue;
                }
                populateTableForModel(clazz, fixtures.get(clazz));
            }
            fixtures.clear();
            log.info("Populating initial data for models done!");
            session.getTransaction().commit();
            if(session.isOpen()) session.close();
        }catch(Exception e){
            log.error(e);
            log.error("Error while populating initial data for models " + e.getMessage(), e);
            log.debug("Rolling back the populating database transaction");
            session.getTransaction().rollback();
        }finally{
            try{
                if(session.isOpen()){
                    session.close();
                }
            }catch(Exception e){/*do nothing*/}
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
                log.error("Impossibile reperire i nomi delle fixtures da caricare per il plugin " + plugin.getName());
            }
        }
        return clazzToPopulate;
    }

    private void populateTableForModel(Class clazz, Object[] fixtures) {
        String fixtureName = FixtureHelper.getModelName(clazz);
        DaoProvider daos = (DaoProvider)ctx.getBean(DAO_PROVIDER_ID);
        GenericDaoBase dao = (GenericDaoBase)daos.getDao(clazz);
        if (dao != null) {
            int count = dao.findAll().size();
            if (count == 0) {
                log.info("Populating " + fixtureName + " with " + fixtures.length + " items...");
                FixtureHelper.populateDbForModel(clazz, fixtures, dao);
                log.info("Population of " + fixtureName + " done!");
            } else {
                log.info("Population of " + fixtureName + " skipped (already populated)");
            }
        }
        else {
            log.info("Dao not found for " + fixtureName + " and po " + clazz.getName());
        }
    }

}