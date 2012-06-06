/**
 * Copyright (C) 2006-2010 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Validator.
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
package org.parancoe.validator.constraints.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang.StringUtils;
import org.parancoe.validator.constraints.NewPassword;

/**
 * A validator for the setting of a new password.
 *
 * @author Lucio Benfante
 */
public class NewPasswordValidator implements
        ConstraintValidator<NewPassword, Object> {

    private NewPassword constraintAnnotation;

    @Override
    public void initialize(NewPassword constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String newPasswordValue = null;
        String confirmPasswordValue = null;
        boolean newPasswordFound = false;
        boolean confirmPasswordFound = false;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(value.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (constraintAnnotation.newPasswordProperty().equals(propertyDescriptor.getName())) {
                    newPasswordValue = (String) propertyDescriptor.getReadMethod().invoke(value);
                    newPasswordFound = true;
                }
                if (constraintAnnotation.confirmPasswordProperty().equals(
                        propertyDescriptor.getName())) {
                    confirmPasswordValue = (String) propertyDescriptor.getReadMethod().invoke(value);
                    confirmPasswordFound = true;
                }
                if (newPasswordFound && confirmPasswordFound) {
                    break;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Can't validate this bean.", ex);
        }
        if (!newPasswordFound) {
            throw new RuntimeException("Can't validate this bean: property "
                    + constraintAnnotation.newPasswordProperty() + " not found.");
        }
        if (!confirmPasswordFound) {
            throw new RuntimeException("Can't validate this bean: property "
                    + constraintAnnotation.confirmPasswordProperty() + " not found.");
        }
        boolean result = false;
        if (value == null || (StringUtils.isBlank(newPasswordValue)
                && StringUtils.isBlank(confirmPasswordValue))) {
            result = true;
        } else {
            if (StringUtils.isNotBlank(newPasswordValue)
                    && newPasswordValue.equals(confirmPasswordValue)) {
                result = true;
            }
        }
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{org.parancoe.validator.constraints.NewPassword.message}").
                    addNode("newPassword").
                    addConstraintViolation();
        }
        return result;
    }
}
