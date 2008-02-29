// Copyright 2006-2007 The Parancoe Team
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.parancoe.test;

import java.util.ArrayList;
import java.util.List;

import org.parancoe.persistence.dao.DaoProvider;
import org.parancoe.persistence.dao.DaoUtils;
import org.parancoe.persistence.po.hibernate.AuthorTC;
import org.parancoe.persistence.po.hibernate.AuthorTCDao;
import org.parancoe.persistence.po.hibernate.BookTC;
import org.parancoe.persistence.po.hibernate.BookTCDao;
import org.parancoe.persistence.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests fixture load with M:N relationships
 * 
 * @author <a href="mailto:michele.franzin@seesaw.it">Michele Franzin</a>
 * @version $Revision$
 */
public class ManyToManyFixturesTest extends BaseTest {

    @Autowired
    protected AuthorTCDao authorDao = null;
    
    @Autowired
    protected BookTCDao bookDao = null;


    public void testDaoExists() {
        assertNotNull("Author dao variable not setted", authorDao);
        assertNotNull("Book dao variable not setted", bookDao);
    }

    // FIXME fails in call with other ones
    public void _testAllSize() {
        assertSize(4, authorDao.findAll());
        assertSize(3, bookDao.findAll());
    }

    // FIXME fails in call with other ones
    public void _testFixturesLoad() {
        List<AuthorTC> authors = authorDao.findByName("joe");
        assertSize(1, authors);
        assertSize(2, authors.get(0).getBooks());
        List<BookTC> books = bookDao.findByTitle("Java manual");
        assertSize(1, books);
        assertSize(3, books.get(0).getAuthors());
        books = bookDao.findByTitle("Mr. Bean");
        assertSize(1, books);
        assertSize(2, books.get(0).getAuthors());
    }

    // FIXME fails in call with other ones
    public void _testRelationSanity() {
        BookTC book1 = new BookTC();
        book1.setTitle("title1");
        bookDao.createOrUpdate(book1);
        BookTC book2 = new BookTC();
        book2.setTitle("title2");
        bookDao.createOrUpdate(book2);

        AuthorTC author1 = new AuthorTC();
        author1.setName("name1");
        List<BookTC> bookList = new ArrayList<BookTC>();
        bookList.add(book1);
        bookList.add(book2);
        author1.setBooks(bookList);
        authorDao.createOrUpdate(author1);

        AuthorTC author2 = new AuthorTC();
        author2.setName("name2");
        bookList.clear();
        bookList.add(book2);
        author2.setBooks(bookList);
        authorDao.createOrUpdate(author2);

        authorDao.deleteAll();
        bookDao.deleteAll();

        List<AuthorTC> authors = authorDao.findByName("name1");
        assertSize(1, authors);
        assertSize(2, authors.get(0).getBooks());
        List<BookTC> books = bookDao.findByTitle("title2");
        assertSize(1, books);
        assertSize(2, books.get(0).getAuthors());

        authorDao.deleteAll();
        bookDao.deleteAll();
    }
}
