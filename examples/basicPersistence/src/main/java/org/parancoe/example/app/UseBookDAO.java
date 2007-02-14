/**
 * 
 */
package org.parancoe.example.app;

import java.util.List;

import org.parancoe.example.bo.BookBO;
import org.parancoe.example.dao.BookDao;
import org.parancoe.example.po.Book;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * A simple example of an application using Parancoe persistence module for Book entity.
 * @author <a href="mailto:enricogiurin@gmail.com">Enrico Giurin</a>
 * @version $Revision$
 *
 */
public class UseBookDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance("beanRefFactory.xml");
	    BeanFactoryReference bf = bfl.useBeanFactory("org.parancoe.example");
	      
	    //code added by enrico & emanuele
	        
	    BookBO bookBO =  (BookBO)bf.getFactory().getBean("bookBO");
	    BookDao bookDao = bookBO.getDao();
	    Book myBook = new Book("Parancoe Team", "Parancoe: the best framework");
	        
	    Book myBook2 = new Book("Mario Rossi", "My life");
	    Book myBook3 = new Book("Parancoe Team", "Parancoe: tips & trick");
	    Book myBook4 = new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 320);
	        
	    bookDao.create(myBook);
	    bookDao.create(myBook2);
	    bookDao.create(myBook3);
	    bookDao.create(myBook4);
	        
	    List<Book> myBooks= bookDao.findByAuthor("Parancoe Team");
	    System.out.println(myBooks.size());
	     

	}

}
