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

import javax.annotation.Resource;
import org.parancoe.example.dao.BookDao;
import org.parancoe.example.po.Book;
import org.parancoe.example.po.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represents Book Business object.
 * @author <a href="mailto:enricogiurin@gmail.com">Enrico Giurin</a>
 *
 */
@Component
public class BookBO {
    @Resource
    private BookDao bookDao;

    public BookBO() {
    }

    public BookDao getDao() {
        return bookDao;
    }
    /**
     * This method is called when a book is returned to the library.
     * It resets the borrower of that book and returns the Person
     * that had this book.
     * @param BookTitle
     */

    @Transactional()
    public Person bookReturned(String author, String title)
    {
    	Book book = bookDao.findByAuthorAndTitle(author, title).get(0);
    	Person oldBorrower = book.getBorrower();
    	book.setBorrower(null);
    	bookDao.store(book);
    	return oldBorrower;
    }

    public void setDao(BookDao dao) {
        this.bookDao = dao;
    }
}
