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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parancoe.controller.Controller;
import org.parancoe.utility.WebKeys;
import org.parancoe.utility.error.ErrorMessagesList;

public class RequestHandler {

  private ServletContext context = null;

  public RequestHandler() {
  }

  public RequestHandler(ServletContext context){
	  if (context!=null){
          this.context = context;
	  }
  }

  public ServletContext getContext(){
          return this.context;
  }
  
  
  public void processRequest(String requestURI, HttpServletRequest request,HttpServletResponse response, View view) {

    String action = null;
    String method = null;
    String service = null;
    StringBuffer site = new StringBuffer();
    String parameter = null;

    boolean validAction = true;

    StringTokenizer st = new StringTokenizer(requestURI, "/");
    while (st.hasMoreTokens() && validAction) {
      parameter = st.nextToken();
      if (service == null) {
        service = parameter;
        if (!service.equals(WebKeys.SERVICE)) {
          validAction = false;
        }
      }
      else if (action == null) {
        action = parameter;
      }
      else if (method == null) {
    	  method = parameter;
      }
          else {
                  if (site.length()>0) {
                          site.append(".");
                  }
                  site.append(action);
                  action = method;
                  method = parameter;
          }
    }
    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[processRequest] sito = " + site + ", action = " + action + ", method = " + method);
    }
    if (validAction == true) {
      executeController(site.toString(), action, method, request, response, view);
    }
  }

  private void executeMethod(Controller controller, String method, HttpServletRequest request,HttpServletResponse response)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Class c = controller.getClass();

    ErrorMessagesList errors = new ErrorMessagesList();
    Object[] parameters = {request,response, errors};
    Class[] parameterClasses = { HttpServletRequest.class,HttpServletResponse.class, ErrorMessagesList.class};

    try {
      Method controllerMethod = c.getMethod(method, parameterClasses);
      controllerMethod.invoke(controller, parameters);
    } catch (NoSuchMethodException e) {
      throw e;
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (IllegalAccessException e) {
      throw e;
    } catch (InvocationTargetException e) {
      throw e;
    }
    addErrorsToRequest(request, errors);

  }

 private void executeController(String mySite, String myAction, String myMethod, HttpServletRequest request,HttpServletResponse response, View view) {
   String nextLayout = WebKeys.NO_LAYOUT; 
   String nextContent = WebKeys.ERROR_CONTENT; 

   //TODO to change after adding of a settings system
   String basePackage= "org.parancoe";
   if ( (basePackage==null) || (basePackage.length()==0) ){
     basePackage = WebKeys.BASE_PACKAGE;
   }
   if (myAction!=null){
     StringBuffer classController = new StringBuffer();
     classController.append(basePackage);
     classController.append(".");
     if (mySite != null && mySite.length() > 0) {
       classController.append(mySite);
       classController.append(".");
     }
     classController.append(WebKeys.CONTROLLER_PACKAGE);
     classController.append(".");
     classController.append(myAction.substring(0, 1).toUpperCase());
     classController.append(myAction.substring(1));
     classController.append(WebKeys.CONTROLLER_POSTFIX);

     if (getLogger().isDebugEnabled()) {
       getLogger().debug("[executeController] provo ad istanziare il controller: " + classController.toString());
     }

     try {
       Controller controller = (Controller) (Class.forName(classController.toString()).newInstance());
       nextLayout = myAction;
       try {
         if (getLogger().isDebugEnabled()) getLogger().debug("[executeController] eseguo il metodo: " + myMethod);
         executeMethod(controller, myMethod, request, response);

         if (controller.getLayout() != null) nextLayout = controller.getLayout();
         nextContent = controller.getContent() != null ? controller.getContent() : myAction+"/"+myMethod;
       }
       catch (IllegalAccessException iae) {
         if (getLogger().isErrorEnabled()) getLogger().error("[executeController] il metodo non e' accessibile", iae);
         nextContent = WebKeys.ERROR_CONTENT;
         request.setAttribute("err_obj",iae);
         request.setAttribute("err_code","10 - Il metodo non e' accessibile.");
       }
       catch (InvocationTargetException ite) {
         if (getLogger().isErrorEnabled()) getLogger().error("[executeController] errore nell'invocazione del metodo", ite.getTargetException());
         nextContent = WebKeys.ERROR_CONTENT;
         request.setAttribute("err_obj",ite);
         request.setAttribute("err_code","11 - Errore nell'invocazione del metodo.");
       }
       catch (NoSuchMethodException nsme) {
         if (getLogger().isErrorEnabled()) getLogger().error("[executeController] il metodo non esiste", nsme);
         nextContent = WebKeys.ERROR_CONTENT;
         request.setAttribute("err_obj",nsme);
         request.setAttribute("err_code","12 - Il metodo non esiste.");
       }
       catch (NullPointerException npe){
         if (getLogger().isErrorEnabled()) getLogger().error("[executeController] non e' stato specificato nessun metodo da eseguire", npe);
         nextContent = WebKeys.ERROR_CONTENT;
         request.setAttribute("err_obj",npe);
         request.setAttribute("err_code","13 - Non e' stato specificato nessun metodo da eseguire.");
       }
     }
     catch (IllegalAccessException iae) {
       if (getLogger().isErrorEnabled()) getLogger().error("[executeController] il costruttore vuoto non e' accessibile", iae);
       request.setAttribute("err_obj",iae);
       request.setAttribute("err_code","20 - Il costruttore vuoto non e' accessibile.");
     }
     catch (InstantiationException ie) {
       if (getLogger().isErrorEnabled()) getLogger().error("[executeController] classe astratta, interfaccia, array di classi, tipo primitivo o void;\n oppure manca il costruttore nullo;\n oppure l'istanziazione e' fallita per qualche altro motivo.", ie);
       request.setAttribute("err_obj",ie);
       request.setAttribute("err_code","21 - Classe astratta, interfaccia, array di classi, tipo primitivo o void;\n oppure manca il costruttore nullo;\n oppure l'istanziazione e' fallita per qualche altro motivo.");
     }
     catch (ExceptionInInitializerError eie) {
       if (getLogger().isErrorEnabled()) getLogger().error("[executeController] errore nell'inizializzazione", eie);
       request.setAttribute("err_obj",eie);
       request.setAttribute("err_code","22 - Errore nell'inizializzazione.");
     }
     catch (SecurityException se) {
       if (getLogger().isErrorEnabled()) getLogger().error("[executeController] non si hanno i permessi per creare una nuova istanza", se);
       request.setAttribute("err_obj",se);
       request.setAttribute("err_code","23 - Non si hanno i permessi per creare una nuova istanza.");
     }
     catch (Exception e) {
       if (getLogger().isErrorEnabled()) getLogger().error("[executeController] errore generico durante l'inizializzazione" + classController.toString() + "\n", e);
       request.setAttribute("err_obj",e);
       request.setAttribute("err_code","24 - Errore generico durante l'inizializzazione.");
     }
   }

   view.setContent(nextContent);
   view.setLayout(nextLayout);

   if (getLogger().isDebugEnabled()) {
     getLogger().debug("[executeController] action: " + myAction + ", toDo: " + myMethod + ", screen: " + nextLayout + ", option: " + nextContent);
   }

 } // [m] executeController

 protected void addErrorsToRequest(HttpServletRequest request,
                                   ErrorMessagesList errors) {
   if (errors != null && !errors.isEmpty()) {
     ErrorMessagesList requestErrors = (ErrorMessagesList) request.
         getAttribute(WebKeys.ERROR_MESSAGES);
     if (requestErrors == null) {
       request.setAttribute(WebKeys.ERROR_MESSAGES, errors);
       if (getLogger().isDebugEnabled()) {
         getLogger().debug("[addErrorsToRequest] ci sono " + errors.size() + " errori");
       }
     }
     else {
       requestErrors.add(errors);
       if (getLogger().isDebugEnabled()) {
         getLogger().debug("[addErrorsToRequest] ci sono " + requestErrors.size() + " errori");
       }
     }
   }
 }

 public Log getLogger() {
   return LogFactory.getLog(this.getClass().getName());
 }
 
}
