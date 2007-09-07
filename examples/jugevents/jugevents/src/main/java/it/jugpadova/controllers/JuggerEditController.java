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
import it.jugpadova.bean.EditJugger;
import it.jugpadova.exception.ParancoeAccessDeniedException;
import it.jugpadova.po.Jugger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.acegisecurity.Authentication;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parancoe.web.BaseFormController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller for editing Jugger informations.
 * @author Enrico Giurin
 *
 */
public abstract class JuggerEditController extends BaseFormController {

    private static final Logger logger =
            Logger.getLogger(JuggerEditController.class);



    protected void initBinder(HttpServletRequest req,
            ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

    /* questo viene chiamato solo in caso di una post a jugger/edit.form */

    @Override
    protected ModelAndView onSubmit(HttpServletRequest req,
            HttpServletResponse res, Object command,
            BindException errors) throws Exception {

        EditJugger ej = (EditJugger) command;
        if (StringUtils.isNotBlank(ej.getPassword())) {
            ej.getJugger().getUser().setPassword(ej.getPassword());
        }
        blo().getJuggerBO().update(ej.getJugger());
        ModelAndView mv = onSubmit(command, errors);
        mv.addObject("jugger", ej.getJugger());
        return mv;
    }


    @Override
    protected Object formBackingObject(HttpServletRequest req) throws Exception {
        String username = req.getParameter("jugger.user.username");
        checkAuthorization(username);
        EditJugger ej = new EditJugger();
        Jugger jugger = dao().getJuggerDao().searchByUsername(username).get(0);
        ej.setJugger(jugger);

        return ej;
    }

    public Logger getLogger() {
        return logger;
    }

    protected abstract Daos dao();

    protected abstract Blos blo();

    /**
     * Check if username corrisponds to authenticated user.
     * @param username
     * @return
     */
    private void checkAuthorization(String username) {
        // TODO add admin
        Authentication authentication =
                org.acegisecurity.context.SecurityContextHolder.getContext().
                getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();
            if (username.equals(name)) {
                return;
            }
        }
        throw new ParancoeAccessDeniedException("You are not authorized on this Jugger.");
    }
} //end of class
