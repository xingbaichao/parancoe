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
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Common methods for Dao 
 * 
 * @author Jacopo Murador <jacopo.murador at seesaw.it> 
 */
public interface GenericDaoBase<T, PK extends Serializable> {

    T read(PK id);

    T get(PK id);
    
    /** create or update an object  */
    void create(T transientObject);

    /** create or update an object  */
    void store(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);

    List<T> findAll();

    List<T> searchByCriteria(Criterion... criterion);

    List<T> searchByCriteria(DetachedCriteria criteria);

    List<T> searchByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

    Page<T> searchPaginatedByCriteria(int page, int pageSize, Criterion... criterion);

    Page<T> searchPaginatedByCriteria(int page, int pageSize, DetachedCriteria criteria);

    int deleteAll();

    long count();
    
    long countByCriteria(DetachedCriteria criteria);
    
    void rollBackTransaction();

    HibernateTemplate getHibernateTemplate();

}
