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

package org.parancoe.validator;

import java.lang.annotation.ElementType;
import java.util.Locale;
import javax.validation.Configuration;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;
import junit.framework.TestCase;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

/**
 * A base class for tests on validators.
 * 
 * @author Lucio Benfante <lucio.benfante@gmail.com>
 */
public abstract class BaseValidatorTest extends TestCase {

    protected Validator hibernateValidator;

    public BaseValidatorTest(String name) {
        super(name);
        Locale.setDefault(Locale.ENGLISH);
        Configuration<HibernateValidatorConfiguration> configuration =
                Validation.byProvider(HibernateValidator.class).configure();
        configuration.traversableResolver(new TraversableResolver() {

            @Override
            public boolean isReachable(Object traversableObject, Node traversableProperty,
                    Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
                return true;
            }

            @Override
            public boolean isCascadable(Object traversableObject, Node traversableProperty,
                    Class<?> rootBeanType, Path pathToTraversableObject, ElementType elementType) {
                return true;
            }
        });
        hibernateValidator = configuration.buildValidatorFactory().getValidator();
    }
}
