package eu.anmore.emed.authentication;

import eu.anmore.emed.diff.DiffBuilder;

/**
 * @author mmiedzinski
 */
public final class UserDiffBuilder extends DiffBuilder<User, UserDiff> {

	public static UserDiffBuilder get() {
		return new UserDiffBuilder();
	}

	public static UserDiffBuilder get(final User user) {
		if (user != null) {
			final UserDiff userDiff = new UserDiff();
			userDiff.username = user.getUsername();
			return new UserDiffBuilder(userDiff, user);
		}
		return new UserDiffBuilder();
	}

	public UserDiffBuilder firstName(final String value) {
		diff.firstName = getAttributeDiff(value, object != null ? object.getFirstName() : null);
		return this;
	}

	public UserDiffBuilder surname(final String value) {
		diff.surname = getAttributeDiff(value, object != null ? object.getSurname() : null);
		return this;
	}

	public UserDiffBuilder username(final String value) {
		diff.username = value;
		return this;
	}

	public UserDiffBuilder password(final String password) {
		diff.password = getAttributeDiff(password, null);
		return this;
	}

	private UserDiffBuilder() {
		super(new UserDiff());
	}

	private UserDiffBuilder(final UserDiff userDiff, final User user) {
		super(userDiff, user);
	}
}
