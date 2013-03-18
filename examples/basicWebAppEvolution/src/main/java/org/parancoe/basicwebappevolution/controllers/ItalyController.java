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

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/italy/*.html")
public class ItalyController {
    @RequestMapping
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("italy/index", null);
    }
}
