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
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import org.junit.Test;

public class PropertyDaoTest extends BaseTest {

    @Autowired
    private PropertyDao propertyDao;
    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void findAll() {
        List<Property> results = propertyDao.findAll();
        assertThat(results, hasSize(5));
    }

    @Test
    public void findByNameAndCategoryId() {
        Category category = categoryDao.findByName("first_category");
        Property property =
                propertyDao.findByNameAndCategoryId("first_property", category.getId());
        assertThat(property, is(notNullValue()));
        assertThat(property.getName(), equalTo("first_property"));
        assertThat(property.getCategory().getId(), equalTo(category.getId()));
        assertThat(property.getType(), equalTo(PropertyType.STRING));
        assertThat(property.getValue(), equalTo("first value"));
    }

    @Test
    public void findIntegerProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("integer_property", category.getId());
        assertThat(property.getType(), equalTo(PropertyType.INTEGER));
        assertThat(property.getValueAsInteger(), equalTo(Integer.valueOf("10")));
    }

    @Test
    public void findBooleanProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("boolean_property", category.getId());
        assertThat(property.getType(), equalTo(PropertyType.BOOLEAN));
        assertThat(property.getValueAsBoolean(), equalTo(Boolean.TRUE));
    }

    @Test
    public void findRealProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("real_property", category.getId());
        assertThat(property.getType(), equalTo(PropertyType.REAL));
        assertThat(property.getValueAsReal(), closeTo(new BigDecimal(
                "3.141592653589793238462643383279502884197169399375105820974944592"),
                BigDecimal.ZERO));
    }

    @Test
    public void findTextProperty() {
        Category category = categoryDao.findByName("second_category");
        Property property =
                propertyDao.findByNameAndCategoryId("text_property", category.getId());
        assertThat(property.getType(), equalTo(PropertyType.TEXT));
        assertThat(property.getTypedValue().toString(), equalTo(
                "Here you can have a very long text."));
    }

    @Test
    public void setStringValue() {
        Category category = categoryDao.findByName("first_category");
        Property property =
                propertyDao.findByNameAndCategoryId("first_property", category.getId());
        String newValue = "A new value";
        property.setTypedValue(newValue);
        assertThat(property.getValue(), is(sameInstance(newValue)));
        Property oldObject = property;
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId("first_property", category.getId());
        assertThat(property, is(not(sameInstance(oldObject))));
        assertThat(property.getTypedValue().toString(), equalTo(newValue));
    }

    @Test
    public void setIntegerValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "integer_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        Integer newValue = Integer.valueOf(25);
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertThat(property.getValueAsInteger(), equalTo(newValue));
    }

    @Test
    public void setRealValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "real_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        BigDecimal newValue = BigDecimal.valueOf(10.50);
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertThat(property.getValueAsReal(), equalTo(newValue));
    }

    @Test
    public void setBooleanValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "boolean_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        Boolean newValue = Boolean.FALSE;
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertThat(property.getValueAsBoolean(), equalTo(newValue));
    }

    @Test
    public void setTextValue() {
        Category category = categoryDao.findByName("second_category");
        String propertyName = "text_property";
        Property property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        String newValue = "Another long text!";
        property.setTypedValue(newValue);
        fixAndClearSession(property);
        property =
                propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        assertThat(property.getValue(), equalTo(newValue));
    }

    private void fixAndClearSession(Property property) throws DataAccessException {
        propertyDao.store(property);
        HibernateTemplate hibernateTemplate = ((HibernateGenericDao) propertyDao).getHibernateTemplate();
        hibernateTemplate.flush();
        hibernateTemplate.evict(property);
    }
}