/**
 * 
 */
package it.jugpadova.exception;

/**
 * @author Enrico Giurin
 *
 */
public class UserNotEnabledException extends RuntimeException {

	/**
	 * 
	 */
	public UserNotEnabledException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UserNotEnabledException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UserNotEnabledException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserNotEnabledException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
