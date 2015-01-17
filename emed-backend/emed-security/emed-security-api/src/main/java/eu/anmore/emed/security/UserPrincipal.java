package eu.anmore.emed.security;

import java.io.Serializable;

import eu.anmore.emed.authentication.User;

/**
 * User principal.
 * <p>
 * Used to store user authentication details in Spring Security Context.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public final class UserPrincipal implements Serializable {

	public static UserPrincipal valueOf(User user) {
		return new UserPrincipal(user.getUsername());
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return String.format("UserPrincipal [username=%s]", username);
	}

	private UserPrincipal(String username) {
		this.username = username;
	}

	private static final long serialVersionUID = 1732233418784688472L;

	private final String username;

}
