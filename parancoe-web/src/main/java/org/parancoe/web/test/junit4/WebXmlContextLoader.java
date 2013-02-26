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
package org.parancoe.web.test.junit4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * Concrete implementation of {@link AbstractGenericContextLoader} which reads
 * bean definitions from XML resources. NB creates a {@link GenericWebApplicationContext}
 * suitable fot testing purposes.
 *
 * @see {@link GenericXmlContextLoader}.
 * @author michele franzin <michele at franzin.net>
 */
public class WebXmlContextLoader extends AbstractContextLoader {

    private static final Logger logger = LoggerFactory.getLogger(WebXmlContextLoader.class);

    /**
     * Returns &quot;<code>-test.xml</code>&quot;.
     */
    @Override
    public String getResourceSuffix() {
        return "-test.xml";
    }

    /**
     * Loads a Spring ApplicationContext from the supplied
     * <code>locations</code>. and creates a standard {@link GenericWebApplicationContext} instance.
     *
     * @return a new application context
     * @see org.springframework.test.context.ContextLoader#loadContext
     * @see GenericWebApplicationContext
     * @see #createBeanDefinitionReader(GenericApplicationContext)
     * @see BeanDefinitionReader
     */
    @Override
    public final ConfigurableApplicationContext loadContext(String... locations) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading ApplicationContext for locations [{}].",
                    StringUtils.arrayToCommaDelimitedString(locations));
        }
        GenericApplicationContext context = new GenericWebApplicationContext();
        createBeanDefinitionReader(context).loadBeanDefinitions(locations);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
        context.refresh();
        context.registerShutdownHook();
        return context;
    }

    /**
     * Create a new {@link XmlBeanDefinitionReader}.
     *
     * @return a new XmlBeanDefinitionReader.
     * @see XmlBeanDefinitionReader
     */
    private BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context) {
        return new XmlBeanDefinitionReader(context);
    }
}
