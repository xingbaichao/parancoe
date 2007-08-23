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

import java.util.List;
import java.util.Set;

import it.jugpadova.Blos;
import it.jugpadova.Daos;
import it.jugpadova.bean.EnableJugger;
import it.jugpadova.exception.UserAlreadyEnabledException;
import it.jugpadova.po.Jugger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.parancoe.web.BaseFormController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public abstract class JuggerEnableController extends BaseFormController {

	private static final Logger logger = Logger
			.getLogger(JuggerEnableController.class);

	@Override
	protected ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception {

		EnableJugger ej = (EnableJugger) command;
		String confirmationCode = req.getParameter("code");
		String password = ej.getPassword();
		logger.info("confirmationCode: " + confirmationCode);
		try {
			blo().getJuggerBO().enableJugger(confirmationCode, password);
		} catch (UserAlreadyEnabledException uaee) {
			return new ModelAndView("redirect:/jugger/already.html");
		} catch (Exception e) {
			logger.error(e, e);
			return new ModelAndView("redirect:/jugger/failed.html");
		}
		return onSubmit(command, errors);

	}

	@Override
	protected Object formBackingObject(HttpServletRequest req) throws Exception {
		String confirmationCode = null;
		if ((confirmationCode = req.getParameter("code")) == null) {
			throw new Exception("No code found in the request!");
		}
		List<Jugger> juggers = dao().getJuggerDao().findByConfirmationCode(
				confirmationCode);
		if (juggers.size() == 0) {
			throw new Exception(
					"There is no code associated with this confirmationCode: "
							+ confirmationCode);
		}
		req.setAttribute("jugger", juggers.get(0));
		return new EnableJugger();
	}

	public Logger getLogger() {
		return logger;
	}

	protected abstract Daos dao();

	protected abstract Blos blo();

}
