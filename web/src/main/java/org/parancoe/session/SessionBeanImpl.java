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
package org.parancoe.session;

public class SessionBeanImpl implements SessionBean {

	private static final long serialVersionUID = 2390884971235946728L;

	private String sessionBeanName = null;

	public String scope = null;

	public SessionBeanImpl() {
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
