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
package org.parancoe.plugin.configuration.bo;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.parancoe.plugin.configuration.ConfigurationCollection;
import org.parancoe.plugin.configuration.PropertyCollection;
import org.parancoe.plugin.configuration.po.Category;
import org.parancoe.plugin.configuration.po.Property;

/**
 *
 * @author Lucio Benfante <lucio@benfante.com>
 */
@Path("configuration")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface ConfigurationService {

    /**
     * Return a property of a category.
     *
     * @param categoryName The category name.
     * @param propertyName The property name.
     * @return A property. null if the category or the property are not found.
     */
    @GET @Path("property/{category}/{property}")
    Property getProperty(@PathParam("category") String categoryName, @PathParam("property") String propertyName);


    /*
     * Return all properties of a category.
     *
     * @param categoryName The category name.
     * @return A property collection. null if the category is not found.
     */
    @GET @Path("category/{category}/properties")
    public PropertyCollection getProperties(@PathParam("category") String categoryName);
    
    /**
     * Load a property.
     *
     * @param id The property id.
     * @return A property. null if the property doesn't exist.
     */
    @GET @Path("property/{id}")
    Property getProperty(@PathParam("id") Long id);
    
    /**
     * Initialize a configuration, creating the elements (categories and properties) that doesn't
     * already exists.
     *
     * @param value The configuration collection to initialize.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    void initializeConfiguration(ConfigurationCollection configurationCollection);

    /**
     * Load all configuration categories.
     *
     * @return The configuration categories.
     */
    @GET @Path("categories")
    ConfigurationCollection getConfiguration();

    /**
     * Store a property.
     *
     * @param property The property to store.
     */
    @PUT
    void store(Property property);
    
}
