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
package org.parancoe.web.test;

import org.junit.Test;
import org.parancoe.web.test.junit4.AbstractControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.ModelAndViewAssert.*;

/**
 * A test for the TestController controller.
 *
 * It's mostly an integration test for the Parancoe controller stack, including validation. And an
 * example of how to write and test controllers.
 *
 * @author Lucio Benfante
 * @author michele franzin <michele at franzin.net>
 */
public class MockControllerTest extends AbstractControllerTest {

    @Autowired
    private MockController controller;
    @Autowired
    private HandlerAdapter handler;

    @Test
    public void sanity() {
        assertThat(controller, is(notNullValue()));
        assertThat(handler, is(notNullValue()));
        assertThat(handler.supports(controller), is(true));
    }

    @Test
    public void submit() throws Exception {
        getForm("initial value");
        resetRequestAndResponse();
        ModelAndView mv = postValue("modified value");

        assertViewName(mv, "redirect:/test/done.html");
        assertModelAttributeAvailable(mv, "something");
        MockModel oSomething = (MockModel) mv.getModel().get("something");
        assertThat(oSomething.getValue(), equalTo("modified value"));
        assertThat("The something attribute should have been removed from the session",
                request.getSession().getAttribute("something"), is(nullValue()));
    }

    @Test
    public void updateFailedForValidation() throws Exception {
        getForm("initial value");
        resetRequestAndResponse();
        ModelAndView mv = postValue("");  // not valid empty value
        assertViewName(mv, "test/form");
        assertThat("The something attribute shouldn't have been removed from the session",
                request.getSession().getAttribute("something"), is(notNullValue()));
    }

    @Test
    public void form() throws Exception {
        ModelAndView mv = getForm("initial value");
        assertModelAttributeAvailable(mv, "something");
        Object oSomething = mv.getModel().get("something");
        assertThat("The object is not of type TestControllerModel",
                oSomething, is(instanceOf(MockModel.class)));
        Object sSomething = request.getSession().getAttribute("something");
        assertThat(sSomething, is(notNullValue()));
        assertThat("The object is not of type TestControllerModel",
                sSomething, is(instanceOf(MockModel.class)));
        assertThat(oSomething, is(sameInstance(sSomething)));
        assertThat(((MockModel) oSomething).getValue(), equalTo("initial value"));
        assertViewName(mv, "test/form");
    }

    private ModelAndView getForm(String value) throws Exception {
        request.setMethod("GET");
        request.setRequestURI("/test/controller.form");
        request.setParameter("value", value);
        return handler.handle(request, response, controller);
    }

    private ModelAndView postValue(String value) throws Exception {
        request.setMethod("POST");
        request.setRequestURI("/test/controller.form");
        request.setParameter("value", value);
        return handler.handle(request, response, controller);
    }
}
