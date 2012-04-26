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
import org.parancoe.web.test.PluginTest;
import org.springframework.beans.factory.annotation.Autowired;

public class SampleDaoTest extends PluginTest {
    
    @Autowired
    private SampleDao sampleDao;
    
    /* test everything has been loaded properly */
    public void testFindAll() {
        List<Sample> results = sampleDao.findAll();
        assertSize(2, results);
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Sample.class};
    }
    
}