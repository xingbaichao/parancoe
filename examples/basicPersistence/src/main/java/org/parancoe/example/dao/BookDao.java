/**
 * 
 */
package org.parancoe.example.dao;


import java.util.List;

import org.parancoe.example.po.Book;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * @author Enrico Giurin
 *
 */
@Dao(entity=Book.class)
public interface BookDao extends GenericDao<Book, Long>{
		List<Book> findByAuthor(String author);
	    List<Book> findByTitle(String title);
	   

}
