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
package org.parancoe.example.dao;

import java.util.List;

import javax.annotation.Resource;
import org.parancoe.example.po.Book;
import org.parancoe.example.test.BaseTest;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;

/**
 *
 * @author Enrico Giurin
 */
public class BookDaoTest extends BaseTest {

    @Resource
    private BookDao bookDao;

    @Test
    public void allBooksByBorrower() {
        List<Book> list = bookDao.allBooksByBorrower("Ugo", "Benfante");
        assertThat(list, hasSize(2));
    }

    @Test
    public void findByAuthor() {
        List<Book> books = bookDao.findByAuthor("Doug Lea");
        assertThat(books, hasSize(1));
        assertThat(books.get(0).getTitle(), equalTo("Concurrent programming in java second edition"));
    }
}
