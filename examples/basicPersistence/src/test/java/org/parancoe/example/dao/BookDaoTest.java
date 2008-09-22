package org.parancoe.example.dao;

import java.util.List;

import javax.annotation.Resource;
import org.parancoe.example.po.Book;
import org.parancoe.example.po.Person;
import org.parancoe.example.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Enrico Giurin
 */
public class BookDaoTest extends BaseTest {
    
    @Resource
    private BookDao bookDao;
    
    
    public void testAllBooksByBorrower() {
    	List<Book> list = bookDao.allBooksByBorrower("Ugo", "Benfante");
    	assertEquals(2, list.size());      
    }
    
    public void testFindByAuthor() {
    	
        List<Book> books = bookDao.findByAuthor("Doug Lea");
        assertEquals(1, books.size());
        assertEquals(books.get(0).getTitle(), "Concurrent programming in java second edition");
        
    }

    
   
    
}
