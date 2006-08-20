package org.parancoe.session;

public class SessionBean implements ISessionBean {

	private static final long serialVersionUID = 2390884971235946728L;

	private String sessionBeanName = null;

	public String scope = null;

	public SessionBean() {
	}

	public String getSessionBeanName() {
		return this.sessionBeanName;
	}

	public void setSessionBeanName(String nome) {
		this.sessionBeanName = nome;
	}

	public String getBeanScope() {
		return scope;
	}

	public void setBeanScope(String scope) {
		this.scope = scope;
	}

}
