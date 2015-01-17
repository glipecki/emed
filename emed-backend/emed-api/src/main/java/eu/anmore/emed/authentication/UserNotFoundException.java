package eu.anmore.emed.authentication;

/**
 * User not found exception.
 * 
 * @author Grzegorz Lipecki
 */
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	/**
	 * Serialization number.
	 */
	private static final long serialVersionUID = -9042028668436263829L;
}
