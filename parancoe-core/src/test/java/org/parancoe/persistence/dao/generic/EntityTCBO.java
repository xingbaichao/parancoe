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
import org.parancoe.persistence.dao.Daos;

import org.parancoe.persistence.po.hibernate.EntityTC;
import org.springframework.transaction.annotation.Transactional;

/**
 * A BO to be used for the tests of the versioned entity.
 *
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
public class EntityTCBO {
    
    /** Creates a new instance of EntityTCBO */
    public EntityTCBO() {
    }
    
    @Transactional()
    public Long createEntity(EntityTC entity) {
        return daos.getEntityTCDao().create(entity);
    }
    
    @Transactional(readOnly=true)
    public EntityTC retrieveEntity(Long id) {
        EntityTC retrievedEntity = this.daos.getEntityTCDao().read(id);
        return retrievedEntity;
    }

    @Transactional(readOnly=true)    
    List<EntityTC> retrieveEntityByFieldOne(String value) {
        return daos.getEntityTCDao().findByFieldOne(value);
    }

    @Transactional(readOnly=true)    
    List<EntityTC> retrieveEntityByFieldTwo(String value) {
        return daos.getEntityTCDao().findByFieldTwo(value);
    }
    
    @Transactional(readOnly=true)    
    List<EntityTC> retrieveEntityByFieldOneAndFieldTwo(String one, String two) {
        return daos.getEntityTCDao().findByFieldOneAndFieldTwo(one, two);
    }

    public Daos daos;

    public Daos getDaos() {
        return daos;
    }

    public void setDaos(Daos daos) {
        this.daos = daos;
    }
    
}
