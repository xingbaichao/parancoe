/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic WebApp.
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
package org.parancoe.basicWebApp;

import java.util.Date;
import javax.annotation.Resource;
import org.parancoe.basicWebApp.po.Person;
import org.parancoe.web.test.BaseTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * A test for the application validation.
 *
 * @author lucio
 */
public class ValidationTest extends BaseTest {

    @Resource
    private Validator validator;

    public void testPersonValidation() {
        Person person =
                new Person("Lucio", "Benfante", new Date());
        BindingResult result = new BeanPropertyBindingResult(person, "person");
        validator.validate(person, result);
        assertFalse(result.hasErrors());
    }

    public void testPersonValidationInError() {
        Person person =
                new Person("", "Benfante", new Date());
        BindingResult result = new BeanPropertyBindingResult(person, "person");
        validator.validate(person, result);
        assertTrue(result.hasErrors());
        assertSize(2, result.getAllErrors());
    }

}
