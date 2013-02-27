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

import org.parancoe.plugin.configuration.dao.CategoryDao;
import org.parancoe.plugin.configuration.dao.PropertyDao;
import org.parancoe.web.plugin.WebPlugin;
import org.parancoe.web.test.junit4.AbstractPluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 * test everything has been loaded properly
 *
 * @author michele franzin <michele at franzin.net>
 */
public class ParancoeTest extends AbstractPluginTest {

    @Autowired
    @Qualifier("parancoe-plugin-configurationPluginConfig")
    private WebPlugin plugin;
    @Autowired
    private InitializerContextListener sampleContextListener;
    @Autowired
    private SampleInterceptor sampleInterceptor;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PropertyDao propertyDao;

    @Test
    public void sanity() {
        assertThat(plugin, is(notNullValue()));
        assertThat(sampleContextListener, is(notNullValue()));
        assertThat(sampleInterceptor, is(notNullValue()));
        assertThat(categoryDao, is(notNullValue()));
        assertThat(propertyDao, is(notNullValue()));
    }
}