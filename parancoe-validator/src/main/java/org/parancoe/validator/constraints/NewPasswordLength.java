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
package org.parancoe.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.parancoe.validator.constraints.impl.NewPasswordLengthValidator;

/**
 * Validation annotation for the setting of a new password.
 * The validation fails if the new password property is not blank and
 * hasn't a length between min and max.
 *
 * Example:
 * <pre>
 * &#064;{@code NewPassword(newPasswordProperty="newPassword", confirmPasswordProperty="confirmPassword")
 * &#064;{@code NewPasswordLength(newPasswordProperty="newPassword", confirmPasswordProperty="confirmPassword", min = 6)
 * public class NewPasswordBean implements Serializable {
 *
 *     protected String newPassword;
 *     protected String confirmPassword;
 *
 *     public String getNewPassword() {
 *         return newPassword;
 *     }
 *
 *     public void setNewPassword(String newPassword) {
 *         this.newPassword = newPassword;
 *     }
 *
 *     public String getConfirmPassword() {
 *         return confirmPassword;
 *     }
 *
 *     public void setConfirmPassword(String confirmPassword) {
 *         this.confirmPassword = confirmPassword;
 *     }
 * }
 * }</pre>
 * 
 * @author Lucio Benfante
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewPasswordLengthValidator.class)
@Documented
public @interface NewPasswordLength {

    String message() default "{org.parancoe.validator.constraints.NewPassword.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The name of the property that contains the new password.
     *
     * @return The name of the property.
     */
    String newPasswordProperty() default "newPassword";

    /**
     * The name of the property that contains the confirm password.
     *
     * @return The name of the property.
     */
    String confirmPasswordProperty() default "confirmPassword";
    
    /**
     * The min length of the new password.
     *
     * @return The min length of the new password.
     */
    int min() default 0;

    /**
     * The min length of the new password.
     *
     * @return The min length of the new password.
     */
    int max() default Integer.MAX_VALUE;
    
    /**
     * Pass the validation if the new password is blank. Usually for not changing the current password.
     * 
     * @return True if the validation must pass if the new password is blank.
     */
    boolean passIfBlank() default true;
    
}
