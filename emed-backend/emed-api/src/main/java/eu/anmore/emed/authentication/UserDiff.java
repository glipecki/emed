package eu.anmore.emed.authentication;

import eu.anmore.emed.diff.ObjectDiff;
import eu.anmore.emed.diff.ValueDiff;

/**
 * @author mmiedzinski
 */
public class UserDiff implements ObjectDiff<User> {

	public String getUsername() {
		return username;
	}

	public ValueDiff<String> getSurname() {
		return surname;
	}

	public ValueDiff<String> getFirstName() {
		return firstName;
	}

	public ValueDiff<String> getPasswordHash() {
		return passwordHash;
	}

	public ValueDiff<String> getSalt() {
		return salt;
	}

	public ValueDiff<String> getPassword() {
		return password;
	}

	@Override
	public boolean hasChanges() {
		return firstName.isChanged() || surname.isChanged() || getSalt().isChanged() || getPasswordHash().isChanged()
				|| password.isChanged();
	}

	void setSalt(final ValueDiff<String> salt) {
		this.salt = salt;
	}

	void setPasswordHash(final ValueDiff<String> passwordHash) {
		this.passwordHash = passwordHash;
	}

	ValueDiff<String> firstName = ValueDiff.notChanged();

	ValueDiff<String> surname = ValueDiff.notChanged();

	String username;

	ValueDiff<String> password = ValueDiff.notChanged();

	private ValueDiff<String> salt = ValueDiff.notChanged();

	private ValueDiff<String> passwordHash = ValueDiff.notChanged();
}