/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
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

import org.parancoe.web.plugin.WebPlugin;
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SanityTest extends PluginTest {

    @Autowired
    @Qualifier("parancoe-plugin-springsecurity-evolutionPluginConfig")
    private WebPlugin plugin;

    /* test everything has been loaded properly */
    public void testSanity() {

        /*for (String beanName : applicationContext.getBeanDefinitionNames()) {
                try {
                        Object bean = applicationContext.getBean(beanName);
                        System.out.println("beanName: " + beanName + " - class: "
                                        + bean.getClass().getName());
                } catch (BeansException e) {
                        System.err.println("Error with beanName: "+beanName);
                }
        }*/
        assertNotNull(plugin);
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }
}