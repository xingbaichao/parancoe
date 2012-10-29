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
import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parancoe.plugin.configuration.bo.ConfigurationService;
import org.parancoe.plugin.configuration.po.Category;
import org.parancoe.plugin.configuration.po.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test CXF REST services
 *
 * @author Arjun Dhar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:org/parancoe/plugin/configuration/rest-test-client.xml"
})
public class RestServicesTest {

    private static Logger log = LoggerFactory.getLogger(RestServicesTest.class);
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
    public void testGetPropertyByCategoryAndName() throws Exception {
        Property property = proxy.getProperty("first_category", "first_property");
        Assert.assertNotNull(property);
        Assert.assertEquals("first_property", property.getName());
        Assert.assertEquals("first_category", property.getCategory().getName());
    }
    
    @Test
    public void testGetPropertyById() throws Exception {
        Property startProperty = proxy.getProperty("first_category", "first_property");
        Property property = proxy.getProperty(startProperty.getId());
        Assert.assertNotNull(property);
        Assert.assertEquals(startProperty.getId(), property.getId());
    }

    @Test
    public void testLoadCategories() throws Exception {
        ConfigurationCollection configuration = proxy.getConfiguration();
        Assert.assertNotNull(configuration.getCategories());
        Assert.assertEquals(2, configuration.getCategories().size());
    }

    @Test
    public void testGetProperties() throws Exception {
        PropertyCollection properties = proxy.getProperties("first_category");
        Assert.assertNotNull(properties.getProperties());
        Assert.assertEquals(2, properties.getProperties().size());
    }
    
    @Test
    public void testXmlGetPropertyByCategoryAndName() throws Exception {
        Property property = proxyXml.getProperty("first_category", "first_property");
        Assert.assertNotNull(property);
        Assert.assertEquals("first_property", property.getName());
        Assert.assertEquals("first_category", property.getCategory().getName());
    }
    
    @Test
    public void testXmlGetPropertyById() throws Exception {
        Property startProperty = proxyXml.getProperty("first_category", "first_property");
        Property property = proxyXml.getProperty(startProperty.getId());
        Assert.assertNotNull(property);
        Assert.assertEquals(startProperty.getId(), property.getId());
    }

    @Test
    public void testXmlLoadCategories() throws Exception {
        ConfigurationCollection categories = proxyXml.getConfiguration();
        Assert.assertNotNull(categories.getCategories());
        Assert.assertEquals(2, categories.getCategories().size());
    }

    @Test
    public void testXmlGetProperties() throws Exception {
        PropertyCollection properties = proxyXml.getProperties("first_category");
        Assert.assertNotNull(properties.getProperties());
        Assert.assertEquals(2, properties.getProperties().size());
    }
    
}
