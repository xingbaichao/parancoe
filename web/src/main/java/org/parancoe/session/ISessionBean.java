package org.parancoe.session;

import java.io.Serializable;

public interface ISessionBean extends Serializable {

  public String getSessionBeanName();

  public void setSessionBeanName(String nome);

  public String getBeanScope();

  public void setBeanScope(String scope);

}
