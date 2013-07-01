/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Plugin Configuration.
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
package org.parancoe.plugin.configuration;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.parancoe.plugin.configuration.po.Property;

/**
 * A class for collecting configuration properties. Usually it's used for passing sets of properties
 * in web services.
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
@XmlRootElement
public class PropertyCollection {

    private List<Property> properties;

    public PropertyCollection() {
    }
    
    public PropertyCollection(List<Property> properties) {
        this.properties = properties;
    }
    
    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
