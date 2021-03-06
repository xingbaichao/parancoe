#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.beans;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.parancoe.plugins.security.User;
import org.parancoe.web.test.BaseTest;

/**
 * A test case for the UserBean usage (for UserBean validation, etc.)
 *
 * @author Lucio Benfante
 */
public class UserBeanTest extends BaseTest {

    public void testValidUserBean() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserBean userBean = buildValidUserBean();
        Set<ConstraintViolation<UserBean>> result = validator.validate(userBean);
        assertSize(0, result);
    }

    public void testFailedPasswordValidationUserBean() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserBean userBean = buildValidUserBean();
        userBean.setNewPassword("password");
        userBean.setConfirmPassword("different");
        Set<ConstraintViolation<UserBean>> result = validator.validate(userBean);
        assertSize(1, result);
        assertEquals("{${package}.validation.newpassword}", result.
                iterator().next().getMessageTemplate());
    }

    public void testFailedAuthorityCheckboxesValidationUserBean() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserBean userBean = buildValidUserBean();
        for (AuthorityCheckBox authorityCheckBox : userBean.getAuthorityCheckBoxes()) {
            authorityCheckBox.setChecked(false);
        }
        Set<ConstraintViolation<UserBean>> result = validator.validate(userBean);
        assertSize(1, result);
        assertEquals("{${package}.validation.atleastoneauthoritychecked}", result.
                iterator().next().getMessageTemplate());
    }

    private UserBean buildValidUserBean() {
        UserBean userBean = new UserBean();
        final User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userBean.setUser(user);
        AuthorityCheckBox authorityCheckBox = new AuthorityCheckBox();
        authorityCheckBox.setChecked(true);
        userBean.getAuthorityCheckBoxes().add(authorityCheckBox);
        return userBean;
    }
}
