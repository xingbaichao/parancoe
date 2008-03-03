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

import java.util.Iterator;
import java.util.List;
import org.parancoe.persistence.dao.DaoUtils;
import org.parancoe.persistence.dao.Daos;
import org.parancoe.persistence.po.hibernate.EntityTC;
import org.parancoe.persistence.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test case for the generic DAO.
 *
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
public class HibernateGenericDaoTest extends BaseTest {
    
    @Autowired
    private EntityTCBO entityTCBO;
    
    @Autowired
    private Daos daos;
        
    public void testStoreRetrieve() {
        EntityTC entity = new EntityTC();
        this.entityTCBO.createEntity(entity);
        EntityTC retrievedEntity = this.entityTCBO.retrieveEntity(entity.getId());
        assertEquals(entity, retrievedEntity);
    }

    public void testFindAll(){
        List<EntityTC> list = this.daos.getEntityTCDao().findAll();
        assertNotNull(list);
    }
    
    public void testGetByFieldOne() {
        EntityTC entity = new EntityTC();
        entity.setFieldOne("ONE");
        this.entityTCBO.createEntity(entity);
        List<EntityTC> result = this.entityTCBO.retrieveEntityByFieldOne("ONE");
        assertTrue(result.size() > 0);
        Iterator<EntityTC> enIt = result.iterator();
        while (enIt.hasNext()) {
            EntityTC elem = enIt.next();
            assertEquals(elem.getFieldOne(), "ONE");
        }
    }
    
    public void testGetByFieldTwo() {
        EntityTC entity = new EntityTC();
        entity.setFieldTwo("TWO");
        this.entityTCBO.createEntity(entity);
        List<EntityTC> result = this.entityTCBO.retrieveEntityByFieldTwo("TWO");
        assertTrue(result.size() > 0);
        Iterator<EntityTC> enIt = result.iterator();
        while (enIt.hasNext()) {
            EntityTC elem = enIt.next();
            assertEquals(elem.getFieldTwo(), "TWO");
        }
    }

    public void testGetByFieldOneAndFieldTwo() {
        EntityTC entity = new EntityTC();
        entity.setFieldOne("ONEONE");
        entity.setFieldTwo("TWOTWO");
        this.entityTCBO.createEntity(entity);
        List<EntityTC> result = this.entityTCBO.retrieveEntityByFieldOneAndFieldTwo("ONEONE", "TWOTWO");
        assertTrue(result.size() > 0);
        Iterator<EntityTC> enIt = result.iterator();
        while (enIt.hasNext()) {
            EntityTC elem = enIt.next();
            assertEquals(elem.getFieldOne(), "ONEONE");
            assertEquals(elem.getFieldTwo(), "TWOTWO");
        }
    }
    
}
