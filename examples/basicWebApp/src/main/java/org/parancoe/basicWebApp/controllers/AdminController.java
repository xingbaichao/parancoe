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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.parancoe.util.MemoryAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


//@DefaultUrlMapping
@Controller
@RequestMapping("/admin/*.html")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("admin/index", null);
    }

    @RequestMapping
    public ModelAndView logs(HttpServletRequest req, HttpServletResponse res) {
        if ("true".equals(req.getParameter("clean"))) {
            MemoryAppender.clean();
        }

        if ("error".equals(req.getParameter("test"))) logger.error("sample error message");
        if ("warn".equals(req.getParameter("test"))) logger.warn("sample warn message");

        String log = MemoryAppender.getFullLog();
        log = colourLog(log);

        Map params = new HashMap();
        params.put("log", log);
        return new ModelAndView("admin/logs", params);
    }

    @RequestMapping
    public ModelAndView conf(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("admin/conf", null);
    }

    @RequestMapping
    public ModelAndView spring(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("admin/spring", null);
    }

    @RequestMapping
    public ModelAndView system(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("admin/system", null);
    }


    private String colourLog(String log) {
        String lines[];
        if (log == null) lines = new String[]{""};
        else lines = log.split("[\\n\\r]");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].indexOf("[ERROR]") != -1) lines[i] = "<span class=\"log_error\">" + lines[i] + "</span>";
            if (lines[i].indexOf("[WARN]") != -1) lines[i] = "<span class=\"log_warn\">" + lines[i] + "</span>";
            if (StringUtils.isNotBlank(lines[i])) lines[i] += "<br/>";
        }
        return StringUtils.join(lines);
    }


    public Logger getLogger() {
        return logger;
    }
}
