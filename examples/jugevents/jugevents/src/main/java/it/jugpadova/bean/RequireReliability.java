/**
 * 
 */
package it.jugpadova.bean;

import java.io.Serializable;

/**
 * @author Admin
 *
 */
public class RequireReliability implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2428749062752032826L;

	private boolean requireReliability = false;

	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isRequireReliability() {
		return requireReliability;
	}

	public void setRequireReliability(boolean requireReliability) {
		this.requireReliability = requireReliability;
	}

}
