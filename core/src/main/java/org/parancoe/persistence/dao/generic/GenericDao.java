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


/**
 * Interface for the generic DAO.
 *
 * Derived from http://www-128.ibm.com/developerworks/java/library/j-genericdao.html
 *
 * @author <a href="mailto:lucio.benfante@jugpadova.it">Lucio Benfante</a>
 * @version $Rev$
 */
public interface GenericDao <T, PK extends Serializable> extends GenericDaoHibernateSupport {

    /** Persist the newInstance object into database */
    PK create(T newInstance);

    /** create or update an object */
    void createOrUpdate(T o);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T read(PK id);

    /** Save changes made to a persistent object.  */
    void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);

    List<T> findAll();
}
