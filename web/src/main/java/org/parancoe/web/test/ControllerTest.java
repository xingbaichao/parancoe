// Copyright 2006 The Parancoe Team
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.web.test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public abstract class ControllerTest extends BaseTest {

    protected MockMultipartHttpServletRequest mpReq;
    protected MockHttpServletRequest req;
    protected MockHttpServletResponse res;

    public void setUp() throws Exception {
        super.setUp();
        // preparo la richiesta multipart
        mpReq = new MockMultipartHttpServletRequest();
        mpReq.setMethod("GET");
        mpReq.setContextPath("/specialitaly");
        // preparo la richiesta normale
        req = new MockHttpServletRequest();
        req.setMethod("GET");
        req.setContextPath("/specialitaly");
        res = new MockHttpServletResponse();
    }

    public void tearDown() throws Exception {
        super.tearDown();
        req = null;
        res = null;
    }
}