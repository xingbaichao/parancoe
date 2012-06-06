#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation annotation for the setting of a new password.
 *
 * @author Lucio Benfante
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewPasswordValidator.class)
@Documented
public @interface NewPassword {

    String message() default "{${package}.validation.newpassword}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
