package eu.anmore.emed.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import eu.anmore.emed.authentication.User;

/**
 * Validates user authentication.
 * 
 * @author Grzegorz Lipecki
 */
public class UserAuthenticationValidator {

	UserAuthenticationValidator(User user) {
		this.user = user;
	}

	boolean isValid() {
		clearValidationErrors();
		for (AuthenticationValidator validator : validators) {
			runValidator(validator);
		}
		return hasValidationErrors();
	}

	List<String> getValidationMessages() {
		return Collections.unmodifiableList(validationMessages);
	}

	private void clearValidationErrors() {
		validationMessages.clear();
	}

	private void runValidator(AuthenticationValidator validator) {
		if (!validator.validate(user)) {
			validationMessages.add(validator.getErrorMessage());
		}
	}

	private boolean hasValidationErrors() {
		return validationMessages.size() == 0;
	}

	private final User user;

	private final List<String> validationMessages = new ArrayList<String>();

	private final List<AuthenticationValidator> validators = Arrays.asList(
			AuthenticationValidator.ALLOWED_TO_AUTHENTICATE, AuthenticationValidator.ACTIVE_ACCOUNT);

	/**
	 * Possible user authentication validators.
	 * 
	 * @author Grzegorz Lipecki
	 */
	private enum AuthenticationValidator {

		ALLOWED_TO_AUTHENTICATE {

			@Override
			boolean validate(final User user) {
				return user.getPermissions().contains(SystemPermissions.Authentication.AUTHENTICATE);
			}

			@Override
			String getErrorMessage() {
				return "User has insufficent authorities to authenticate";
			}

		},

		ACTIVE_ACCOUNT {

			@Override
			boolean validate(final User user) {
				// TTODO (Grzegorz Lipecki): implement active/disabled status of User
				// return user.isActive();
				return true;
			}

			@Override
			String getErrorMessage() {
				return "User account is not active";
			}

		};

		abstract boolean validate(final User user);

		abstract String getErrorMessage();
	}

}
