/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Web.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parancoe.web.plugin;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.context.ContextLoaderListener;

/**
 *
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class ApplicationContextPlugin {

    private String name = "no name given";
    private List<ContextLoaderListener> contextLoaderListeners =
            new ArrayList<ContextLoaderListener>();
    private List<String> fixtureClassNames = new ArrayList<String>();
    private String jspPrefix = "/WEB-INF/jsp";
    private List<String> jspResources = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContextLoaderListener> getContextLoaderListeners() {
        return contextLoaderListeners;
    }

    public void setContextLoaderListeners(List<ContextLoaderListener> contextLoaderListeners) {
        this.contextLoaderListeners = contextLoaderListeners;
    }

    public List<String> getFixtureClassNames() {
        return fixtureClassNames;
    }

    public void setFixtureClassNames(List<String> fixtureClassNames) {
        this.fixtureClassNames = fixtureClassNames;
    }

    public List<Class> getFixtureClasses() throws Exception {
        List<Class> classes = new ArrayList<Class>(fixtureClassNames.size());
        for (String className : fixtureClassNames) {
            classes.add(Class.forName(className));
        }
        return classes;
    }

    public String getJspPrefix() {
        return jspPrefix;
    }

    public void setJspPrefix(String jspPrefix) {
        this.jspPrefix = jspPrefix;
    }
    
    public List<String> getJspResources() {
        return jspResources;
    }

    public void setJspResources(List<String> jspResources) {
        this.jspResources = jspResources;
    }
}
