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
package org.parancoe.bean;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BeanImpl implements Bean {
	public static final String RULE_NODE = "rule";

	public static final String RULE_TYPE = "kind";

	public static final String RULE_EMPTY = "empty";

	public static final String RULE_TRIM = "trim";

	public static final String RULE_ERROR = "error";

	public static final String RULE_ERROR_ARGS = "errorArgs";

	public static final String RULE_ARGS = "args";

	public static final String RULE_ARG = "arg";

	public static final String ARG_NAME = "name";

	public static final String ARG_TYPE = "type";

	public static final String ARG_VALUE = "value";

	private Map beanRules = null;

	private String beanKey = null;

	private String beanScope = null;

	public Map getBeanRules() {
		return this.beanRules;
	}

	public void setBeanRules(Map beanRules) {
		this.beanRules = beanRules;
	}

	public String getBeanKey() {
		return this.beanKey;
	}

	public void setBeanKey(String beanKey) {
		this.beanKey = beanKey;
	}

	public String getBeanScope() {
		return this.beanScope;
	}

	public void setBeanScope(String beanScope) {
		this.beanScope = beanScope;
	}

	public Log getLogger() {
		return LogFactory.getLog(this.getClass().getName());
	}
}