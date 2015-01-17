package eu.anmore.emed.authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Grzegorz Lipecki
 */
public class UserDto {

	public static UserDto valueOf(final User user) {
		final UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.permissions = new ArrayList<String>();
		userDto.setFirstName(user.getFirstName());
		userDto.setSurname(user.getSurname());
		userDto.setActive(user.isActive());
		if (user.getPermissions() != null) {
			userDto.permissions.addAll(user.getPermissions());
		}
		return userDto;
	}

	public User asUser() {
		return new User(username, firstName, surname, permissions, active);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(final List<String> permissions) {
		this.permissions = permissions;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserDto [surname=");
		builder.append(surname);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", username=");
		builder.append(username);
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

	private String surname;

	private String firstName;

	private String username;

	private boolean active;

	private List<String> permissions;
}
