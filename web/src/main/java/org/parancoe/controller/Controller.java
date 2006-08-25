package org.parancoe.controller;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.parancoe.session.SessionBean;
import org.parancoe.utility.ProfileHandler;


public interface Controller {

  public String getLayout();

  public void setLayout(String layout);
  
  public String getContent();
  
  public void setContent(String content);

  public BeanHandler getBeanHandler();

  public SessionBean getSession(HttpServletRequest request);

  public void init(ServletContextEvent sce) throws ServletException;

  public ProfileHandler getProfileHandler();

}