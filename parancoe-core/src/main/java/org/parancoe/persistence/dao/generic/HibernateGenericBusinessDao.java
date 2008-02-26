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
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author jacopo
 */
public class HibernateGenericBusinessDao<T, PK extends Serializable> implements GenericDaoBase<T, PK> {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Class<T> persistentClass;

    public HibernateGenericBusinessDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
     }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
    
    /** Persist the newInstance object into database */
    @SuppressWarnings("unchecked")
    public PK create(T newInstance) {
        return (PK) sessionFactory.getCurrentSession().save(newInstance);
    }

    /** create or update an object */
    public void createOrUpdate(T o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    @SuppressWarnings("unchecked")
    public T read(PK id) {
      return (T) sessionFactory.getCurrentSession().load(getPersistentClass(), id);
    }

    /** Save changes made to a persistent object.  */
    public void update(T transientObject) {
        sessionFactory.getCurrentSession().update(transientObject);
    }

    /** Remove an object from persistent storage in the database */
    public void delete(T persistentObject) {
        sessionFactory.getCurrentSession().delete(persistentObject);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        
        return searchByCriteria();
    }
    
    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(Criterion... criterion) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }
    
    public List<T> searchByCriteria(DetachedCriteria criteria) {
        Criteria crit = criteria.getExecutableCriteria(sessionFactory.getCurrentSession());
        return crit.list();
    }
    
    public List<T> searchByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
       Criteria crit = criteria.getExecutableCriteria(sessionFactory.getCurrentSession());
       crit.setFirstResult(firstResult);
       crit.setMaxResults(maxResults);
       return crit.list(); 
    }

    public int deleteAll() {
        return sessionFactory.getCurrentSession().createQuery("delete from "+persistentClass.getName()+" x").executeUpdate();
    }
    
    public long count() {
        return 0L;
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(getPersistentClass());
        Example example =  Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    public void clear() {
        sessionFactory.getCurrentSession().clear();
    }
    
    public void evict(T persistentObject) {
        sessionFactory.getCurrentSession().evict(persistentObject);
    }
    
}
