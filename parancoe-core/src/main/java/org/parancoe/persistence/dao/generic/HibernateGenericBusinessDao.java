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
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory; 
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author jacopo
 */
public class HibernateGenericBusinessDao<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDaoBase<T, PK>
         {
    private Class type;
        
    
    private Class<T> persistentClass;

    public HibernateGenericBusinessDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    @Autowired
    public void initBusinessDao(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
    
    public Class<T> getPersistentClass() {
        return persistentClass;
    }
    
    @SuppressWarnings("unchecked")
    public PK create(T o) {
        return (PK) getHibernateTemplate().save(o);
    }
    @SuppressWarnings("unchecked")
    public void createOrUpdate(T o) {
        getHibernateTemplate().saveOrUpdate(o);
    }
    
    @SuppressWarnings("unchecked")
    public T read(PK id) {
        return (T) getHibernateTemplate().get(persistentClass, id);
    }
    
    public void update(T o) {
        getHibernateTemplate().update(o);
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
    
    public int deleteAll() {
        return getHibernateTemplate().bulkUpdate("delete from "+persistentClass.getName()+" x");
    }
    
    public long count() {
        // TODO IMPLEMENTARE IL METODO COUNT
        throw new RuntimeException("Implementare il metodo di contaggio");
    }
    
  
    public void evict(T persistentObject) {
        getHibernateTemplate().evict(persistentObject);
    }
  }
