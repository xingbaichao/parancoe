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
package org.parancoe.persistence.dao.generic;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import org.hibernate.criterion.Projections;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate implementation of the generic DAO.
 *
 * Derived from http://www-128.ibm.com/developerworks/java/library/j-genericdao.html
 *
 * @author <a href="mailto:lucio.benfante@jugpadova.it">Lucio Benfante</a>
 * @version $Revision$
 */
public class HibernateGenericDao <T, PK extends Serializable>
        extends HibernateDaoSupport
        implements GenericDao<T, PK> {
    private Class type;
            
    @SuppressWarnings("unchecked")
    public PK create(T o) {
        return (PK) getHibernateTemplate().save(o);
    }
    @SuppressWarnings("unchecked")
    public void createOrUpdate(T o) {
        getHibernateTemplate().merge(o);
    }
    
    @SuppressWarnings("unchecked")
    public T read(PK id) {
        return (T) getHibernateTemplate().get(getType(), id);
    }
    
    public void update(T o) {
        getHibernateTemplate().merge(o);
    }
    
    public void delete(T o) {
        getHibernateTemplate().delete(o);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getHibernateTemplate().find("from "+getType().getName()+" x");
    }

    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(Criterion... criterion) {
        Criteria crit = getSession().createCriteria(getType());
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
        Criteria crit = getSession().createCriteria(getType());
        Criteria count = getSession().createCriteria(getType());
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
    
    public Class getType() {
        return type;
    }
    
    public void setType(Class type) {
        this.type = type;
    }

    public void evict(T persistentObject) {
        getHibernateTemplate().evict(persistentObject);
    }
}
