package server;

public class BadRequestException extends RuntimeException {

	/**
	 * The serial version unique ID variable.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new BadRequestException without parameters
	 * 
	 */
	public BadRequestException() {
	}
	
	/**
	 * Initialise the exception with the given message and Throwable.
	 * 
	 * @param message
	 *        The error message of the exception.
	 * @param exception
	 *        The Throwable of the exception.
	 * @effect This new BadRequestException is initialised as new 
	 *         RuntimeException with the given message and exception.
	 *         | super(message,exception)
	 */
	public BadRequestException(String message, Throwable exception) {
		super(message,exception);
	}
	
	/**
	 * Initialise the exception with the given message. 
	 * 
	 * @param message
	 *        The error message of the exception.
	 * @effect This new BadRequestException is initialised as new 
	 *         RuntimeException with the given message.
	 *         | super(message)
	 */
	public BadRequestException(String message) {
		super(message);
	}
	
	/**
	 * Initialise the exception with the given Throwable. 
	 * 
	 * @param exception
	 *        The Throwable of the exception.
	 * @effect This new BadRequestException is initialised as new 
	 *         RuntimeException with the given exception.
	 *         | super(exception)
	 */
	public BadRequestException(Throwable exception) {
		super(exception);
	}
}
