/**
 * 
 */
package org.parancoe.example.po;

import org.parancoe.persistence.po.hibernate.EntityBase;

/**
 * Represents a book entity.
 * @author <a href="mailto:enricogiurin@gmail.com">Enrico Giurin</a>
 *
 */
@javax.persistence.Entity()
public class Book extends EntityBase {
	private int numPages = 0;
	private String author = null;
	private String title = null;
	


	/**
	 * 
	 */
	public Book() {
		// TODO Auto-generated constructor stub
	}
	public Book(String author, String title) {
		this(author, title, 0);
	}
	
	public Book(String author, String title, int numPages) {
		this.author = author;
		this.title = title;
		this.numPages = numPages;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
