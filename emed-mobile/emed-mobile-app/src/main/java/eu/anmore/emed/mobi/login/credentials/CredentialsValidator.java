package eu.anmore.emed.mobi.login.credentials;

import java.util.ArrayList;
import java.util.List;

/**
 * Base user credentials validator.
 * 
 * @author Grzegorz Lipecki
 */
public class CredentialsValidator {

	public static CredentialsValidator get(final String username, final String password) {
		return new CredentialsValidator(username, password);
	}

	public CredentialsValidator nonEmptyUsername() {
		if (username == null || username.isEmpty()) {
			errors.add(ValidationError.EMPTY_USERNAME);
		}
		return this;
	}

	public CredentialsValidator nonEmptyPassword() {
		if (password == null || password.isEmpty()) {
			errors.add(ValidationError.EMPTY_PASSWORD);
		}
		return this;
	}

	public List<ValidationError> validate() {
		return new ArrayList<ValidationError>(errors);
	}

	private CredentialsValidator(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	private final String username;

	private final String password;

	private final List<ValidationError> errors = new ArrayList<ValidationError>();

}
