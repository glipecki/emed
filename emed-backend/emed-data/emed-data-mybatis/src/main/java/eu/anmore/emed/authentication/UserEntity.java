package eu.anmore.emed.authentication;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * User entity.
 * <p>
 * User object representation in DB.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
@Alias("UserEntity")
public class UserEntity {

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserEntity [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", surname=");
		builder.append(surname);
		builder.append(", username=");
		builder.append(username);
		builder.append(", passwordHash=");
		builder.append(passwordHash);
		builder.append(", salt=");
		builder.append(salt);
		builder.append(", active=");
		builder.append(active);
		builder.append(", permissions=");
		builder.append(permissions);
		builder.append("]");
		return builder.toString();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	UserEntity(final String firstName, final String surname, final String username, final String passwordHash,
			final String salt, final boolean active) {
		super();
		this.firstName = firstName;
		this.surname = surname;
		this.username = username;
		this.passwordHash = passwordHash;
		this.salt = salt;
		this.active = active;
	}

	UserEntity() {
		super();
	}

	int getId() {
		return id;
	}

	void setId(final int id) {
		this.id = id;
	}

	List<String> getPermissions() {
		return permissions;
	}

	void setPermissions(final List<String> permissions) {
		this.permissions = permissions;
	}

	String getSalt() {
		return salt;
	}

	void setSalt(final String salt) {
		this.salt = salt;
	}

	String getUsername() {
		return username;
	}

	void setUsername(final String username) {
		this.username = username;
	}

	String getPasswordHash() {
		return passwordHash;
	}

	void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
	}

	User getUser() {
		return new User(username, firstName, surname, permissions, passwordHash, salt, isActive());
	}

	private int id;

	private String firstName;

	private String surname;

	private String username;

	private String passwordHash;

	private String salt;

	private boolean active;

	private List<String> permissions;
}
