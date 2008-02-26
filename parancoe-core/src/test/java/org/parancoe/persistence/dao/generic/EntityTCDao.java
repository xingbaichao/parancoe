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

import java.util.List;

import org.parancoe.persistence.po.hibernate.EntityTC;

/**
 * A DAO to be used for the tests of the generic DAO.
 *
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
@Dao(entity=EntityTC.class)
public interface EntityTCDao extends GenericDao<EntityTC, Long> {
    List<EntityTC> findByFieldOne(String value);
    List<EntityTC> findByFieldTwo(String value);
    List<EntityTC> findByFieldThree(@Compare(CompareType.ILIKE) String value);
    List<EntityTC> findByFieldOneAndFieldTwo(String one, String two);
    List<EntityTC> findByOrderByFieldOne();
    List<EntityTC> findByOrderByFieldTwo();
    List<EntityTC> findByOrderByFieldOneAndFieldTwo();
    EntityTC findByFieldOneOrderByFieldTwo(String one);
    List<EntityTC> findByOrderByFieldOne(@FirstResult int firstResult, @MaxResults int maxResults);
    List<EntityTC> searchAllOrderByFieldOne(@FirstResult int firstResult, @MaxResults int maxResults);
}
