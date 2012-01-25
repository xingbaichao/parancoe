/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Spring Security Evolution.
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
package org.parancoe.plugins.securityevolution;

import org.apache.log4j.Logger;
import org.parancoe.web.plugin.ApplicationContextPlugin;
import org.parancoe.web.plugin.WebPlugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

public class SanityTest extends PluginTest {
	private static Logger logger = Logger.getLogger(
			SanityTest.class);



    @Resource(name="applicationContextpluginSecurityEvolutionConfig")
    private ApplicationContextPlugin applicationContextPlugin;
        
    /* test everything has been loaded properly */
    public void testSanity() {
       assertNotNull(applicationContextPlugin);       
       for(String s:applicationContext.getBeanDefinitionNames())
        {
    	   logger.debug("bean: "+s);
        }
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }
    
}