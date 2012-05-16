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
package org.parancoe.plugin.configuration.bo;

import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.parancoe.plugin.configuration.ConfigurationCollection;
import org.parancoe.plugin.configuration.dao.CategoryDao;
import org.parancoe.plugin.configuration.dao.PropertyDao;
import org.parancoe.plugin.configuration.po.Category;
import org.parancoe.plugin.configuration.po.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Services for managing the configuration.
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
@Service
public class ConfigurationManager {

    private static final Logger log = Logger.getLogger(ConfigurationManager.class);
    @Resource
    private CategoryDao categoryDao;
    @Resource
    private PropertyDao propertyDao;

    /**
     * Return a property of a category.
     *
     * @param categoryName The category name.
     * @param propertyName The property name.
     * @return A property. null if the category or the property are not found.
     */
    @Transactional(readOnly = true)
    public Property getProperty(String categoryName, String propertyName) {
        Property result = null;
        Category category = categoryDao.findByName(categoryName);
        if (category != null) {
            result = propertyDao.findByNameAndCategoryId(propertyName, category.getId());
        }
        return result;
    }

    /**
     * Load a property.
     *
     * @param id The property id.
     * @return A property. null if the property doesn't exist.
     */
    @Transactional(readOnly = true)
    public Property getProperty(Long id) {
        return propertyDao.get(id);
    }

    /**
     * Load all configuration categories.
     *
     * @return The configuration categories.
     */
    @Transactional(readOnly = true)
    public List<Category> loadCategories() {
        return categoryDao.findByOrderByName();
    }

    /**
     * Store a property.
     *
     * @param property The property to store.
     */
    @Transactional
    public void store(Property property) {
        propertyDao.store(property);
    }

    /**
     * Initialize a configuration, creating the elements (categories and properties) that doesn't
     * already exists.
     *
     * @param value The configuration collection to initialize.
     */
    @Transactional
    public void initializeConfiguration(ConfigurationCollection configurationCollection) {
        for (Category category : configurationCollection.getCategories()) {
            Category dbCategory = categoryDao.findByName(category.getName());
            if (dbCategory == null) {
                log.info("Creating new configuration category: " + category.getName());
                dbCategory = new Category();
                BeanUtils.copyProperties(category, dbCategory, new String[]{"properties"});
                categoryDao.create(dbCategory);
            } else {
                log.info("This category already exists: " + category.getName());
            }
            for (Property property : category.getProperties()) {
                Property dbProperty =
                        propertyDao.findByNameAndCategoryId(property.getName(), dbCategory.getId());
                if (dbProperty == null) {
                    log.info("  Creating new configuration property: {name=" + property.getName()
                            + ", type=" + property.getType() + ", value=" + property.getValue()
                            + "}");
                    dbProperty = new Property();
                    BeanUtils.copyProperties(property, dbProperty, new String[]{"category",
                                "typedValue"});
                    dbProperty.setCategory(dbCategory);
                    propertyDao.create(dbProperty);
                } else {
                    log.info("  This property already exists: " + property.getName());
                }
            }
        }
    }
}
