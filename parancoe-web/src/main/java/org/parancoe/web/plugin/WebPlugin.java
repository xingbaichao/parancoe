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

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Define the basic properties of a plugin Ogni plugin nel suo parancoe-plugin.xml configura
 * un'istanza di questa classe impostando le properties adatte.
 *
 * @author paolo.dona@seesaw.it
 * @author Jacopo Murador <jacopo.murador at seesaw.it>
 */
public class WebPlugin {

    private String name = "no name given";
    private List<HandlerInterceptor> interceptors = new ArrayList<HandlerInterceptor>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
