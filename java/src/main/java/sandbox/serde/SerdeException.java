package sandbox.serde;

public class SerdeException extends RuntimeException {

	/**
	 * Constructor.
	 *
	 * @param message error message
	 */
	public SerdeException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 *
	 * @param message error message
	 * @param cause cause of serialization exception
	 */
	public SerdeException(String message, Throwable cause) {
		super(message, cause);
	}
}
