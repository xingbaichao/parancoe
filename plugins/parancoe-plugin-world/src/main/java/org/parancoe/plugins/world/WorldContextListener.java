/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin World.
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
package org.parancoe.plugins.world;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author lucio.benfante@jugpadova.it
 */
public class WorldContextListener extends ContextLoaderListener {

    private static final Logger log = Logger.getLogger(WorldContextListener.class);

    private ServletContext servletContext;

    private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent evt) {
      log.info("WorldPlugin contextInitialized"); 
    }
}
