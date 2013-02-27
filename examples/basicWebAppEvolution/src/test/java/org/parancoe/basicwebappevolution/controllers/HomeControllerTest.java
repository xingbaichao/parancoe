/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic WebApp Evolution.
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
package org.parancoe.basicwebappevolution.controllers;

import org.junit.Test;
import org.parancoe.web.test.junit4.AbstractControllerTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerAdapter;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.ModelAndViewAssert.*;

/**
 * test home controller
 *
 * @author michele franzin <michele at franzin.net>
 */
public class HomeControllerTest extends AbstractControllerTest {

    @Autowired
    private HomeController controller;
    @Autowired
    private HandlerAdapter handler;

    @Test
    public void sanity() {
        assertThat(controller, is(notNullValue()));
        assertThat(handler, is(notNullValue()));
        assertThat(handler.supports(controller), is(true));
    }

    @Test
    public void welcome() throws Exception {
        ModelAndView mv = getPage("/home/welcome.html");
        assertViewName(mv, "welcome");
        assertModelAttributeAvailable(mv, "something");
    }

    private ModelAndView getPage(String pagePath) throws Exception {
        request.setMethod("GET");
        request.setRequestURI(pagePath);
        return handler.handle(request, response, controller);
    }
}
