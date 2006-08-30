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


import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parancoe.bean.Bean;
import org.parancoe.utility.WebKeys;
import org.parancoe.utility.error.ErrorMessage;
import org.parancoe.utility.error.ErrorMessagesList;
import org.parancoe.utility.error.ZoneErrorMessageList;

public class BeanHandler {

  private String packageName = null;

  public BeanHandler(String packageName) {
    this.packageName = packageName;
  }

  private Bean getInstance(String beanId, String beanScope, String beanKey,
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

    Bean bean = null;

    if ((beanKey == null) || (beanKey.equals(""))) {
      if (getLogger().isDebugEnabled()) {
        getLogger().error("[getInstance] null or empty key");
      }
      return bean;
    }

    if (beanScope.equals(WebKeys.SESSION)) {

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] searching bean [" + beanKey + "] in session");
      }

      Object oBean = request.getSession().getAttribute(beanKey);

      if (oBean != null) {

        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] bean [" + beanKey + "] already present in session, using it");
        }
        bean = (Bean) oBean;

        if (reset) {
          if (getLogger().isDebugEnabled()) {
            getLogger().debug("[getInstance] resetting bean [" + beanKey + "]...");
          }
          request.getSession().removeAttribute(beanKey);
          bean = null;
          try {
                  bean = (Bean) Class.forName(beanClassName).newInstance();
          }
          catch (InstantiationException ie) {
            if (getLogger().isErrorEnabled()) {
              getLogger().error("[getInstance] can't instantiate bean [" + beanKey + "]: " + ie);
            }
          }
          catch (IllegalAccessException iae) {
            if (getLogger().isErrorEnabled()) {
              getLogger().error("[getInstance] can't get attributes for bean [" + beanKey + "]: " + iae);
            }
          }
          catch (ClassNotFoundException cnfe) {
            if (getLogger().isErrorEnabled()) {
              getLogger().error("[getInstance] can't find class for bean [" + beanKey + "]: " + cnfe);
            }
          }
          bean.setBeanKey(beanKey);
          bean.setBeanScope(beanScope);
        }

        if (validate) {
          if (getLogger().isDebugEnabled()) {
            getLogger().debug("[getInstance] validating input data for bean [" + beanKey + "]...");
          }
          ZoneErrorMessageList errorList = bean.validate("HttpRequestResolver", request, selector);
          if (errorList == null) {
            errorList = new ZoneErrorMessageList();
            errorList.add("DataSiftError", new ErrorMessage("Warning: DataSift Error. Can't do validation!", false));
            errors.add(errorList);
            return null;
          }
          if (!errorList.isEmpty()) {
            errors.add(errorList);
            return null;
          }
        }

        if (getLogger().isDebugEnabled()) {
            getLogger().debug("[getInstance] loading new data on bean [" + beanKey + "]...");
        }
        load(bean, request);

        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] saving bean [" + beanKey + "] in session");
        }
        request.getSession().setAttribute(beanKey, bean);

        return bean;
      }

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] can't find bean [" + beanKey + "] in session scope");
      }
    }

    try {
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] creating bean of type " + beanClassName);
      }

      bean = (Bean) Class.forName(beanClassName).newInstance();
      bean.setBeanKey(beanKey);
      bean.setBeanScope(beanScope);
      bean.setBeanRules(validationrules);

      if (validate) {
        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] validating request data for bean [" + beanKey + "]...");
        }
        ZoneErrorMessageList errorList = bean.validate("HttpRequestResolver", request, selector);
        if (errorList == null) {
          errorList = new ZoneErrorMessageList();
          errorList.add("DataSiftError",
              new ErrorMessage("Warning: DataSift Error. Can't do validation!", false));
          errors.add(errorList);
          return null;
        }
        if (!errorList.isEmpty()) {
          errors.add(errorList);
          return null;
        }
      }

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] setting data on bean [" + beanKey + "]");
      }
      load(bean, request);

      if (getLogger().isDebugEnabled()) {
        getLogger().debug("[getInstance] instantiating bean [" + beanKey + "]");
      }
      if (bean.getBeanScope().equals(WebKeys.SESSION)) {
        if (getLogger().isDebugEnabled()) {
          getLogger().debug("[getInstance] saving bean [" + beanKey + "] in session");
        }
        request.getSession().setAttribute(beanKey, bean);
      }
    }
    catch (InstantiationException ie) {
      if (getLogger().isErrorEnabled()) {
        getLogger().error("[getInstance] can't instantiate bean bean [" + beanKey + "]: " + ie);
      }
    }
    catch (IllegalAccessException iae) {
      if (getLogger().isErrorEnabled()) {
        getLogger().error("[getInstance] can't get attributes for bean [" + beanKey + "]: " +
                          iae);
      }
    }
    catch (ClassNotFoundException cnfe) {
      if (getLogger().isErrorEnabled()) {
        getLogger().error("[getInstance] can't find class for bean [" + beanKey + "]: " + cnfe);
      }
    }

    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[getInstance] end");
    }

    return bean;
  }


  public Bean getRequestInstance(String id, String key, HttpServletRequest request) {
    return getInstance(id, WebKeys.REQUEST, key, request, false, false, null, null, null);
  }

  public Bean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ZoneErrorMessageList errors) {
    return getInstance(id, WebKeys.REQUEST, key, request, false, true, errors, null, null);
  }

  public Bean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ErrorMessagesList errors) {
    ZoneErrorMessageList validationErrors = new ZoneErrorMessageList();
    Bean bean = getInstance(id, WebKeys.REQUEST, key, request, false, true, validationErrors, null, null);
    if (!validationErrors.isEmpty()) {
      errors.addZoneErrorMessageList(validationErrors);
    }
    return bean;
  }


  public Bean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ZoneErrorMessageList errors, String selector) {
    return getInstance(id, WebKeys.REQUEST, key, request, false, true, errors, selector, null);
  }

  public Bean getRequestInstanceAndValidate(String id, String key, HttpServletRequest request, ErrorMessagesList errors, String selector) {
    ZoneErrorMessageList validationErrors = new ZoneErrorMessageList();
    Bean bean = getInstance(id, WebKeys.REQUEST, key, request, false, true, validationErrors, selector, null);
    if (!validationErrors.isEmpty()) {
      errors.addZoneErrorMessageList(validationErrors);
    }
    return bean;
  }

    private void load(Bean bean, HttpServletRequest request)  {

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
      if (getLogger().isErrorEnabled()) getLogger().error("[load] Error accessing property of bean - " + ite);
    }catch (IllegalAccessException iae) {
      if (getLogger().isErrorEnabled()) getLogger().error("[load] Access denied for property of bean - " + iae);
    }
    if (getLogger().isDebugEnabled()) {
      getLogger().debug("[load] end");
    }

  }

  private Log getLogger() {
    return LogFactory.getLog(this.getClass().getName());
  }

}
