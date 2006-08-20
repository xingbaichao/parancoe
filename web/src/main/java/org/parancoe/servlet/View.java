package org.parancoe.servlet;

import org.parancoe.utility.WebKeys;

public class View {
	private String layout = WebKeys.NO_LAYOUT; 
	private String content = WebKeys.ERROR_CONTENT;
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}
	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}
}
