/**
 * 
 */
package org.parancoe.example.bo;

import org.parancoe.example.dao.BookDao;

/**
  * Represents Book Business object.
  * @author <a href="mailto:enricogiurin@gmail.com">Enrico Giurin</a>
  *
 */
public class BookBO {
	private BookDao dao;
	
	public BookBO()
	{
		
	}

	public BookDao getDao() {
		return dao;
	}

	public void setDao(BookDao dao) {
		this.dao = dao;
	}
	

}
