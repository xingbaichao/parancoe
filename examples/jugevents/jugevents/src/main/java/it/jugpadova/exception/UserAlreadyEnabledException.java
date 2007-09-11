/**
 * 
 */
package it.jugpadova.exception;

/**
 * @author Enrico Giurin
 *
 */
public class UserAlreadyEnabledException extends RuntimeException {

	/**
	 * 
	 */
	public UserAlreadyEnabledException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UserAlreadyEnabledException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UserAlreadyEnabledException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UserAlreadyEnabledException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
