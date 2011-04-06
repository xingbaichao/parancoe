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

import java.io.Serializable;

/**
 * A class for testing NewPassord constraint.
 *
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
@NewPassword(newPasswordProperty="newPassword", confirmPasswordProperty="confirmPassword")
public class NewPasswordBean implements Serializable {

    protected String newPassword;
    protected String confirmPassword;

    public NewPasswordBean() {
    }

    public NewPasswordBean(String newPassword, String confirmPassword) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    /**
     * Get the value of newPassword
     *
     * @return the value of newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set the value of newPassword
     *
     * @param newPassword new value of newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get the value of confirmPassword
     *
     * @return the value of confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Set the value of confirmPassword
     *
     * @param confirmPassword new value of confirmPassword
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
