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
package org.parancoe.web.validation;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springmodules.validation.bean.BeanValidator;
import org.springmodules.validation.bean.conf.BeanValidationConfiguration;
import org.springmodules.validation.bean.conf.loader.BeanValidationConfigurationLoader;
import org.springmodules.validation.bean.conf.loader.annotation.AnnotationBeanValidationConfigurationLoader;
import org.springmodules.validation.bean.converter.ErrorCodeConverter;
import org.springmodules.validation.bean.converter.ModelAwareErrorCodeConverter;
import org.springmodules.validation.bean.rule.ValidationRule;

/**
 * Gives convenience methods for validation through direct web remoting (DWR) 
 *
 * @author gtrev
 */
public class DWRAnnotationValidator extends BeanValidator implements ApplicationContextAware{
    
    private final static Logger logger = Logger.getLogger(DWRAnnotationValidator.class);
    //private final static Log logger = LogFactory.getLog(DWRAnnotationValidator.class);
    
    private BeanValidationConfigurationLoader configurationLoader;
    
    private ErrorCodeConverter errorCodeConverter;
    
    private ApplicationContext applicationContext;

    public DWRAnnotationValidator() {
    	this(new AnnotationBeanValidationConfigurationLoader());
    }

    public DWRAnnotationValidator(BeanValidationConfigurationLoader configurationLoader) {
	this.configurationLoader = configurationLoader;  
	this.errorCodeConverter = new ModelAwareErrorCodeConverter();
    }
    
    /**
     * Validate a propertyValue of the commandClass of controller controllerId
     * 
     * @param controllerId 
     * @param propertyName
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public String validateDWR(String controllerId, String propertyName, String propertyValue) {
        if (propertyName == null) 
            throw new IllegalArgumentException(propertyName);
        
        // look up to the type of validated bean
        SimpleFormController ctrl = (SimpleFormController)applicationContext.getBean(controllerId);
        
        BeanWrapper bw = new BeanWrapperImpl(ctrl);
        Class clazz = (Class)bw.getPropertyValue("commandClass");
        
        // load validation rules for the given class
        BeanValidationConfiguration configuration = configurationLoader.loadConfiguration(clazz);
	
        // search for validation rules 
        ValidationRule[] rules = configuration.getPropertyRules(propertyName);
        
	// creating a new instance of the type validated 
        Object obj = null;
	try {
	    obj = clazz.newInstance();
	} catch (InstantiationException e) {
	    throw new RuntimeException("Can't instantiate class", e);
	} catch (IllegalAccessException e) {
            throw new RuntimeException("Instantiation is forbidden for this class", e);
	}
        
        BeanWrapper wrapper = new BeanWrapperImpl(obj);
  
        // register all custom editors of the controller in the new bean wrapper
        if(ctrl.getPropertyEditorRegistrars() != null){
            for (PropertyEditorRegistrar elem : ctrl.getPropertyEditorRegistrars()) {
                logger.info("registering custom editors");
        	elem.registerCustomEditors(wrapper);
            }
        } else {
            logger.info("no custom editor to register");
        }
       
        //setting propertyValue to the instance created
        PropertyValue val = new PropertyValue(propertyName, propertyValue);
        wrapper.setPropertyValue(val);
        
	//Errors object
        Errors errors = new BeanPropertyBindingResult(obj, controllerId);
	
        //start validation
        validateAndShortCircuitRules(rules, propertyName, obj, errors);
        
        return getValidationMessage(errors, propertyName);
        
    }
    
    /**
     * Returns a localized validation message for the given field (if any)
     */
    protected String getValidationMessage(Errors errors, String fieldName) {
        String message = null;
        FieldError fieldError = errors.getFieldError(fieldName);
        if (fieldError != null){
            MessageSource msgSrc = (MessageSource)applicationContext.getBean("messageSource");
            Object[] par = fieldError.getArguments();
            message = msgSrc.getMessage(fieldError.getCode(),par,LocaleContextHolder.getLocale());
        }
        return message;
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;	
    }
        
}

