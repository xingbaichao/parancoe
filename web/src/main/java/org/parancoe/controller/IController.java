package org.parancoe.controller;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.parancoe.session.ISessionBean;
import org.parancoe.utility.IProfileHandler;


public interface IController {

  public String getLayout();

  public void setLayout(String layout);
  
  public String getContent();
  
  public void setContent(String content);

  public BeanHandler getBeanHandler();

  public ISessionBean getSession(HttpServletRequest request);

  public void init(ServletContextEvent sce) throws ServletException;

  public IProfileHandler getProfileHandler();

}