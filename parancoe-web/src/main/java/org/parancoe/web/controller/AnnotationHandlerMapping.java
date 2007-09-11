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

import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.parancoe.web.controller.annotation.MultiUrlMapping;
import org.parancoe.web.controller.annotation.UrlMapping;
import org.springframework.beans.BeansException;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * An HandlerMapping implementation that reads url mapping from annotations.
 * Annotation supported are:
 * <ol>
 *  <li>{@link org.parancoe.web.controller.annotation.UrlMapping}</li>
 *  <li>{@link org.parancoe.web.controller.annotation.MultiUrlMapping}</li>
 * </ol>
 * 
 * This handlerMapping inherits directly from BeanNameUrlHandlerMapping, in this
 * way it's still possible to map urls to controller using the 'name' attribute.
 * 
 * @author Andrea Nasato <mailto:andrea.nasato@jugpadova.it/> 
 * @version $$Revision$$
 */
public class AnnotationHandlerMapping extends BeanNameUrlHandlerMapping {

    private static final Logger logger = Logger.getLogger(AnnotationHandlerMapping.class);

    @Override
    public void initApplicationContext() throws ApplicationContextException {
        super.initApplicationContext();
        registerHandlers();
    }

    /**
     *  Iterates over beans of type Controller registered in the current spring context
     *  searching for UrlAnnotation and MultiUrlAnnotation. 
     * 
     *  If a controller doesn't have annotations we search in the superclass. This is 
     *  the case of AbstractControllers: the concrete implementation does not
     *  inherit annotations which may have been provided by the superclass
     *
     */
    protected void registerHandlers() {
        Map controllersMap = getApplicationContext().getBeansOfType(Controller.class);

        for (Iterator it = controllersMap.values().iterator(); it.hasNext();) {
            Controller ctrl = (Controller) it.next();
            logger.info("processing controller: " + ctrl.getClass().getSimpleName());

            //check for UrlMapping annotation
            if (ctrl.getClass().isAnnotationPresent(UrlMapping.class)) {
                UrlMapping urlMappingAnn = ctrl.getClass().getAnnotation(UrlMapping.class);
                mapUrl(ctrl, urlMappingAnn);
            } else if (ctrl.getClass().getSuperclass().isAnnotationPresent(UrlMapping.class)) {
                UrlMapping urlMappingAnn = ctrl.getClass().getSuperclass().getAnnotation(UrlMapping.class);
                mapUrl(ctrl, urlMappingAnn);
            }


            //check for MultiUrlMapping annotation
            if (ctrl.getClass().isAnnotationPresent(MultiUrlMapping.class)) {
                MultiUrlMapping multiUrlMappingAnn = ctrl.getClass().getAnnotation(MultiUrlMapping.class);

                for (int i = 0; i < multiUrlMappingAnn.values().length; i++) {
                    UrlMapping urlMapping = multiUrlMappingAnn.values()[i];
                    mapUrl(ctrl, urlMapping);
                }
            } else if (ctrl.getClass().getSuperclass().isAnnotationPresent(MultiUrlMapping.class)) {
                MultiUrlMapping multiUrlMappingAnn = ctrl.getClass().getSuperclass().getAnnotation(MultiUrlMapping.class);

                for (int i = 0; i < multiUrlMappingAnn.values().length; i++) {
                    UrlMapping urlMapping = multiUrlMappingAnn.values()[i];
                    mapUrl(ctrl, urlMapping);
                }
            }
        }
    }

    private void mapUrl(Controller ctrl, UrlMapping urlMappingAnn) throws BeansException, IllegalStateException {
        java.lang.String url = urlMappingAnn.value();

        if (!this.getHandlerMap().containsKey(url)) {
            logger.info("registering handler [" + ctrl.toString() + "] for url [" + url + "]");
            registerHandler(url, ctrl);
        }
    }

}
