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
