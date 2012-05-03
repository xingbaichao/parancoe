/**
 * Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of parancoe-plugin-tiles Parancoe Plugin.
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
package org.parancoe.plugin.tiles;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.TilesException;
import org.apache.tiles.access.TilesAccess;

import org.apache.tiles.mgmt.MutableTilesContainer;
import org.springframework.web.servlet.view.tiles2.TilesView;

/**
 * An extension of {@link org.springframework.web.servlet.view.tiles2.TilesView}
 * that implements a convention for tiles views that aren't defined in the
 * configuration files.
 *
 * You can use in the following way in your application context configuration:
 *
 * <pre>
 * <bean id="viewResolver"
 *     class="org.springframework.web.servlet.view.UrlBasedViewResolver">
 *     <property name="viewClass" value="net.scarabocio.util.CheapTilesView"/>
 *     <property name="attributesMap">
 *         <map>
 *             <entry key="net.scarabocio.util.CheapTilesView.DEFAULT_TEMPLATE" value="template.main"/>
 *             <entry key="net.scarabocio.util.CheapTilesView.DEFAULT_ATTRIBUTES" value="main"/>
 *             <entry key="net.scarabocio.util.CheapTilesView.DEFAULT_PREFIX" value="/WEB-INF/jsp/"/>
 *             <entry key="net.scarabocio.util.CheapTilesView.DEFAULT_SUFFIX" value=".jsp"/>
 *         </map>
 *     </property>
 * </bean>
 * </pre>
 *
 * or, if you are using the previous default values, simply:
 *
 * <pre>
 * <bean id="viewResolver"
 *     class="org.springframework.web.servlet.view.UrlBasedViewResolver">
 *     <property name="viewClass" value="net.scarabocio.util.CheapTilesView"/>
 * </bean>
 * </pre>
 *
 * This view maps URLs on dynamically created tiles definitions.
 *
 * For example, the URL "people/list" is mapped on the following tile definition:
 *
 * <pre>
 *  <definition name="people/list" extends="template.main">
 *      <put-attribute name="main" value="/WEB-INF/jsp/people/list.jsp"/>
 *  </definition>
 * </pre>
 *
 * You can use multiple default attributes in you definitions. For example, with
 * the following configuration:
 *
 * <pre>
 * <bean id="viewResolver"
 *     class="org.springframework.web.servlet.view.UrlBasedViewResolver">
 *     <property name="viewClass" value="net.scarabocio.util.CheapTilesView"/>
 *     <property name="attributesMap">
 *         <map>
 *             <entry key="net.scarabocio.util.CheapTilesView.DEFAULT_ATTRIBUTES" value="main,sidebar"/>
 *         </map>
 *     </property>
 * </bean>
 * </pre>
 *
 * the URL "people/list" is mapped on the following configuration:
 *
 * <pre>
 *  <definition name="people/list" extends="template.main">
 *      <put-attribute name="main" value="/WEB-INF/jsp/people/list_main.jsp"/>
 *      <put-attribute name="main" value="/WEB-INF/jsp/people/list_sidebar.jsp"/>
 *  </definition>
 * </pre>
 *
 * @author Lucio Benfante
 * @see TilesView
 */
public class CheapTilesView extends TilesView {

    public static final String DEFAULT_DEFAULT_TEMPLATE = "template.main";
    public static final String KEY_DEFAULT_TEMPLATE =
            CheapTilesView.class.getName() + ".DEFAULT_TEMPLATE";
    public static final String DEFAULT_DEFAULT_ATTRIBUTES = "main";
    public static final String KEY_DEFAULT_ATTRIBUTES =
            CheapTilesView.class.getName() + ".DEFAULT_ATTRIBUTES";
    public static final String DEFAULT_DEFAULT_PREFIX = "/WEB-INF/jsp/";
    public static final String KEY_DEFAULT_PREFIX =
            CheapTilesView.class.getName() + ".DEFAULT_PREFIX";
    public static final String DEFAULT_DEFAULT_SUFFIX = ".jsp";
    public static final String KEY_DEFAULT_SUFFIX =
            CheapTilesView.class.getName() + ".DEFAULT_SUFFIX";

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        return true;
    }


    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        try {
            super.renderMergedOutputModel(model, request, response);
        } catch (TilesException te) {
            ServletContext servletContext = getServletContext();
            MutableTilesContainer container =
                    (MutableTilesContainer) TilesAccess.getContainer(
                    servletContext);
            Definition definition = new Definition();
            definition.setName(getUrl());
            definition.setExtends((String) getAttribute(KEY_DEFAULT_TEMPLATE,
                    DEFAULT_DEFAULT_TEMPLATE));
            String attributeList = (String) getAttribute(KEY_DEFAULT_ATTRIBUTES,
                    DEFAULT_DEFAULT_ATTRIBUTES);
            String[] attributes = attributeList.split(",");
            if (attributes.length == 1) {
                definition.putAttribute(attributes[0],
                        new Attribute(
                        (String) getAttribute(KEY_DEFAULT_PREFIX,
                        DEFAULT_DEFAULT_PREFIX) +
                        getUrl() + (String) getAttribute(KEY_DEFAULT_SUFFIX,
                        DEFAULT_DEFAULT_SUFFIX)));
            } else {
                for (String attribute : attributes) {
                    definition.putAttribute(attribute, new Attribute(
                            (String) getAttribute(KEY_DEFAULT_PREFIX,
                            DEFAULT_DEFAULT_PREFIX) +
                            getUrl() + "_" + attribute +
                            (String) getAttribute(KEY_DEFAULT_SUFFIX,
                            DEFAULT_DEFAULT_SUFFIX)));
                }
            }
            container.register(definition, request, response);
            container.render(getUrl(), new Object[]{request, response});
        }
    }

    private Object getAttribute(Object key, Object defaultValue) {
        Object result = this.getStaticAttributes().get(key);
        return result != null ? result : defaultValue;
    }
}
