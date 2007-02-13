/**
 * 
 */
package org.parancoe.example.po;

import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * Represents a book entity.
 * @author Enrico Giurin
 *
 */
@javax.persistence.Entity()
public class Book extends EntityBase {
	private int mumPages = 0;
	private String author = null;
	private String title = null;
	


	/**
	 * 
	 */
	public Book() {
		// TODO Auto-generated constructor stub
	}
	public Book(String author, String title) {
		this.author = author;
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getMumPages() {
		return mumPages;
	}

	public void setMumPages(int mumPages) {
		this.mumPages = mumPages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
