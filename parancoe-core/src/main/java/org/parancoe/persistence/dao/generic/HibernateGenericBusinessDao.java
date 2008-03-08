/*
 *  Copyright 2008 jacopo.
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

package org.parancoe.persistence.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.MappedSuperclass;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory; 
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author jacopo
 */
public class HibernateGenericBusinessDao<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDaoBase<T, PK>
         {
    
   
    private Class<T> persistentClass;

    public HibernateGenericBusinessDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    
    public Class<T> getPersistentClass() {
        return persistentClass;
    }
    
    @Autowired
    public void initDao(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
    
    @SuppressWarnings("unchecked")
    public void create(T o) {
       getHibernateTemplate().persist(o);
    }
    
    @SuppressWarnings("unchecked")
    public void store(T o) {
       getHibernateTemplate().merge(o);
    }
    
    @SuppressWarnings("unchecked")
    public T read(PK id) {
        return (T) getHibernateTemplate().load(persistentClass, id);
    }
    
    public void delete(T o) {
        getHibernateTemplate().delete(o);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getHibernateTemplate().find("from "+persistentClass.getName()+" x");
    }

    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(persistentClass);
        for (Criterion c: criterion) {
            crit.add(c);
        }
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
        return getHibernateTemplate().
                findByCriteria(criteria, firstResult, maxResults);
    }    
    
    @SuppressWarnings("unchecked")
    public HibernatePage<T> searchPaginatedByCriteria(int page, int pageSize, Criterion... criterion) {
        Criteria crit = getSession().createCriteria(persistentClass);
        Criteria count = getSession().createCriteria(persistentClass);
        for (Criterion c: criterion) {
            crit.add(c);
            count.add(c);
        }
        
        // row count
        count.setProjection(Projections.rowCount());
        int rowCount = ((Integer)count.list().get(0)).intValue();
        
        crit.setFirstResult((page-1)*pageSize);
        crit.setMaxResults(pageSize);
        return new HibernatePage<T>(crit.list(), page, pageSize, rowCount);
    }
    
    @SuppressWarnings("unchecked")
    public HibernatePage<T> searchPaginatedByCriteria(int page, int pageSize, DetachedCriteria criteria) {
        // Row count
        criteria.setProjection(Projections.rowCount());
        int rowCount = ((Integer)getHibernateTemplate().
                findByCriteria(criteria).get(0)).intValue();
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        List<T> list = getHibernateTemplate().
                findByCriteria(criteria, (page-1)*pageSize, pageSize);
        
        return new HibernatePage<T>(list, page, pageSize,rowCount);
    }
    
    public int deleteAll() {
        List<T> rows = findAll();
        
        getHibernateTemplate().deleteAll(rows);
        
        return rows.size();
    }
    
    public long count() {
        // TODO IMPLEMENTARE IL METODO COUNT
        throw new RuntimeException("Implementare il metodo di contaggio");
    }

  }
