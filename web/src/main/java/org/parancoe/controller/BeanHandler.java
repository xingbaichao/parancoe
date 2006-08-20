package org.parancoe.controller;


import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parancoe.bean.IBean;
import org.parancoe.utility.WebKeys;
import org.parancoe.utility.error.ErrorMessage;
import org.parancoe.utility.error.ErrorMessagesList;
import org.parancoe.utility.error.ZoneErrorMessageList;

public class BeanHandler {

  private String packageName = null;
  
  public BeanHandler(String packageName) {
    this.packageName = packageName;
  }

  private IBean getInstance(String beanId, String beanScope, String beanKey,
                        HttpServletRequest request, boolean reset, boolean validate, ZoneErrorMessageList errors, String selector, Map validationrules) {

    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[getInstance] start");
    }

    StringBuffer sb = new StringBuffer();
    sb.append(packageName);
    sb.append(WebKeys.BEAN_PACKAGE);
    sb.append(".");
    sb.append(beanId);
    sb.append(WebKeys.BEAN_POSTFIX);
    String beanClassName = sb.toString();

    IBean bean = null;

    if ((beanKey == null) || (beanKey.equals(""))) {
      if (getLogger().isDebugEnabled()) {
        getLogger().error("[getInstance] chiave nulla o vuota");
      }
      return bean;
    }

    if (beanScope.equals(WebKeys.SESSION)) {

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] lo scope del bean � session, cerco nella sessione il bean [" + beanKey + "]");
      }

      Object oBean = request.getSession().getAttribute(beanKey);

      if (oBean != null) {

        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] il bean [" + beanKey + "] � gi� presente in sessione, lo riutilizzo");
        }
        bean = (IBean) oBean;

        if (reset) {
          if (getLogger().isDebugEnabled()) {
            getLogger().debug("[getInstance] resetto il bean [" + beanKey + "]...");
          }
          request.getSession().removeAttribute(beanKey);
          bean = null;
          try {
                  bean = (IBean) Class.forName(beanClassName).newInstance();
          }
          catch (InstantiationException ie) {
            if (getLogger().isErrorEnabled()) {
              getLogger().error("[getInstance] impossibile istanziare il bean [" + beanKey + "]: " + ie);
            }
          }
          catch (IllegalAccessException iae) {
            if (getLogger().isErrorEnabled()) {
              getLogger().error("[getInstance] impossibile accedere agli attributi del bean [" + beanKey + "]: " + iae);
            }
          }
          catch (ClassNotFoundException cnfe) {
            if (getLogger().isErrorEnabled()) {
              getLogger().error("[getInstance] impossibile trovare la classe relativa al bean [" + beanKey + "]: " + cnfe);
            }
          }
          bean.setBeanKey(beanKey);
          bean.setBeanScope(beanScope);
        }

        if (validate) {
          if (getLogger().isDebugEnabled()) {
            getLogger().debug("[getInstance] valido i dati in request del bean [" + beanKey + "]...");
          }
          ZoneErrorMessageList errorList = bean.validate("HttpRequestResolver", request, selector);
          if (errorList == null) {
            errorList = new ZoneErrorMessageList();
            errorList.add("DataSiftError", new ErrorMessage("Attenzione: Errore DataSift. Impossibile eseguire la validazione!", false));
            errors.add(errorList);
            return null;
          }
          if (!errorList.isEmpty()) {
            errors.add(errorList);
            return null;
          }
        }

        if (getLogger().isDebugEnabled()) {
            getLogger().debug("[getInstance] carico i nuovi dati nel bean [" + beanKey + "]...");
        }
        load(bean, request);

        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] salvo il bean [" + beanKey + "] in sessione");
        }
        request.getSession().setAttribute(beanKey, bean);

        return bean;
      }

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] il bean [" + beanKey + "] non � presente in sessione");
      }
    }

    try {
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] creo un bean di tipo " + beanClassName);
      }

      bean = (IBean) Class.forName(beanClassName).newInstance();
      bean.setBeanKey(beanKey);
      bean.setBeanScope(beanScope);
      bean.setBeanRules(validationrules);

      if (validate) {
        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] valido i dati in request del bean [" + beanKey + "]...");
        }
        ZoneErrorMessageList errorList = bean.validate("HttpRequestResolver", request, selector);
        if (errorList == null) {
          errorList = new ZoneErrorMessageList();
          errorList.add("DataSiftError",
              new ErrorMessage("Attenzione: Errore DataSift. Impossibile eseguire la validazione!", false));
          errors.add(errorList);
          return null;
        }
        if (!errorList.isEmpty()) {
          errors.add(errorList);
          return null;
        }
      }

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] popolo il bean [" + beanKey + "]");
      }
      load(bean, request);

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] istanziato correttamente il bean [" + beanKey + "]");
      }
      if (bean.getBeanScope().equals(WebKeys.SESSION)) {
        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] salvo il bean [" + beanKey + "] in sessione");
        }
        request.getSession().setAttribute(beanKey, bean);
      }
    }
    catch (InstantiationException ie) {
      if (getLogger().isErrorEnabled()) {
        getLogger().error("[getInstance] impossibile istanziare il bean [" + beanKey + "]: " + ie);
      }
    }
    catch (IllegalAccessException iae) {
      if (getLogger().isErrorEnabled()) {
        getLogger().error("[getInstance] impossibile accedere agli attributi del bean [" + beanKey + "]: " +
                          iae);
      }
    }
    catch (ClassNotFoundException cnfe) {
      if (getLogger().isErrorEnabled()) {
        getLogger().error("[getInstance] impossibile trovare la classe relativa al bean [" + beanKey + "]: " + cnfe);
      }
    }

    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[getInstance] end");
    }

    return bean;
  }

  
  public IBean getRequestInstance(String id, String key, HttpServletRequest request) {
    return getInstance(id, WebKeys.REQUEST, key, request, false, false, null, null, null);
  }
  
  public IBean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ZoneErrorMessageList errors) {
    return getInstance(id, WebKeys.REQUEST, key, request, false, true, errors, null, null);
  }

  public IBean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ErrorMessagesList errors) {
    ZoneErrorMessageList validationErrors = new ZoneErrorMessageList();
    IBean bean = getInstance(id, WebKeys.REQUEST, key, request, false, true, validationErrors, null, null);
    if (!validationErrors.isEmpty()) {
      errors.addZoneErrorMessageList(validationErrors);
    }
    return bean;
  }

  
  public IBean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ZoneErrorMessageList errors, String selector) {
    return getInstance(id, WebKeys.REQUEST, key, request, false, true, errors, selector, null);
  }

  public IBean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ErrorMessagesList errors, String selector) {
    ZoneErrorMessageList validationErrors = new ZoneErrorMessageList();
    IBean bean = getInstance(id, WebKeys.REQUEST, key, request, false, true, validationErrors, selector, null);
    if (!validationErrors.isEmpty()) {
      errors.addZoneErrorMessageList(validationErrors);
    }
    return bean;
  }

    private void load(IBean bean, HttpServletRequest request)  {

    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[load] start");
    }
    HashMap properties = new HashMap();
    Enumeration names = null;
    Map multipartParameters = null;

    String contentType = request.getContentType();
    String method = request.getMethod();
    boolean isMultipart = false;
    if ( (contentType != null) && (contentType.startsWith("multipart/form-data"))
    	&& (method.equalsIgnoreCase("post")) ) {
        //TODO to be add a multipart handler 
    }
    if (!isMultipart) {
        names = request.getParameterNames();
    }
    while (names.hasMoreElements()) {
        String name = (String) names.nextElement();
        Object parameterValue = null;

        if (isMultipart) {
            parameterValue = multipartParameters.get(name);
        } else {
            parameterValue = request.getParameterValues(name);
        }
        properties.put(name, parameterValue);
    }

    try {
        BeanUtils.populate(bean, properties);
    }catch (InvocationTargetException ite) {
      if (getLogger().isErrorEnabled()) getLogger().error("[load] Errore nel tentativo di accesso alla propriet� del bean - " + ite);
    }catch (IllegalAccessException iae) {
      if (getLogger().isErrorEnabled()) getLogger().error("[load] Accesso negato per la propriet� del bean - " + iae);
    }
    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[load] end");
    }

  }
  
  private Log getLogger() {
    return LogFactory.getLog(this.getClass().getName());
  }

}
