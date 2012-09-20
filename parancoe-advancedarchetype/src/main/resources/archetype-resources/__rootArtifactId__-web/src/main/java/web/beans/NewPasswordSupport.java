#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ${package}.web.beans;

/**
 *
 * @author lucio
 */
public interface NewPasswordSupport {

    String getConfirmPassword();

    String getNewPassword();

    void setConfirmPassword(String confirmPassword);

    void setNewPassword(String newPassword);

}
