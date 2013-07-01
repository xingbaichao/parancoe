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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.parancoe.plugin.configuration.bo.ConfigurationService;
import org.parancoe.plugin.configuration.po.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Test CXF REST services
 *
 * @author Arjun Dhar
 */
@ContextConfiguration(locations = {"/org/parancoe/plugin/configuration/rest-test-client.xml"})
public class RestServicesTest extends AbstractJUnit4SpringContextTests {

    private static SelfServer server;
    @Autowired
    @Qualifier("testclient")
    protected ConfigurationService proxy;
    @Autowired
    @Qualifier("testclientxml")
    protected ConfigurationService proxyXml;

    @BeforeClass
    public static void startServer() throws Exception {
        /* please note, in actuality, for multiple tests you will have
         to ensure a single version of the server is running only.

         For each test case, it will invoke start and will give an error.
         This is simplified for Blog consumption here only. */
        server = new SelfServer();
        server.start();
    }

    @AfterClass
    public static void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void getPropertyByCategoryAndName() throws Exception {
        Property property = proxy.getProperty("first_category", "first_property");
        assertThat(property, is(notNullValue()));
        assertThat(property.getName(), equalTo("first_property"));
        assertThat(property.getCategory().getName(), equalTo("first_category"));
    }

    @Test
    public void getPropertyById() throws Exception {
        Property startProperty = proxy.getProperty("first_category", "first_property");
        Property property = proxy.getProperty(startProperty.getId());
        assertThat(property, is(notNullValue()));
        assertThat(property.getId(), equalTo(startProperty.getId()));
    }

    @Test
    public void loadCategories() throws Exception {
        ConfigurationCollection configuration = proxy.getConfiguration();
        assertThat(configuration.getCategories(), is(notNullValue()));
        assertThat(configuration.getCategories(), hasSize(2));
    }

    @Test
    public void getProperties() throws Exception {
        PropertyCollection properties = proxy.getProperties("first_category");
        assertThat(properties.getProperties(), is(notNullValue()));
        assertThat(properties.getProperties(), hasSize(2));
    }

    @Test
    public void xmlGetPropertyByCategoryAndName() throws Exception {
        Property property = proxyXml.getProperty("first_category", "first_property");
        assertThat(property, is(notNullValue()));
        assertThat(property.getName(), equalTo("first_property"));
        assertThat(property.getCategory().getName(), equalTo("first_category"));
    }

    @Test
    public void xmlGetPropertyById() throws Exception {
        Property startProperty = proxyXml.getProperty("first_category", "first_property");
        Property property = proxyXml.getProperty(startProperty.getId());
        assertThat(property, is(notNullValue()));
        assertThat(property.getId(), equalTo(startProperty.getId()));
    }

    @Test
    public void xmlLoadCategories() throws Exception {
        ConfigurationCollection categories = proxyXml.getConfiguration();
        assertThat(categories.getCategories(), is(notNullValue()));
        assertThat(categories.getCategories(), hasSize(2));
    }

    @Test
    public void xmlGetProperties() throws Exception {
        PropertyCollection properties = proxyXml.getProperties("first_category");
        assertThat(properties.getProperties(), is(notNullValue()));
        assertThat(properties.getProperties(), hasSize(2));
    }
}
