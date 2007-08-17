/**
 * 
 */
package it.jugpadova.exception;

/**
 * Thrown when user is already presents in the application
 * @author Enrico Giurin
 *
 */
public class UserAlreadyPresentsException extends Exception {

	/**
	 * 
	 */
	public UserAlreadyPresentsException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UserAlreadyPresentsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UserAlreadyPresentsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserAlreadyPresentsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
