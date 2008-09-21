/*
 *  Copyright 2008 lucio.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.parancoe.web.test.controller;

import org.parancoe.web.test.ControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

/**
 * A test for the TestController controller.
 * 
 * It's mostly an integration test for the Parancoe controller stack, including validation.
 * And an example of how to write and test controllers.
 * 
 * @author Lucio Benfante
 */
public class TestControllerTest extends ControllerTest {
    @Autowired
    private TestController controller;
    @Autowired
    @Qualifier("methodHandler")
    private HandlerAdapter handler;

    public void testConfiguration() {
        assertNotNull(controller);
        assertNotNull(handler);
    }
    
    public void testSubmit() throws Exception {
        callForm("initial value");
        resetRequestAndResponse();
        req.setMethod("POST");
        req.setRequestURI("/test/controller.form");
        req.setParameter("value", "modified value");
        ModelAndView mv = handler.handle(req, res, controller);
        assertEquals("redirect:/test/done.html", mv.getViewName());
        Object oSomething = mv.getModel().get("something");
        assertNotNull(oSomething);
        assertEquals("modified value", ((TestControllerModel)oSomething).getValue());
        Object sSomething = req.getSession().getAttribute("something");
        assertNull("The something attribute should have been removed from the session", sSomething);
    }

    public void testUpdateFailedForValidation() throws Exception {
        callForm("initial value");
        resetRequestAndResponse();
        req.setMethod("POST");
        req.setRequestURI("/test/controller.form");
        req.setParameter("value", ""); // not valid empty value
        ModelAndView mv = handler.handle(req, res, controller);
        assertEquals("test/form", mv.getViewName());
        Object sSomething = req.getSession().getAttribute("something");
        assertNotNull("The something attribute shouldn't have been removed from the session", sSomething);
    }
    
    public void testForm() throws Exception {
        ModelAndView mv = callForm("initial value");
        Object oSomething = mv.getModel().get("something");
        assertNotNull(oSomething);
        assertTrue("The object is not of type TestControllerModel", oSomething instanceof TestControllerModel);
        Object sSomething = req.getSession().getAttribute("something");
        assertNotNull(sSomething);
        assertTrue("The object is not of type TestControllerModel", sSomething instanceof TestControllerModel);
        assertSame(oSomething, sSomething);
        assertEquals("initial value", ((TestControllerModel)oSomething).getValue());
        assertEquals("test/form", mv.getViewName());
    }
    
    private ModelAndView callForm(String value) throws Exception {
        req.setMethod("GET");
        req.setRequestURI("/test/controller.form");
        req.setParameter("value", value);
        ModelAndView mv =  handler.handle(req, res, controller);
        this.endTransaction();
        this.startNewTransaction();
        return mv;
    }
    
    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{ };
    }
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:org/parancoe/persistence/dao/generic/genericDao.xml",
                    "classpath:org/parancoe/persistence/applicationContextBase.xml",
                    "classpath:org/parancoe/web/parancoeBase.xml", "classpath:spring-test.xml"};
    }
    
}
