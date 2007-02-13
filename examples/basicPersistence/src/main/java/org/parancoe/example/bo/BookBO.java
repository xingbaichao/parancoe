/**
 * 
 */
package org.parancoe.example.bo;

import org.parancoe.example.dao.BookDao;

/**
 * @author Enrico Giurin
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
