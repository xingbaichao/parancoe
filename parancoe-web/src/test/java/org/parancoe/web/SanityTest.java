/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Web.
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

package org.parancoe.web;

//import javax.annotation.Resource;
//import org.parancoe.util.BaseConf;
import org.parancoe.web.test.BaseTest;

public class SanityTest extends BaseTest {

//    @Resource
//    private BaseConf developmentConfiguration;

    public void testSanity() throws Exception {
//        assertNotNull(developmentConfiguration);
    }

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{
                    "classpath:org/lambico/spring/dao/hibernate/genericDao.xml",
                    "classpath:org/lambico/spring/dao/hibernate/applicationContextBase.xml",
                    "classpath:org/parancoe/web/parancoeBase.xml",
                    "classpath:spring-test.xml"};
    }
}
