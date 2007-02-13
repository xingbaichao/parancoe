/**
 * 
 */
package org.parancoe.example.dao;


import java.util.List;

import org.parancoe.example.po.Book;
import org.parancoe.persistence.dao.generic.Dao;
import org.parancoe.persistence.dao.generic.GenericDao;

/**
 * Represents Book DAO
  * @author <a href="mailto:enricogiurin@gmail.com">Enrico Giurin</a>
 *
 */
@Dao(entity=Book.class)
public interface BookDao extends GenericDao<Book, Long>{
		List<Book> findByAuthor(String author);
	    List<Book> findByTitle(String title);
	   

}
