package it.jugpadova.exception;

/**
 * Thrown when an email is already present in the application
 * @author Lucio Benfante
 *
 */
public class EmailAlreadyPresentException extends RuntimeException {

	public EmailAlreadyPresentException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmailAlreadyPresentException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EmailAlreadyPresentException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailAlreadyPresentException(String message, Throwable cause) {
		super(message, cause);
	}

}
