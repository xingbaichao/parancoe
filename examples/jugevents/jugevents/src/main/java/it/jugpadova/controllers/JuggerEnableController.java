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

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.bean.EnableJugger;
import it.jugpadova.exception.UserAlreadyEnabledException;
import it.jugpadova.po.Jugger;
import it.jugpadova.util.Utilities;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.web.BaseFormController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public abstract class JuggerEnableController extends BaseFormController {

    private static final Logger logger =
            Logger.getLogger(JuggerEnableController.class);
    private static final String JUGGER_ATTRIBUTE = "jugger";

    @Override
    protected ModelAndView onSubmit(HttpServletRequest req,
            HttpServletResponse res, Object command,
            BindException errors) throws Exception {

        EnableJugger ej = (EnableJugger) command;
        Jugger jugger = (Jugger) req.getAttribute(JUGGER_ATTRIBUTE);
        String password = ej.getPassword();
        try {
            blo().getJuggerBO().enableJugger(jugger, password);
        } catch (UserAlreadyEnabledException uaee) {
            return Utilities.getMessageView("jugger.registration.already");
        } catch (Exception e) {
            logger.error(e, e);
            return Utilities.getMessageView("jugger.registration.failed");
        }
        ModelAndView mv = onSubmit(command, errors);
        Utilities.addMessageArguments(mv, jugger.getFirstName());
        return mv;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        String username = null;
        String confirmationCode = null;
        if ((username = req.getParameter("username")) == null) {
            throw new Exception("No username found in the request!");
        }
        if ((confirmationCode = req.getParameter("code")) == null) {
            throw new Exception("No code found in the request!");
        }
        Jugger jugger =
                dao().getJuggerDao().
                findByUsernameAndConfirmationCode(username, confirmationCode);
        if (jugger == null) {
            logger.warn("Trying to enable " + username +
                    " user, but it doesn't exist or the confirmation code (" +
                    confirmationCode + ") doesn't correspond");
            throw new Exception("The " + username +
                    " user doesn't exist, or the confirmation code (" +
                    confirmationCode + ") doesn't correspond");
        }
        req.setAttribute(JUGGER_ATTRIBUTE, jugger);
        return new EnableJugger();
    }

    public Logger getLogger() {
        return logger;
    }

    protected abstract Daos dao();

    protected abstract Blos blo();
}
