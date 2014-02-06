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
import javax.validation.Valid;

import org.slf4j.Logger;
import org.parancoe.plugin.configuration.bo.ConfigurationService;
import org.parancoe.plugin.configuration.po.Category;
import org.parancoe.plugin.configuration.po.Property;
import org.parancoe.plugin.configuration.po.PropertyType;
import org.parancoe.web.util.FlashHelper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/plugin/configuration")
@SessionAttributes("pluginConfigurationProperty")
public class ConfigurationController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    @Resource(name = "configurationManager")
    private ConfigurationService configurationService;

    @RequestMapping(method= RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res, Model model) {
        logger.info("Executing index in the ConfigurationController");
        List<Category> categories = configurationService.getConfiguration().getCategories();
        model.addAttribute("pluginConfigurationCategories", categories);
        return "plugin/configuration/index";
    }

    @RequestMapping(value = "/{propertyId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("propertyId") Long id, Model model) {
        Property property = configurationService.getProperty(id);
        if (property != null) {
            model.addAttribute("pluginConfigurationProperty", property);
        } else {
            throw new RuntimeException("Property not found: "+id);
        }
        return "plugin/configuration/edit";
    }

    @RequestMapping(value="/{propertyId}/store", method = {RequestMethod.PUT, RequestMethod.POST})
    public String store(@ModelAttribute("pluginConfigurationProperty") @Valid Property property,
            BindingResult result, SessionStatus status, HttpServletRequest req) {
        String value = property.getValue();
        if (PropertyType.STRING.equals(property.getType())) {
            if (value != null && value.length() > 255) {
                result.rejectValue("value", "PluginConfiguration_Error_MaxLengthExceeded", new Object[] {Integer.valueOf(255), Integer.valueOf(value.length())}, "You exceeded the max length (255)");
            }
        } else if (PropertyType.INTEGER.equals(property.getType())) {
            try {
                property.getValueAsInteger();
            } catch (NumberFormatException numberFormatException) {
                result.rejectValue("value", "PluginConfiguration_Error_NotAnInteger", new Object[] {value}, "The value must be an integer number.");
            }
        } else if (PropertyType.REAL.equals(property.getType())) {
            try {
                Double.valueOf(value);
            } catch (NumberFormatException numberFormatException) {
                result.rejectValue("value", "PluginConfiguration_Error_NotAReal", new Object[] {value}, "The value must be a real number.");
            }
        }
        if (result.hasErrors()) {
            return "plugin/configuration/edit";
        }
        configurationService.store(property);
        FlashHelper.setRedirectNotice(req, "PluginConfiguration_flash_PropertyUpdated");
        status.setComplete();
        return "redirect:..";
    }

}
