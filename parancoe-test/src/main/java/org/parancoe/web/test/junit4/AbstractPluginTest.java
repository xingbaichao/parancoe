/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Test.
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
package org.parancoe.web.test.junit4;

import org.springframework.test.context.ContextConfiguration;

/**
 * E' la classe base per i test di plugins.
 *
 * @author michele franzin <michele at franzin.net>
 */
@ContextConfiguration(inheritLocations = false,
        locations = {"/org/lambico/spring/dao/hibernate/genericDao.xml",
    "/org/lambico/spring/dao/hibernate/applicationContextBase.xml",
    "/org/parancoe/core/applicationContextBase.xml",
    "/org/parancoe/web/parancoeBase.xml",
    "/database-test.xml",
    "classpath*:parancoe-plugin.xml",
    "classpath*:applicationContext-plugin.xml",
    "/applicationContext-test.xml"})
public abstract class AbstractPluginTest extends AbstractWebTest {
}
