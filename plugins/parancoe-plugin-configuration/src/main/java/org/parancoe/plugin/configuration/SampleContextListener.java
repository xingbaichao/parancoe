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

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;

/**
 * A sample context listener for the plugin
 *
 * @author paolo.dona@seesaw.it
 * @author lucio.benfante@jugpadova.it
 */
@Component("parancoe-plugin-configurationSampleContextListener")
public class SampleContextListener extends ContextLoaderListener {

    private static final Logger log = Logger.getLogger(SampleContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("parancoe-plugin-configuration contextInitialized");
    }
}
