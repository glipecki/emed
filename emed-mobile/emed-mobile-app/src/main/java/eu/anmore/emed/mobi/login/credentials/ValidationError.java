package eu.anmore.emed.mobi.login.credentials;

import eu.anmore.emed.mobi.R;

/**
 * User credentials validation error.
 * 
 * @author Grzegorz Lipecki
 */
public enum ValidationError {
	EMPTY_USERNAME {
		@Override
		public int getErrorMessage() {
			return R.string.empty_username_error;
		}
	},
	EMPTY_PASSWORD {
		@Override
		public int getErrorMessage() {
			return R.string.empty_password_error;
		}
	};

	public abstract int getErrorMessage();
}
