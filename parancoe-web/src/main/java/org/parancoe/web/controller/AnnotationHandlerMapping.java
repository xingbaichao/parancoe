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
package org.parancoe.web.controller;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.parancoe.util.Utils;
import org.parancoe.web.controller.annotation.DefaultUrlMapping;
import org.parancoe.web.controller.annotation.MultiUrlMapping;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;

/**
 * An HandlerMapping implementation that reads url mapping from annotations.
 * Annotation supported are:
 * <ol>
 *  <li>{@link org.parancoe.web.controller.annotation.DefaultUrlMapping}</li>
 *  <li>{@link org.parancoe.web.controller.annotation.UrlMapping}</li>
 *  <li>{@link org.parancoe.web.controller.annotation.MultiUrlMapping}</li>
 * </ol>
 * 
 * This handlerMapping inherits directly from BeanNameUrlHandlerMapping, in this
 * way it's still possible to map urls to controller using the 'name' attribute.
 * 
 * @author Andrea Nasato <mailto:andrea.nasato@jugpadova.it/> 
 * @version $Revision$
 */
public class AnnotationHandlerMapping extends AbstractDetectingUrlHandlerMapping {
    
    private String defaultExtension = "html";

    private static final Logger logger = Logger.getLogger(AnnotationHandlerMapping.class);

    
    public void setDefaultExtension(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }

    
    public String getDefaultExtension() {
        return defaultExtension;
    }

    
    private String[] getDefaultUrlFromControllerName(Class<?> handlerType){
        String[] values = Utils.uncamelize(handlerType.getSimpleName());
        StringBuffer sb = new StringBuffer();
        
        sb.append("/" + values[0]);
        //the last element of the array is the 'controller' string
        //and we don't want to put it in the mapping
        for(int i=1; i<values.length - 1; i++){
            sb.append("_");
            sb.append(values[i]);                    
        }
        sb.append("/*.").append(defaultExtension);
        
        String[] url = {sb.toString()};
        
        return url;
        
    }
    
    
    private <A extends Annotation> A getMapping(ApplicationContext context, String beanName, Class<?> handlerType, Class<A> annotationType) {
        A mapping = AnnotationUtils.findAnnotation(handlerType, annotationType);
        
        
        if (mapping == null && context instanceof ConfigurableApplicationContext &&
                        context.containsBeanDefinition(beanName)) {
                ConfigurableApplicationContext cac = (ConfigurableApplicationContext) context;
                BeanDefinition bd = cac.getBeanFactory().getMergedBeanDefinition(beanName);
                if (bd instanceof AbstractBeanDefinition) {
                        AbstractBeanDefinition abd = (AbstractBeanDefinition) bd;
                        if (abd.hasBeanClass()) {
                                Class<?> beanClass = abd.getBeanClass();
                                mapping = AnnotationUtils.findAnnotation(beanClass, annotationType);
                        }
                }
        }
        
        return mapping;
    }

    
   
    
    @Override
    protected String[] determineUrlsForHandler(String beanName) {
        ApplicationContext context = getApplicationContext();
	Class<?> handlerType = context.getType(beanName);
	
        UrlMapping urlMapping = getMapping(context, beanName, handlerType, UrlMapping.class);
        if (urlMapping != null) {
            if (urlMapping.value().length > 0) {
                logger.debug("URL: " + urlMapping.value()[0] + " | BEAN: " + beanName);
                return urlMapping.value();
            }
            else {
                logger.debug("URL: " + getDefaultUrlFromControllerName(handlerType)[0] + " | BEAN: " + beanName);
                return getDefaultUrlFromControllerName(handlerType);
            }
        }
       
        MultiUrlMapping multiUrlMapping = getMapping(context, beanName, handlerType, MultiUrlMapping.class);
        if (multiUrlMapping != null) {
            if (multiUrlMapping.value().length > 0) {
                logger.debug("URL: " + multiUrlMapping.value()[0] + " | BEAN: " + beanName);
                return multiUrlMapping.value();
            }
            else {
                logger.debug("URL: " + getDefaultUrlFromControllerName(handlerType)[0] + " | BEAN: " + beanName);
                return getDefaultUrlFromControllerName(handlerType);
            } 
        }
        
        DefaultUrlMapping defaultUrlMapping = getMapping(context, beanName, handlerType, DefaultUrlMapping.class);
        if (defaultUrlMapping != null) {
            logger.debug("URL: " + getDefaultUrlFromControllerName(handlerType)[0] + " | BEAN: " + beanName);
            return getDefaultUrlFromControllerName(handlerType); 
        }
        
        RequestMapping requestMapping = getMapping(context, beanName, handlerType, RequestMapping.class);
        if (requestMapping != null) {
            if (requestMapping.value().length > 0) {
                logger.debug("URL: " + requestMapping.value()[0] + " | BEAN: " + beanName);
                return requestMapping.value();
            }
            else {
                logger.debug("URL: " + getDefaultUrlFromControllerName(handlerType)[0] + " | BEAN: " + beanName);
                return getDefaultUrlFromControllerName(handlerType);
            } 
        }
        
        return null;
        
    }
    
}
