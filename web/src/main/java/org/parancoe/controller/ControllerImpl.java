// Copyright 2006 The Parancoe Team
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
package org.parancoe.controller;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parancoe.bean.Bean;
import org.parancoe.session.SessionBean;
import org.parancoe.utility.ProfileHandler;
import org.parancoe.utility.WebKeys;
//TODO complete this trivial javadoc comment
/**
 * This class implements Controller interface
 * @author Enrico Giurin
 *
 */
public class ControllerImpl implements Controller {

	private String layout;

	private String content;

	private SessionBean session = null;

	private BeanHandler beanHandler = null;

	private ProfileHandler profileHandler = null;

	private String rootWebApp = null;

	public ControllerImpl() {
		beanHandler = new BeanHandler(this.getBasePackage());
		// TODO to change after adding a setting system
		rootWebApp = null;
		if (rootWebApp == null) {
			rootWebApp = "/";
		}
		rootWebApp = rootWebApp.replaceAll("/{2,}", "/");
	}

	public void saveBeanToRequest(Bean bean, HttpServletRequest request) {

		String beanKey = bean.getBeanKey();

		if (bean.getBeanScope().equals(WebKeys.REQUEST)) {
			if (beanKey != null) {
				if (getLogger().isDebugEnabled()) {
					getLogger().debug(
							"[saveBeanToRequest] salvo il bean [" + beanKey
									+ "] in request");
				}
				request.setAttribute(beanKey, bean);
			}
		}
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNoResponse() {
		this.content = WebKeys.NO_RESPONSE_CONTENT;
	}

	public String getContent() {
		return content;
	}

	public SessionBean getSession(HttpServletRequest request) {

		String classSessione = getSessionName();

		try {
			session = (SessionBean) request.getSession().getAttribute(
					classSessione);
			if (session == null) {
				session = (SessionBean) Class.forName(classSessione)
						.newInstance();
				session.setSessionBeanName(classSessione);
				request.getSession().setAttribute(classSessione, session);
			}
		} catch (IllegalAccessException iae) {
			if (getLogger().isErrorEnabled()) {
				getLogger().error(
						"[getSession] costruttore vuoto non accessibile", iae);
			}
		} catch (InstantiationException ie) {
			if (getLogger().isErrorEnabled()) {
				getLogger()
						.error(
								"[getSession] classe astratta, interfaccia, array di classi, tipo primitivo o void;\n oppure manca il costruttore nullo;\n oppure l'istanziazione e' fallita per qualche altro motivo.",
								ie);
			}
		} catch (ExceptionInInitializerError eie) {
			if (getLogger().isErrorEnabled()) {
				getLogger().error("[getSession] errore nell'inizializzazione",
						eie);
			}
		} catch (SecurityException se) {
			if (getLogger().isErrorEnabled()) {
				getLogger()
						.error(
								"[getSession] non si hanno i permessi per creare una nuova istanza",
								se);
			}
		} catch (Exception e) {
			if (getLogger().isErrorEnabled()) {
				getLogger().error(
						"[getSession] errore generico di inizializzazione ["
								+ classSessione.toString() + "]\n", e);
			}
		}
		return session;
	}

	public String getSessionName() {

		String name = null;
		String percorso = this.getClass().getName();

		int posName = percorso.lastIndexOf(".");
		if (posName > -1) {
			name = percorso.substring(posName + 1);
		}
		posName = name.lastIndexOf(WebKeys.CONTROLLER_POSTFIX);
		if (posName > -1) {
			name = name.substring(0, posName);
		}

		StringBuffer classSessione = new StringBuffer();
		classSessione.append(getBasePackage());
		classSessione.append(WebKeys.SESSION_PACKAGE);
		classSessione.append(".");
		classSessione.append(name);
		classSessione.append(WebKeys.SESSIONBEAN_POSTFIX);
		return classSessione.toString();

	}

	private String getBasePackage() {

		String basePackagePath = this.getClass().getName();

		int dotIndex = basePackagePath.indexOf(WebKeys.CONTROLLER_PACKAGE);
		if (dotIndex > -1) {
			basePackagePath = basePackagePath.substring(0, dotIndex);
		}

		return basePackagePath;

	}

	public BeanHandler getBeanHandler() {
		return beanHandler;
	}

	public void init(ServletContextEvent sce) throws ServletException {
	}

	public boolean redirect(String url, HttpServletResponse response) {
		getLogger().debug("url = " + url);
		if (url != null) {
			if (url.startsWith("/") && !rootWebApp.equals("/")) {
				url = url.replaceAll("^/+", "");
				url = rootWebApp + "/" + url;
				getLogger().debug("nuovo url = " + url);
			}
			response.setHeader("Location", url);
			response.setStatus(301);
			setNoResponse();
			return true;
		}
		return false;
	} // [m]redirect

	public ProfileHandler getProfileHandler() {

		if (profileHandler == null) {
			// TODO to change after adding a setting system
			String className = null;
			if (className != null) {
				try {
					profileHandler = (ProfileHandler) Class.forName(className)
							.newInstance();
				} catch (Exception doNothing) {

				}
			}
		}

		return profileHandler;
	}

	protected Log getLogger() {
		return LogFactory.getLog(this.getClass().getName());
	}

}
