#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.beans;

import ${package}.core.po.User;
import ${package}.web.validation.NewPassword;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * A bean for editing a user.
 *
 * @author Lucio Benfante
 */
@NewPassword
public class UserProfileBean implements NewPasswordSupport {

    private User user;
    private String newPassword;
    private String confirmPassword;

    @Valid
    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
