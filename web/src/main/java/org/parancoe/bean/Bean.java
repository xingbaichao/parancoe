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

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

import org.parancoe.utility.error.ZoneErrorMessageList;

import java.util.Map;

/**
 * TODO: write the javadoc
 */
public interface Bean extends Serializable {
    
    /**
     * TODO: write the javadoc
     */
    public Map getBeanRules();
    
    /**
     * TODO: write the javadoc
     */
    public void setBeanRules(Map getBeanRules);
    
    /**
     * Returns the key used for uniquely identifing the bean
     *
     * @return The bean key (unique identifier)
     */
    public String getBeanKey();
    
    /**
     * Set the bean key (unique identifier)
     *
     * @param key The bean unique identifier
     */
    public void setBeanKey(String key);
    
    /**
     * Returns the bean scope (<code>session</code> or <code>request</code>)
     *
     * @return lo scope del bean
     */
    public String getBeanScope();
    
    /**
     * Set the bean scope (<code>session</code> or <code>code request</code>)
     *
     * @param scope The bean scope
     */
    public void setBeanScope(String scope);
    
    /**
     * Validates the attributes of the bean in the request
     *
     * @param resolver The name of the resolver to use for the validation.
     * @param request the request containing the bean data
     * @param selector validation selector, for having different validation on the same bean
     * @return The list of validation errors
     */
    public ZoneErrorMessageList validate(String resolver, HttpServletRequest request, String selector);
    
}
