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

package org.parancoe.persistence.dao.generic;

import org.parancoe.persistence.po.hibernate.EntityTC;
import org.parancoe.persistence.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class HibernateGenericBusinessDaoTest  extends BaseTest {
    
    @Autowired
    private EntityTCBusinessDao dao;
    
    public void testStoreRetrieve() {
        EntityTC entity = new EntityTC();
        dao.store(entity);
        assert(entity.getId() > 0);
        entity.setFieldOne("pippo");
        dao.store(entity);
        entity = dao.read(entity.getId());
        assertEquals("pippo", entity.getFieldOne());
    }

    
    
}