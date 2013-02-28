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
package org.parancoe.web.test;

import javax.servlet.http.HttpSession;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.web.util.WebUtils;

/**
 * @deprecated please use {@link AbstractContextTest}
 */
@Deprecated
public abstract class TagTest extends BaseTest {

    protected MockMultipartHttpServletRequest mpReq;
    protected MockHttpServletRequest req;
    protected MockHttpServletResponse res;
    protected MockPageContext pc;
    protected RequestContext rc;

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
        pc = null;
    }

    /**
     * Reset the request and the response, maintaining the same session. Useful, for example, to
     * call a post after calling the get of the form.
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
        req.setContextPath("/testctx");
        req.setRequestURI("/testctx/test/request/uri");
        req.setAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE, "/testctx/test/forward/request/uri");
        req.setQueryString("p1=v1&p2=v2&p3=v3");
        res = new MockHttpServletResponse();
        pc = new MockPageContext(((WebApplicationContext) this.getApplicationContext()).
                getServletContext(), req, res);
        rc = new JspAwareRequestContext(pc);
        pc.setAttribute(RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE, rc);
    }

}
