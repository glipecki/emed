package eu.anmore.emed;

/**
 * Invalid query exception.
 * 
 * @author Grzegorz Lipecki
 */
public class InvalidQueryException extends RuntimeException {

	public InvalidQueryException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -6817978319461005196L;

}
