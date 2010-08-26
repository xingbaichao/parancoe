// Copyright 2008 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.web.test.controller;



import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.parancoe.web.validation.Validation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/test/controller.form")
@SessionAttributes("something")
public class TestController {

    private static final Logger logger = Logger.getLogger(TestController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String form(@RequestParam("value") String value,
            Model model) {
        TestControllerModel tcm = new TestControllerModel(value);
        model.addAttribute("something", tcm);
        return "test/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute("something") @Valid TestControllerModel tcm,
            BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "test/form";
        }
        // doing what you need with tcm and the other parameters
        status.setComplete();
        return "redirect:/test/done.html";
    }
}
