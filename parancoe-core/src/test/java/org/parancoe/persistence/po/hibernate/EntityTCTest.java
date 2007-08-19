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
package org.parancoe.persistence.po.hibernate;

import java.util.List;
import org.parancoe.persistence.dao.Daos;
import org.parancoe.persistence.util.BaseTest;

/**
 * Tests on generic DAO using EntityTC.
 * 
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
public class EntityTCTest extends BaseTest {

    protected Daos daos;

    public EntityTCTest() {
        this.daos = (Daos) this.ctx.getBean("daos");
    }

    public void testDaoExists() {
        assertNotNull(this.daos);
        assertNotNull(this.daos.getEntityTCDao());
    }

    public void testAllSize() {
        assertSize(5, this.daos.getEntityTCDao().findAll());
    }

    public void testOrderByFieldOne() {
        List<EntityTC> entities = this.daos.getEntityTCDao().findByOrderByFieldOne();
        assertNotEmpty(entities);
        EntityTC old = entities.get(0);
        for (int i = 1; i < entities.size(); i++) {
            EntityTC curr = entities.get(i);
            assertTrue("Entities not orderd by fieldOne", old.getFieldOne().compareTo(curr.getFieldOne()) <= 0);
            old = curr;
        }
    }

    public void testOrderByFieldTwo() {
        List<EntityTC> entities = this.daos.getEntityTCDao().findByOrderByFieldTwo();
        assertNotEmpty(entities);
        EntityTC old = entities.get(0);
        for (int i = 1; i < entities.size(); i++) {
            EntityTC curr = entities.get(i);
            assertTrue("Entities not orderd by fieldTwo", old.getFieldTwo().compareTo(curr.getFieldTwo()) <= 0);
            old = curr;
        }
    }

    public void testOrderByFieldOneAndFieldTwo() {
        List<EntityTC> entities = this.daos.getEntityTCDao().findByOrderByFieldOneAndFieldTwo();
        assertNotEmpty(entities);
        EntityTC old = entities.get(0);
        for (int i = 1; i < entities.size(); i++) {
            EntityTC curr = entities.get(i);
            if (old.getFieldOne().equals(curr.getFieldOne())) {
                assertTrue("Entities ordered by fieldOne but not orderd by fieldTwo", old.getFieldTwo().compareTo(curr.getFieldTwo()) <= 0);
            } else {
                assertTrue("Entities not orderd by fieldOne", old.getFieldOne().compareTo(curr.getFieldOne()) <= 0);
            }
            old = curr;
        }
    }
}
