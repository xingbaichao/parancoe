/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
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

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lucio.benfante@jugpadova.it
 */
public class WorldInterceptor extends HandlerInterceptorAdapter {

    public static final Logger logger = Logger.getLogger(WorldInterceptor.class);

    public WorldInterceptor() {
        logger.info("WorldInterceptor set up");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object object) throws Exception {
        logger.debug("WorldPlugin interceptor preHandle");
        return true;
    }
}