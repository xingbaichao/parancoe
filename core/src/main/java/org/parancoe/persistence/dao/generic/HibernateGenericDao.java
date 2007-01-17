// Copyright 2006 The Parancoe Team
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
        getHibernateTemplate().saveOrUpdate(o);
    }
    
    @SuppressWarnings("unchecked")
    public T read(PK id) {
        return (T) getHibernateTemplate().get(getType(), id);
    }
    
    public void update(T o) {
        getHibernateTemplate().update(o);
    }
    
    public void delete(T o) {
        getHibernateTemplate().delete(o);
    }

    public List<T> findAll() {
        return getHibernateTemplate().find("from "+getType().getName()+" x");
    }

    public Class getType() {
        return type;
    }
    
    public void setType(Class type) {
        this.type = type;
    }
}
