package eu.anmore.emed.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception while validating authentication.
 * 
 * @author Grzegorz Lipecki
 */
public class AuthenticationValidationException extends AuthenticationException {

	public AuthenticationValidationException(String explanation) {
		super(explanation);
	}

	private static final long serialVersionUID = -3912734531974108374L;

}
