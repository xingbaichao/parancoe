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
package org.parancoe.validator.constraints.impl;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import org.parancoe.validator.BaseValidatorTest;
import org.parancoe.validator.constraints.NewPasswordBean;

/**
 * Tests on the @NewPassword constraint implementation.
 *
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public class NewPasswordValidatorTest extends BaseValidatorTest {

    public NewPasswordValidatorTest(String testName) {
        super(testName);
    }

    public void testValidationOk() {
        NewPasswordBean newPasswordBean =
                new NewPasswordBean("passwd", "passwd");
        Set<ConstraintViolation<NewPasswordBean>> constraintViolations =
                this.hibernateValidator.validate(newPasswordBean);
        assertEquals(0, constraintViolations.size());
    }

    public void testValidationOkWithNoPasswd() {
        NewPasswordBean newPasswordBean =
                new NewPasswordBean("", "");
        Set<ConstraintViolation<NewPasswordBean>> constraintViolations =
                this.hibernateValidator.validate(newPasswordBean);
        assertEquals(0, constraintViolations.size());
    }

    public void testValidationOkWithNoPasswdAndWithConfirm() {
        NewPasswordBean newPasswordBean =
                new NewPasswordBean("", "");
        Set<ConstraintViolation<NewPasswordBean>> constraintViolations =
                this.hibernateValidator.validate(newPasswordBean);
        assertEquals(0, constraintViolations.size());
    }

    public void testValidationFailWithPasswdAnConfirmThatDiffer() {
        NewPasswordBean newPasswordBean =
                new NewPasswordBean("one", "two");
        Set<ConstraintViolation<NewPasswordBean>> constraintViolations =
                this.hibernateValidator.validate(newPasswordBean);
        assertEquals(1, constraintViolations.size());
        ConstraintViolation<NewPasswordBean> constraintViolation = constraintViolations.iterator().
                next();
        assertSame(newPasswordBean, constraintViolation.getRootBean());
        Path propertyPath = constraintViolation.getPropertyPath();
        Iterator<Node> itNodes = propertyPath.iterator();
        assertTrue(itNodes.hasNext());
        Node node = itNodes.next();
        assertEquals("newPassword", node.getName());
        assertEquals("{org.parancoe.validator.constraints.NewPassword.message}", constraintViolation.getMessage());
        assertEquals("{org.parancoe.validator.constraints.NewPassword.message}", constraintViolation.getMessageTemplate());
    }
}
