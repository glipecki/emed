package eu.anmore.emed.authentication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Authenticated user.
 * <p>
 * Contains user information and permissions.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public class User {

	public String getUsername() {
		return username;
	}

	public List<String> getPermissions() {
		return Collections.unmodifiableList(permissions);
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [firstName=");
		builder.append(firstName);
		builder.append(", surname=");
		builder.append(surname);
		builder.append(", username=");
		builder.append(username);
		builder.append(", permissions=");
		builder.append(permissions);
		builder.append(", passwordHash=");
		builder.append(passwordHash);
		builder.append(", salt=");
		builder.append(salt);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

	public boolean isActive() {
		return active;
	}

	User(final String username, final String firstName, final String surname, final List<String> permissions,
			final boolean active) {
		this(username, firstName, surname, permissions, null, null, active);
	}

	User(final String username, final String firstName, final String surname, final List<String> permissions,
			final String passwordHash, final String salt, final boolean active) {
		this.username = username;
		this.active = active;
		this.permissions = new ArrayList<String>();
		if (permissions != null) {
			this.permissions.addAll(permissions);
		}
		this.passwordHash = passwordHash;
		this.salt = salt;
		this.firstName = firstName;
		this.surname = surname;
	}

	private final String firstName;

	private final String surname;

	private final String username;

	private final List<String> permissions;

	private final String passwordHash;

	private final String salt;

	private final boolean active;
}
