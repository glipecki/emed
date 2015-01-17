package eu.anmore.emed.mobi.user;

public class User {

	public User() {
		super();
	}

	public User(final String username, final String firstName, final String surname) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", surname=" + surname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (username == null ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		final User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	private String username;

	private String firstName;

	private String surname;

}
