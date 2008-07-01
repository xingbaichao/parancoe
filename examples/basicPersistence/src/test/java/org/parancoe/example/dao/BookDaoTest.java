package org.parancoe.example.dao;

import java.util.List;

import org.parancoe.example.po.Book;
import org.parancoe.example.po.Person;
import org.parancoe.example.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Enrico Giurin
 */
public class BookDaoTest extends BaseTest {
    
    @Autowired
    private BookDao dao;
    
    
    public void testAllBooksByBorrower() {
    	List<Book> list = dao.allBooksByBorrower("Ugo", "Benfante");
    	assertTrue(list.size()==2);      
    }
    
    public void testFindByAuthor() {
    	
        List<Book> books = dao.findByAuthor("Doug Lea");
        assertEquals(1, books.size());
        assertEquals(books.get(0).getTitle(), "Concurrent programming in java second edition");
        
    }

    
   
    
}
