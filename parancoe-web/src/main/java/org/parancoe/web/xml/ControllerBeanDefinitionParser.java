// Copyright 2006-2007 The Parancoe Team
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
package org.parancoe.web.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.mvc.Controller;
import org.w3c.dom.Element;

/**
 * A parser to load all the controllers defined in 'basePackage'.
 * 
 * @author <mailto:andrea.nasato@jugpadova.it/>
 * @version $Revision$
 */
public class ControllerBeanDefinitionParser implements BeanDefinitionParser {
    
    private static final Logger logger = Logger.getLogger(ControllerBeanDefinitionParser.class);

    private BeanDefinitionParserDelegate delegate;
    private ResourcePatternResolver resourceLoader;
    private BeanDefinitionRegistry registry;
    private ParserContext parserContext;

    /**
     * The package from which this parser loads controllers
     */
    public static final String BASE_PACKAGE_ATTRIBUTE = "basePackage";
    
    /**
     * The parent used to define controllers found
     */
    public static final String PARENT_ATTRIBUTE = "parent";    
    

    /**
     * Register controllers found in <code>basePackage</code> attribute giving them 
     * <code>parent</code> as parent.
     *
     * @param element 
     * @param parserContext 
     * @return 
     */
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String packageName = element.getAttribute(BASE_PACKAGE_ATTRIBUTE);
        String parentName = element.getAttribute(PARENT_ATTRIBUTE);

        this.parserContext = parserContext;
        this.delegate = parserContext.getDelegate();
        this.resourceLoader = (ResourcePatternResolver) parserContext
				.getReaderContext().getReader().getResourceLoader();
        this.registry = parserContext.getReaderContext().getRegistry();

        List<Class> controllerClasses = getAllControllerTypeClasses(packageName);
        if (controllerClasses != null) {
            for (Class ctrl : controllerClasses) {
                createBeanDefinition(ctrl, parentName);
            }
        }

        return null;
    }

    /**
     * Return all classes in the package subtree matching Controller type.
     *
     * @param packageName The base package
     * @return The list of all classes
     */
    protected List<Class> getAllControllerTypeClasses(String packageName) {
        List<Class> result = new ArrayList<Class>();
        try {
            String packagePart = packageName.replace('.', '/');
            String classPattern = "classpath*:/" + packagePart + "/**/*.class";
            Resource[] resources = this.resourceLoader.getResources(classPattern);
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                String fileName = resource.getURL().toString();
                String className = fileName.substring(fileName.indexOf(packagePart), fileName.length() - ".class".length()).replace('/', '.');
                Class<?> type = Class.forName(className);

                if (Controller.class.isAssignableFrom(type)) {
                    result.add(type);
                }
            }
        } catch (IOException e) {
            this.parserContext.getReaderContext().fatal(e.getMessage(), null, e);
            return null;
        } catch (ClassNotFoundException e) {
            this.parserContext.getReaderContext().fatal(e.getMessage(), null, e);
            return null;
        }
        return result;
    }

    private void createBeanDefinition(Class ctrl, String parent) {
        String id = ctrl.getName();
        BeanDefinitionBuilder rootBuilder = BeanDefinitionBuilder
                .childBeanDefinition(parent);
        rootBuilder.getBeanDefinition().setBeanClass(ctrl);
        if(logger.isDebugEnabled()){
            logger.debug("registering bean definition: " + rootBuilder.getBeanDefinition().toString());
        }
        //logger.info("registering bean definition: " + rootBuilder.getBeanDefinition().toString());
        registry.registerBeanDefinition(id, rootBuilder.getBeanDefinition());
        
    }
}