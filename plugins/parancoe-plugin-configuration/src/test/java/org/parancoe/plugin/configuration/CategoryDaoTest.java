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

import java.util.List;
import org.parancoe.plugin.configuration.dao.CategoryDao;
import org.parancoe.plugin.configuration.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import org.junit.Test;

public class CategoryDaoTest extends BaseTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void findAll() {
        List<Category> results = categoryDao.findAll();
        assertThat(results, hasSize(2));
    }

    @Test
    public void findAllOrdered() {
        List<Category> results = categoryDao.findByOrderByName();
        assertThat(results, hasSize(2));
    }

    @Test
    public void findByName() {
        Category result = categoryDao.findByName("first_category");
        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), equalTo("first_category"));
        assertThat(result.getProperties(), hasSize(1));
    }
}