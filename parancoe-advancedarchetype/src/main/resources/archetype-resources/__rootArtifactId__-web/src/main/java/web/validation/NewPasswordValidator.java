#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.validation;

import ${package}.web.beans.NewPasswordSupport;
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
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{${package}.validation.newpassword}")
                    .addNode("newPassword").addConstraintViolation();
        }
        return result;
    }
}
