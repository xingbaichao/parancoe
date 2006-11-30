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

import java.util.List;
import org.parancoe.persistence.po.hibernate.EntityTC;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author <a href="mailto:lucio@benfante.com">Lucio Benfante</a>
 * @version $Revision$
 */
public class EntityTCBO {
    private EntityTCDao dao;    
    
    /** Creates a new instance of EntityTCBO */
    public EntityTCBO() {
    }

    public EntityTCDao getDao() {
        return dao;
    }

    public void setDao(EntityTCDao dao) {
        this.dao = dao;
    }
    
    @Transactional()
    public Long createEntity(EntityTC entity) {
        return (Long)dao.create(entity);
    }
    
    @Transactional(readOnly=true)
    public EntityTC retrieveEntity(Long id) {
        EntityTC retrievedEntity = this.dao.read(id);
        return retrievedEntity;
    }

    @Transactional(readOnly=true)    
    List retrieveEntityByFieldOne(String value) {
        return this.dao.findByFieldOne(value);
    }

    @Transactional(readOnly=true)    
    List retrieveEntityByFieldTwo(String value) {
        return this.dao.findByFieldTwo(value);
    }
    
    @Transactional(readOnly=true)    
    List retrieveEntityByFieldOneAndFieldTwo(String one, String two) {
        return this.dao.findByFieldOneAndFieldTwo(one, two);
    }
    
}
