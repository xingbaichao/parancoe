/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Core.
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
package org.parancoe.core;

import javax.annotation.Resource;
import org.junit.Test;
import org.parancoe.util.BaseConf;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * test everything has been loaded properly
 *
 * @author michele franzin <michele at franzin.net>
 */
public class ParancoeTest extends BaseTest {

    @Resource
    private BaseConf configuration;

    @Test
    public void sanity() {
        assertThat(configuration, is(notNullValue()));
    }
}
