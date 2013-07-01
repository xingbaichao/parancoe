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

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import javax.servlet.ServletContextEvent;
import org.parancoe.plugin.configuration.bo.ConfigurationService;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * A context listener initializing configuration catregories and properties.
 * It searches all the beans of type {@link ConfigurationCollection} defined in the WebApplicationContext
 * and creates all the elements not already existent.
 *
 * @author Lucio Benfante <lucio.benfante@jugpadova.it>
 */
@Component("parancoe-plugin-configurationInitializerContextListener")
public class InitializerContextListener extends ContextLoaderListener {

    private static final Logger log = LoggerFactory.getLogger(InitializerContextListener.class);

    @Resource
    private ConfigurationService configurationService;

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("parancoe-plugin-configuration contextInitialized");
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
        Map<String, ConfigurationCollection> configurationCollections =
                webApplicationContext.getBeansOfType(ConfigurationCollection.class);
        log.info("Found "+ configurationCollections.size()+" configuration collections.");
        for (Map.Entry<String, ConfigurationCollection> entry : configurationCollections.entrySet()) {
            log.info("Loading "+entry.getKey()+" configuration collection...");
            ConfigurationCollection value = entry.getValue();
            configurationService.initializeConfiguration(value);
        }
    }
}
