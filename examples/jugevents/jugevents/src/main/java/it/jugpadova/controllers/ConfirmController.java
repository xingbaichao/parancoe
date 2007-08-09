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
package it.jugpadova.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.parancoe.web.BaseMultiActionController;
import it.jugpadova.Daos;
import it.jugpadova.Blos;
import it.jugpadova.blo.EventBo;
import it.jugpadova.po.Participant;

/**
 *
 */
public abstract class ConfirmController extends BaseMultiActionController {
    private static final Logger logger = Logger.getLogger(ConfirmController.class);

    /**
     *
     */
    public ModelAndView registration(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView result = null;
        EventBo eventBo = blo().getEventBo();
        Participant participant = eventBo.confirmParticipant(req.getParameter("email"), req.getParameter("code"));
        if (participant != null) {
            result = new ModelAndView("event/registration/ok");
            result.addObject("participant", participant);
        } else {
            result = new ModelAndView("event/registration/failed");
        }
        return result;
    }


    /**
     * You don't have to implement this.
     *
     * @return The provider of DAOs
     */
    protected abstract Daos dao();

    /**
     * You don't have to implement this.
     *
     * @return The provider of business logic objects
     */
    protected abstract Blos blo();
}
