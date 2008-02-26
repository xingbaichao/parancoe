/**
 * 
 */
package it.jugpadova.exception;

/**
 * Thrown when there is an authorization exception.
 * @author Enrico Giurin
 *
 */
public class ParancoeAccessDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1384614533433641499L;

	/**
	 * 
	 */
	public ParancoeAccessDeniedException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ParancoeAccessDeniedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ParancoeAccessDeniedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParancoeAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
