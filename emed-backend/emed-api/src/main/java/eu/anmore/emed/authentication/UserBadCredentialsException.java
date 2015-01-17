package eu.anmore.emed.authentication;

/**
 * User bad credentials exception.
 * 
 * @author Grzegorz Lipecki
 */
public class UserBadCredentialsException extends Exception {

	public UserBadCredentialsException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -8928560805869459544L;
}
