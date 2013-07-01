/**
 * Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>
 *
 * This file is part of Parancoe Example - Basic Persistence.
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
package org.parancoe.example.bo;

import org.parancoe.example.po.Person;
import org.parancoe.example.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 *
 * @author Enrico Giurin
 */
public class BookBOTest extends BaseTest {

    @Autowired
    private BookBO bookBO;

    @Test
    public void bookReturned() {
        Person p = bookBO.bookReturned("Doug Lea",
                "Concurrent programming in java second edition");
        assertThat(p.getFirstName(), equalTo("Ugo"));
    }
}
