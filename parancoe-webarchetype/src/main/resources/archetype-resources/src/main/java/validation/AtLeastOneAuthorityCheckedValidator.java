#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.validation;

import ${package}.beans.AuthorityCheckBox;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A validator for the setting of a new password.
 *
 * @author Lucio Benfante
 */
public class AtLeastOneAuthorityCheckedValidator implements
        ConstraintValidator<AtLeastOneAuthorityChecked, Collection<AuthorityCheckBox>> {

    @Override
    public void initialize(AtLeastOneAuthorityChecked constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<AuthorityCheckBox> value,
            ConstraintValidatorContext context) {
        boolean noneChecked = true;
        for (AuthorityCheckBox authorityCheckBox : value) {
            if (authorityCheckBox.isChecked()) {
                noneChecked = false;
                break;
            }
        }
        return !noneChecked;
    }
}
