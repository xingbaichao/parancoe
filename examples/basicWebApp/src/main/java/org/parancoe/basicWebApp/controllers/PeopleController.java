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
package org.parancoe.basicWebApp.controllers;

import java.text.ParseException;
import org.parancoe.basicWebApp.po.Person;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.parancoe.basicWebApp.blo.PersonBo;
import org.parancoe.basicWebApp.dao.PersonDao;
import org.parancoe.web.util.FlashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people/*.html")
public class PeopleController {

    private static final Logger logger = LoggerFactory.getLogger(PeopleController.class);
    @Resource
    private PersonDao personDao;
    @Resource
    private PersonBo personBo;

    @RequestMapping
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res) {
        Map params = new HashMap();
        params.put("people", personDao.findAll());
        return new ModelAndView("people/list", params);
    }

    @RequestMapping
    public ModelAndView populate(HttpServletRequest req, HttpServletResponse res) throws ParseException {
        Map params = new HashMap();
        personBo.populateArchive();
        FlashHelper.setRedirectNotice(req, "Popolamento eseguito");
        return new ModelAndView("redirect:list.html", params);
    }

    @RequestMapping
    public ModelAndView show(HttpServletRequest req, HttpServletResponse res) {
        Long id = Long.parseLong(req.getParameter("id"));
        logger.debug("got id {}", id);
        Map params = new HashMap();
        Person p = personDao.read(id);

        params.put("person", p);
        return new ModelAndView("people/show", params);
    }
}
