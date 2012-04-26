// Copyright 2006-2007 The Parancoe Team
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
package org.parancoe.basicwebappevolution.controllers;

import java.text.ParseException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.parancoe.basicwebappevolution.blo.PersonBo;
import org.parancoe.basicwebappevolution.dao.PersonDao;
import org.parancoe.basicwebappevolution.po.Person;
import org.parancoe.web.util.FlashHelper;
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
@RequestMapping("/person")
@SessionAttributes("person")
public class PersonController {

    private static Logger logger = Logger.getLogger(PersonController.class);
    @Resource
    private PersonDao personDao;
    @Resource
    private PersonBo personBo;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("people", personDao.findAll());
        return "person/list";
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public String populate(HttpServletRequest req) throws ParseException {
        personBo.populateArchive();
        FlashHelper.setRedirectNotice(req, "Person list has been populated.");
        return "redirect:";
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    public String show(@PathVariable("personId") Long personId, Model model) {
        logger.debug("got id " + personId);
        Person p = personDao.read(personId);
        model.addAttribute("person", p);
        return "person/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newObject(Model model) {
        Person p = new Person();
        model.addAttribute("person", p);
        return "person/edit";
    }

    @RequestMapping(value = "/{personId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("personId") Long personId, Model model) {
        Person p = personDao.read(personId);
        model.addAttribute("person", p);
        return "person/edit";
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST})
    public String store(@ModelAttribute("person") @Valid Person person,
            BindingResult result, SessionStatus status, HttpServletRequest req) {
        if (result.hasErrors()) {
            return "person/edit";
        } else {
            personDao.store(person);
            status.setComplete();
            logger.info("Stored the person (" + person + ")");
            FlashHelper.setRedirectNotice(req,
                    "The person data have been stored.");
            return "redirect:";
        }
    }

    @RequestMapping(value = "/{personId}", method={RequestMethod.DELETE})
    public String delete(@PathVariable("personId") Long personId, HttpServletRequest req) {
        Person p = personDao.read(personId);
        personDao.delete(p);
        FlashHelper.setRedirectNotice(req, "The person data have been deleted.");
        return "redirect:";
    }

}
