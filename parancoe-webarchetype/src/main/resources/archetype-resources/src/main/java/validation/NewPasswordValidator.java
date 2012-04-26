#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.validation;

import ${package}.beans.NewPasswordSupport;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang.StringUtils;

/**
 * A validator for the setting of a new password.
 *
 * @author Lucio Benfante
 */
public class NewPasswordValidator implements
        ConstraintValidator<NewPassword, NewPasswordSupport> {

    @Override
    public void initialize(NewPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(NewPasswordSupport value, ConstraintValidatorContext context) {
        boolean result = false;
        if (value == null || (StringUtils.isBlank(value.getNewPassword())
                && StringUtils.isBlank(value.getConfirmPassword()))) {
            result = true;
        } else {
            if (StringUtils.isNotBlank(value.getNewPassword())
                    && value.getNewPassword().equals(value.getConfirmPassword())) {
                result = true;
            }
        }
        return result;
    }
}
