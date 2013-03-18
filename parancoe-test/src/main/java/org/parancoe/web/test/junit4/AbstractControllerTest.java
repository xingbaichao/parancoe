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

import javax.servlet.http.HttpSession;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * E' la classe base per tutti i test di controller
 *
 * @author michele franzin <michele at franzin.net>
 */
public abstract class AbstractControllerTest extends AbstractWebTest {

    protected MockMultipartHttpServletRequest multipartRequest;
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @BeforeTransaction
    public void prepareRequestAndResponse() {
        resetRequestAndResponse();
    }

    @AfterTransaction
    public void clearRequestAndResponse() {
        multipartRequest = null;
        request = null;
        response = null;
    }

    /**
     * Reset the request and the response, maintaining the same session. Useful, for example, to
     * call a post after calling the get of the form.
     */
    protected void resetRequestAndResponse() {
        HttpSession httpSession = null;
        // prepare the multipart request
        if (multipartRequest != null) {
            httpSession = multipartRequest.getSession();
        }
        multipartRequest = new MockMultipartHttpServletRequest();
        multipartRequest.setSession(httpSession);
        multipartRequest.setMethod("GET");
        // preparing the normal request
        if (request != null) {
            httpSession = request.getSession();
        }
        request = new MockHttpServletRequest();
        request.setSession(httpSession);
        request.setMethod("GET");

        response = new MockHttpServletResponse();
    }
}
