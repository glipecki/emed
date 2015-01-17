package eu.anmore.emed.authentication;

/**
 * Invalid Authentication authenticate request exception.
 * 
 * @author Grzegorz Lipecki
 */
public class InvalidAuthenticationRequestException extends Exception {

	public InvalidAuthenticationRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAuthenticationRequestException(String message) {
		super(message);
	}

	public InvalidAuthenticationRequestException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 8829761925990375231L;

}
