/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Configuration.
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
package org.parancoe.plugin.configuration.controllers;


import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parancoe.plugin.configuration.bo.ConfigurationManager;
import org.parancoe.plugin.configuration.po.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/plugin/configuration")
public class ConfigurationController {

    private static final Logger logger = Logger.getLogger(ConfigurationController.class);

    @Resource
    private ConfigurationManager configurationManager;
    
    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res, Model model) {
        logger.info("Executing index in the ConfigurationController");
        List<Category> categories = configurationManager.loadCategories();
        model.addAttribute("pluginConfigurationCategories", categories);
        return "plugin/configuration/index";
    }

}
