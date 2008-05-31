/*
 *  Copyright 2008 Jacopo Murador <jacopo.murador at seesaw.it>.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.parancoe.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * I don't know why the transaction annotations aren't recognized. This class is 
 * a workaround until I will find a solution.
 * 
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class ParancoeOpenSessionInViewInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ParancoeOpenSessionInViewInterceptor.class.getPackage().getName());
    
    @Autowired
    private SessionFactory sessionFactory;

    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        logger.debug("Opening session and beginning transaction");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        TransactionSynchronizationManager.initSynchronization();
        return true;
    }

    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView model) throws Exception {
        //Nothing to do
    }

    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
        Session session =sessionFactory.getCurrentSession();
        try{
            if(ex==null){
                logger.debug("Committing the database transaction");
                session.getTransaction().commit();
            }else{
                logger.error(ex);
                logger.debug("Rolling back the database transaction");
                session.getTransaction().rollback();
            }
            if(session.isOpen())
             session.close();
        }catch(Exception e){
            logger.error(e);
            throw e;
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


}
