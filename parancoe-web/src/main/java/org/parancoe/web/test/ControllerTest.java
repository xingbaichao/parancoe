// Copyright 2006-2007 The Parancoe Team
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

import javax.servlet.http.HttpSession;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public abstract class ControllerTest extends BaseTest {

    protected MockMultipartHttpServletRequest mpReq;
    protected MockHttpServletRequest req;
    protected MockHttpServletResponse res;

    @Override
    public void onSetUpBeforeTransaction() throws Exception {
        super.onSetUpBeforeTransaction();
        resetRequestAndResponse();
    }

    @Override
    public void onTearDownAfterTransaction() throws Exception {
        super.onTearDownAfterTransaction();
        mpReq = null;
        req = null;
        res = null;
    }

    /**
     * Reset the request and the response, maintaining the same session.
     * Useful, for example, to call a post after calling the get of the form.
     */
    protected void resetRequestAndResponse() {
        HttpSession httpSession = null;
        // preparing the multipart request
        if (mpReq != null) {
            httpSession = mpReq.getSession();
        }
        mpReq = new MockMultipartHttpServletRequest();
        mpReq.setSession(httpSession);
        mpReq.setMethod("GET");
        // preparing the normal request
        if (req != null) {
            httpSession = req.getSession();
        }
        req = new MockHttpServletRequest();
        req.setSession(httpSession);
        req.setMethod("GET");
        res = new MockHttpServletResponse();
    }
}
