/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Configuration.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.plugin.configuration;

import java.math.BigDecimal;
import java.util.List;
import org.lambico.dao.spring.hibernate.HibernateGenericDao;
import org.parancoe.plugin.configuration.dao.CategoryDao;
import org.parancoe.plugin.configuration.dao.PropertyDao;
import org.parancoe.plugin.configuration.po.Category;
import org.parancoe.plugin.configuration.po.Property;
import org.parancoe.plugin.configuration.po.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class PropertyDaoTest extends BaseTest {

    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private CategoryDao categoryDao;

    public void testFindAll() {
        List<Property> results = propertyDao.findAll();
        assertSize(5, results);
    }

    public void testFindByNameAndCategoryId() {
        Category category = categoryDao.findByName("first_category");
        Property property =
                propertyDao.findByNameAndCategoryId("first_property", category.getId());
        assertNotNull(property);
        assertEquals("first_property", property.getName());
        assertEquals(category.getId(), property.getCategory().getId());
        assertEquals(PropertyType.STRING, property.getType());
        assertEquals("first value", property.getValue());
    }

    public void testFindIntegerProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("integer_property", category.getId());
        assertEquals(PropertyType.INTEGER, property.getType());
        assertEquals(Integer.valueOf("10"), property.getValueAsInteger());
    }

    public void testFindBooleanProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("boolean_property", category.getId());
        assertEquals(PropertyType.BOOLEAN, property.getType());
        assertEquals(Boolean.TRUE, property.getValueAsBoolean());
    }

    public void testFindRealProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("real_property", category.getId());
        assertEquals(PropertyType.REAL, property.getType());
        assertEquals(new BigDecimal(
                "3.141592653589793238462643383279502884197169399375105820974944592"),
                property.getValueAsReal());
    }

    public void testFindTextProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("text_property", category.getId());
        assertEquals(PropertyType.TEXT, property.getType());
        assertEquals("Here you can have a very long text.", property.getTypedValue());
    }
    
    public void testSetStringValue() {
        Category category = categoryDao.findByName("first_category");
        Property property =
                propertyDao.findByNameAndCategoryId("first_property", category.getId());
        String newValue = "A new value";
        property.setTypedValue(newValue);
        assertSame(newValue, property.getValue());
        Property oldObject = property;
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId("first_property", category.getId());
        assertNotSame(oldObject, property);
        assertEquals(newValue, property.getTypedValue());
    }

    public void testSetIntegerValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "integer_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        Integer newValue = Integer.valueOf(25);
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertEquals(newValue, property.getValueAsInteger());
    }

    public void testSetRealValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "real_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        BigDecimal newValue = BigDecimal.valueOf(10.50);
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertEquals(newValue, property.getValueAsReal());
    }

    public void testSetBooleanValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "boolean_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        Boolean newValue = Boolean.FALSE;
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertEquals(newValue, property.getValueAsBoolean());
    }

    public void testSetTextValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "text_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        String newValue = "Another long text!";
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertEquals(newValue, property.getValue());
    }
    
    private void fixAndClearSession(Property property) throws DataAccessException {
        propertyDao.store(property);
        HibernateTemplate hibernateTemplate =
                ((HibernateGenericDao)propertyDao).getHibernateTemplate();
        hibernateTemplate.flush();
        hibernateTemplate.evict(property);
    }
}