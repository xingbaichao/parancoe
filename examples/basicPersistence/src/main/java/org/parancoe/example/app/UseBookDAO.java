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
	      
	      //code added by enrico
	        
	        BookBO bookBO =  (BookBO)bf.getFactory().getBean("bookBO");
	        BookDao bookDao = bookBO.getDao();
	        Book myBook = new Book("Parancoe Team", "Parancoe: the best framework");
	        
	        Book myBook2 = new Book("Mario Rossi", "My life");
	        Book myBook3 = new Book("Parancoe Team", "Parancoe: tips & trick");
	        
	        bookDao.create(myBook);
	        bookDao.create(myBook2);
	        bookDao.create(myBook3);
	        
	        List<Book> myBooks= bookDao.findByAuthor("Parancoe Team");
	        System.out.println(myBooks.size());
	     

	}

}
