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
package org.parancoe.servlet;


import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parancoe.utility.WebKeys;


public class BaseServlet extends HttpServlet {

  /** Il gestore delle richieste */
  private RequestHandler requestHandler;

  public void init(ServletConfig conf) throws ServletException {
    super.init(conf);

    String rootWebApp;
    //  TODO to change after adding a setting system
    rootWebApp = null;
    if (rootWebApp == null) {
      rootWebApp = "/";
    }
    this.getServletContext().setAttribute(WebKeys.WEBAPP_ROOT, rootWebApp);
    if (getLogger().isDebugEnabled()) {
      getLogger().debug("RootWebAPP = " + rootWebApp);
    }
    
    requestHandler = new RequestHandler(conf.getServletContext());

  }

  
  private void reload(HttpServletRequest request) throws
      ServletException {
    
  } 

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws
      IOException, ServletException {
    doGet(request, response);
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      IOException, ServletException {
	
	View view = new View();
	  
    reload(request);

    String requestURI = getRequestURI(request);
    if (getLogger().isDebugEnabled()) getLogger().debug("[doGet] processRequest");
    requestHandler.processRequest(requestURI,request,response, view);

    request.setAttribute(WebKeys.VIEW, view);
    
    if (!view.getContent().equals(WebKeys.NO_RESPONSE_CONTENT)){
    	if (view.getLayout().equals(WebKeys.NO_LAYOUT)) {
    		request.getRequestDispatcher("/WEB-INF/page/"+view.getContent()+".jsp").forward(request, response);
    	}
    	else {
    		request.getRequestDispatcher("/WEB-INF/page/"+view.getLayout()+".jsp").forward(request, response);
    	}
    }

  } 

  protected String getRequestURI(HttpServletRequest request) {
    String selectedURI = request.getRequestURI();
    String uriInclude = (String) request.getAttribute("javax.servlet.include.request_uri");
    if (uriInclude != null) {
      selectedURI = uriInclude;
    }
    
    String rootwebapp;
    String sc = (String)this.getServletContext().getAttribute(WebKeys.WEBAPP_ROOT);
    
    if (sc != null && sc.length() > 1) {
      int posizioneSlash = selectedURI.indexOf("/", 1);
      if (posizioneSlash > 0) {
        rootwebapp = selectedURI.substring(0, posizioneSlash);
        if (rootwebapp.equals(sc)) {
          selectedURI = selectedURI.substring(posizioneSlash, selectedURI.length());
        }
      }
    }
    
    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[doGet] URI = " + selectedURI);
    }
    return selectedURI;
  } 

  private Log getLogger() {
    return LogFactory.getLog(this.getClass().getName());
  }

}

