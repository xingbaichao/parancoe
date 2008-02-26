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
import it.jugpadova.bean.PasswordRecovery;
import it.jugpadova.po.Jugger;
import it.jugpadova.util.Utilities;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.parancoe.web.BaseFormController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for password recovery.
 *
 * @author Enrico Giurin
 *
 */
public abstract class PasswordRecoveryController extends BaseFormController {

    private static final Logger logger =
            Logger.getLogger(PasswordRecoveryController.class);



    @Override
    protected ModelAndView onSubmit(HttpServletRequest req,
            HttpServletResponse res, Object command,
            BindException errors) throws Exception {

        PasswordRecovery pr = (PasswordRecovery) command;
        String email = pr.getEmail();
        logger.debug("email: " + email);
        Jugger jugger = dao().getJuggerDao().findByEmail(email);
        if (jugger == null) {
            errors.rejectValue("email", "juggerNotFoundByEmail");
            return showForm(req, res, errors);
        }
        if (jugger.getUser().isEnabled() == false) {
            errors.rejectValue("email", "juggerBlocked");
            return showForm(req, res, errors);
        }
        blo().getJuggerBO().
                passwordRecovery(jugger,
                Utilities.getBaseUrl(req));
        ModelAndView mv = onSubmit(command, errors);
        Utilities.addMessageArguments(mv, jugger.getEmail());
        return mv;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        return new PasswordRecovery();
    }

    public Logger getLogger() {
        return logger;
    }

    protected abstract Daos dao();

    protected abstract Blos blo();
} // end of class
