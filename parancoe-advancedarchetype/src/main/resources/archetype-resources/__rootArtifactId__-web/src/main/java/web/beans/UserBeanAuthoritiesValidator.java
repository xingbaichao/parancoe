#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.beans;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * A validator for the authorities checkboxes of a UserBean.
 * 
 * @author Lucio Benfante
 */
public class UserBeanAuthoritiesValidator implements Validator {

    public boolean supports(Class clazz) {
        return UserBean.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        UserBean userBean = (UserBean) target;
        boolean noneChecked = true;
        for (AuthorityCheckBox authorityCheckBox : userBean.getAuthorityCheckBoxes()) {
            if (authorityCheckBox.isChecked()) {
                noneChecked = false;
                break;
            }
        }
        if (noneChecked) {
            errors.rejectValue("authorityCheckBoxes", "noCheckedAuthorities",
                    "At least one authority must be checked!");
        }
    }
}
