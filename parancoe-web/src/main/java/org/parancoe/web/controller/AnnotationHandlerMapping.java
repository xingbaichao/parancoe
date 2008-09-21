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
import java.util.Map;
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
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

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
 * @author Jacopo Murador <mailto:jacopo.murador@seesaw.it/> 
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

    @Override
    protected String[] determineUrlsForHandler(String name) {
        Class<?> ctrl = getApplicationContext().getType(name);
        if (ctrl != null && ctrl.isAnnotationPresent(org.springframework.stereotype.Controller.class)) {
            logger.info("processing controller: " + ctrl.getSimpleName());

            // New annotation since spring 2.5.0
            RequestMapping requestMappingAnn = getMapping(getApplicationContext(), name, ctrl, RequestMapping.class);
            if (requestMappingAnn != null) {
                if (requestMappingAnn.value().length > 0) {
                    for(String url: requestMappingAnn.value()) {
                        logger.info("URL: " + url + " | BEAN: " + name);
                    }
                    return requestMappingAnn.value();
                }
                else {
                    String[] result = getDefaultUrlFromControllerName(ctrl);
                    logger.info("URL: " + result[0] + " | BEAN: " + name);
                    return result;
                }
            }

            // For compatibility with the past

            // check for DefaultUrlMapping
            DefaultUrlMapping defaultMappingAnn = getMapping(getApplicationContext(), name, ctrl, DefaultUrlMapping.class);
            if(defaultMappingAnn != null){
                String[] result = getDefaultUrlFromControllerName(ctrl);
                logger.info("URL: " + result[0] + " | BEAN: " + name);
                return result;
            }

            //check for UrlMapping annotation
            UrlMapping urlMappingAnn = getMapping(getApplicationContext(), name, ctrl, UrlMapping.class);
            if (urlMappingAnn != null) {
                logger.info("URL: " + urlMappingAnn.value() + " | BEAN: " + name);
                String[] result = {urlMappingAnn.value()};
                return result;    
            }


            //check for MultiUrlMapping annotation
            MultiUrlMapping multiUrlMappingAnn = getMapping(getApplicationContext(), name, ctrl, MultiUrlMapping.class);
            if (multiUrlMappingAnn != null) {
                String[] result = new String[multiUrlMappingAnn.values().length]; 
                for(int i = 0; i < multiUrlMappingAnn.values().length; i++) {
                    logger.info("URL: " + multiUrlMappingAnn.values()[i].value() + " | BEAN: " + name);
                    result[i] =  multiUrlMappingAnn.values()[i].value();
                } 
                return result;   
            }
        }
        return null;
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
    
}
