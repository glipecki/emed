package eu.anmore.emed.mobi.login.authenticate;

import eu.anmore.emed.mobi.user.User;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

/**
 * Authenticate task.
 * 
 * @author Grzegorz Lipecki
 */
public class AuthenticateTask {

	public static AuthenticateResult resultSuccess(final boolean authenticated, final User user) {
		return new AuthenticateResult(authenticated, user);
	}

	public static AuthenticateResult resultFail() {
		return new AuthenticateResult(false, null);
	}

	public static AuthenticateAction action(final String username, final String password) {
		return new AuthenticateAction(username, password);
	}

	/**
	 * Authenticate action.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static class AuthenticateAction implements TaskAction {

		public AuthenticateAction(final String username, final String password) {
			super();
			this.username = username;
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		private final String username;

		private final String password;

	}

	/**
	 * Authenticate result.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static class AuthenticateResult implements TaskResult {

		public AuthenticateResult(final Boolean authenticated, final User user) {
			super();
			this.authenticated = authenticated;
			this.user = user;
		}

		public Boolean isAuthenticated() {
			return authenticated;
		}

		public User getUser() {
			return user;
		}

		private final User user;

		private final Boolean authenticated;
	}

}
