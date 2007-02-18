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

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import junit.framework.*;
import org.parancoe.persistence.dao.generic.VersionedEntityTCBO;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * A test case for a versioned entity.
 *
 * @author Lucio Benfante (<a href="lucio.benfante@jugpadova.it">lucio.benfante@jugpadova.it</a>)
 * @version $Revision$
 */
public class VersionedEntityTest extends TestCase {
    
    private VersionedEntityTCBO entityTCBO;
    
    public VersionedEntityTest(String testName) {
        super(testName);
        BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance("beanRefFactory_test.xml");
        BeanFactoryReference bf = bfl.useBeanFactory("org.parancoe.persistence");
        this.entityTCBO = (VersionedEntityTCBO)bf.getFactory().getBean("versionedEntityTCBO");
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public void testStoreRetrieve() {
        VersionedEntityTC versionedEntity = new VersionedEntityTC();
        versionedEntity.setName("Test name");
        VersionedEntityDataTC versionedEntityData = new VersionedEntityDataTC();
        versionedEntityData.setBalance(new BigDecimal("100.00"));
        versionedEntityData.setDescription("Test description");
        versionedEntity.updateVersionedData(versionedEntityData);
        Long id = this.entityTCBO.createEntity(versionedEntity);
        VersionedEntityTC retrievedEntity = this.entityTCBO.retrieveEntity(id);
        assertEquals(versionedEntity, retrievedEntity);
        assertNotSame(versionedEntity, retrievedEntity);
    }
    
    public void testUpdateNewVersionedData() {
        Long id;
        VersionedEntityTC versionedEntity = new VersionedEntityTC();
        versionedEntity.setName("Test name");
        VersionedEntityDataTC versionedEntityData = new VersionedEntityDataTC();
        versionedEntityData.setBalance(new BigDecimal("100.00"));
        versionedEntityData.setDescription("Test description");
        versionedEntity.updateVersionedData(versionedEntityData);
        id = this.entityTCBO.createEntity(versionedEntity);
        VersionedEntityTC retrievedEntity = this.entityTCBO.retrieveEntity(id);
        List<VersionedEntityDataTC> entityDataVersions = retrievedEntity.getVersionedData();
        assertTrue(!entityDataVersions.isEmpty());
        assertEquals(1, entityDataVersions.size());
        assertTrue(versionedEntityData.getId().equals(versionedEntity.findLastVersionedData().getId()));
        assertNotNull(versionedEntity.findLastVersionedData().getDateFrom());
        assertNull(versionedEntity.findLastVersionedData().getDateTo());
    }
    
    public void testUpdateExistentVersionedData() {
        VersionedEntityTC versionedEntity = new VersionedEntityTC();
        versionedEntity.setName("Test name");
        VersionedEntityDataTC versionedEntityData = new VersionedEntityDataTC();
        versionedEntityData.setBalance(new BigDecimal("100.00"));
        versionedEntityData.setDescription("Test description");
        versionedEntity.updateVersionedData(versionedEntityData);
        //begin-added by enrico because test failed
        try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//end-added by enrico because test failed
        Long id = this.entityTCBO.createEntity(versionedEntity);
        versionedEntityData = new VersionedEntityDataTC();
        versionedEntityData.setBalance(new BigDecimal("200.00"));
        versionedEntityData.setDescription("Test description modified");
        VersionedEntityTC updatedEntity = this.entityTCBO.updateVersionedData(id, versionedEntityData);
        VersionedEntityTC retrievedEntity = this.entityTCBO.retrieveEntity(id);
        List<VersionedEntityDataTC> entityDataVersions = retrievedEntity.getVersionedData();
        assertTrue(!entityDataVersions.isEmpty());
        assertEquals(2, entityDataVersions.size());
        assertTrue(versionedEntityData.getId().equals(retrievedEntity.findLastVersionedData().getId()));
        assertTrue(entityDataVersions.get(entityDataVersions.size()-1).getDateFrom().compareTo(entityDataVersions.get(entityDataVersions.size()-2).getDateFrom()) > 0);
    }

    public void testUpdateExistentVersionedDataWithLocales() {
        VersionedEntityTC versionedEntity = new VersionedEntityTC();
        versionedEntity.setDefaultLocale(Locale.ITALIAN.getLanguage());
        versionedEntity.setName("Test name");
        VersionedEntityDataTC italianVersionedEntityData = new VersionedEntityDataTC();
        italianVersionedEntityData.setLocale(Locale.ITALIAN.getLanguage());
        italianVersionedEntityData.setBalance(new BigDecimal("100.00"));
        italianVersionedEntityData.setDescription("Descrizione in italiano");
        versionedEntity.updateVersionedData(italianVersionedEntityData);
        VersionedEntityDataTC englishVersionedEntityData = new VersionedEntityDataTC();
        englishVersionedEntityData.setLocale(Locale.ENGLISH.getLanguage());
        englishVersionedEntityData.setBalance(new BigDecimal("100.00"));
        englishVersionedEntityData.setDescription("English description");
        versionedEntity.updateVersionedData(englishVersionedEntityData);
        //begin-added by enrico because test failed
        try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//end-added by enrico because test failed
        Long id = this.entityTCBO.createEntity(versionedEntity);
        italianVersionedEntityData = new VersionedEntityDataTC();
        italianVersionedEntityData.setLocale(Locale.ITALIAN.getLanguage());
        italianVersionedEntityData.setBalance(new BigDecimal("200.00"));
        italianVersionedEntityData.setDescription("Descrizione in italiano modificata");
        VersionedEntityTC updatedEntity = this.entityTCBO.updateVersionedData(id, italianVersionedEntityData);
        englishVersionedEntityData = new VersionedEntityDataTC();
        englishVersionedEntityData.setLocale(Locale.ENGLISH.getLanguage());
        englishVersionedEntityData.setBalance(new BigDecimal("200.00"));
        englishVersionedEntityData.setDescription("Updated english description");
        updatedEntity = this.entityTCBO.updateVersionedData(id, englishVersionedEntityData);
        VersionedEntityTC retrievedEntity = this.entityTCBO.retrieveEntity(id);
        List<VersionedEntityDataTC> entityDataVersions = retrievedEntity.getVersionedData();
        assertTrue(!entityDataVersions.isEmpty());
        assertEquals(4, entityDataVersions.size());
        assertTrue(italianVersionedEntityData.getId().equals(retrievedEntity.findLastVersionedData().getId()));
        assertTrue(englishVersionedEntityData.getId().equals(retrievedEntity.findLastVersionedData(Locale.ENGLISH.getLanguage()).getId()));
    }
    
}
