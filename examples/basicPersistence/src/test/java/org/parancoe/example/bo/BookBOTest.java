package org.parancoe.example.bo;

import java.util.List;

import org.parancoe.example.po.Book;
import org.parancoe.example.po.Person;
import org.parancoe.example.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Enrico Giurin
 */
public class BookBOTest extends BaseTest {
    
    @Autowired
    private BookBO bookBO;
    
    
   public void testBookReturned()
   {
	   Person p = bookBO.bookReturned("Doug Lea", "Concurrent programming in java second edition");
	   assertTrue(p.getFirstName().equals("Ugo"));
	   
   }
    
   
    
}
