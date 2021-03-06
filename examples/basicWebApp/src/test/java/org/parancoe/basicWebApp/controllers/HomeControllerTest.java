/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic WebApp.
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
package org.parancoe.basicWebApp.controllers;

import javax.annotation.Resource;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.mock.web.MockHttpServletRequest;
import org.parancoe.web.test.ControllerTest;
import org.springframework.web.servlet.HandlerAdapter;

public class HomeControllerTest extends ControllerTest {

    @Resource
    private HomeController controller;
    @Resource(name="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter#0")
    private HandlerAdapter methodHandler;

    public void testConfiguration() {
        assertNotNull(controller);
        assertNotNull(methodHandler);
    }

    public void testWelcome() throws Exception {
        resetRequestAndResponse();
        req.setMethod("GET");
        req.setRequestURI("/home/welcome.html");
        req = new MockHttpServletRequest("GET", "/home/welcome.html");
        ModelAndView mv = methodHandler.handle(req, res, controller);
        assertEquals("welcome", mv.getViewName());
        assertNotNull(mv.getModel().get("something"));
    }
}
