package eu.anmore.emed.authentication;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import eu.anmore.emed.diff.ValueDiff;
import eu.anmore.emed.security.PasswordGenerator;

/**
 * {@link AuthenticationBean}.
 * 
 * @author Grzegorz Lipecki
 */
public class AuthenticationBeanImpl implements AuthenticationBean {

	@Override
	public User authenticate(final String username, final String password) throws UserNotFoundException,
			UserBadCredentialsException {
		validateArguments(username, password);
		final User user = getUserFromRepository(username);
		validatePassword(password, user);
		validateActive(user);
		return user;
	}

	@Override
	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	@Override
	public User addUser(final UserDiff userDiff) {
		final String salt = UUID.randomUUID().toString().toString();
		final PasswordGenerator passwordGenerator = new PasswordGenerator(userDiff.getPassword().getNewValue(), salt);

		userDiff.setPasswordHash(ValueDiff.changed(passwordGenerator.getHashedPassword(), null));
		userDiff.setSalt(ValueDiff.changed(salt, null));
		return userRepository.insert(userDiff);
	}

	@Override
	public User editUser(final UserDiff userDiff) throws UserNotFoundException {
		if (userDiff.getPassword().isChanged() && !userDiff.getPassword().getNewValue().isEmpty()) {
			final String salt = UUID.randomUUID().toString().toString();
			final PasswordGenerator passwordGenerator = new PasswordGenerator(userDiff.getPassword().getNewValue(),
					salt);
			userDiff.setPasswordHash(ValueDiff.changed(passwordGenerator.getHashedPassword(), null));
			userDiff.setSalt(ValueDiff.changed(salt, null));
		}
		userRepository.edit(userDiff);
		return userRepository.getUser(userDiff.getUsername());
	}

	@Override
	public boolean isUnique(final String username) {
		try {
			return userRepository.getUser(username) == null;
		} catch (final UserNotFoundException e) {
			return true;
		}
	}

	@Override
	public boolean blockUser(final String username) {
		userRepository.blockUser(username);
		return true;
	}

	@Override
	public boolean unblockUser(final String username) {
		userRepository.unblockUser(username);
		return true;
	}

	@Override
	public boolean addPermission(final String username, final String permission) {
		userRepository.addPermission(username, permission);
		return true;
	}

	@Override
	public boolean removePermission(final String username, final String permission) {
		userRepository.removePermission(username, permission);
		return true;
	}

	AuthenticationBeanImpl(final UsersDao userRepository) {
		this.userRepository = userRepository;
	}

	private void validateActive(final User user) throws UserBadCredentialsException {
		if (!user.isActive()) {
			throw new UserBadCredentialsException("User is not active");
		}
	}

	/**
	 * Validate passed arguments.
	 * 
	 * @param username
	 * @param password
	 * @throws IllegalArgumentException
	 */
	private void validateArguments(final String username, final String password) throws IllegalArgumentException {
		validateUsernameArgument(username);
		validatePasswordArgument(password);
	}

	/**
	 * Validate password and throw {@link InvalidMethodArgument} if invalid.
	 * <p>
	 * Password has to be:
	 * <ul>
	 * <li>not null</li>
	 * </ul>
	 * </p>
	 * 
	 * @param password
	 * @throws IllegalArgumentException
	 */
	private void validatePasswordArgument(final String password) throws IllegalArgumentException {
		if (password == null) {
			throw new IllegalArgumentException("Password can't be null");
		}
	}

	/**
	 * Validate username and throw {@link InvalidMethodArgument} if invalid.
	 * Username has to be:
	 * <ul>
	 * <li>not null</li>
	 * <li>non empty</li>
	 * </ul>
	 * 
	 * @param username
	 * @throws IllegalArgumentException
	 */
	private void validateUsernameArgument(final String username) throws IllegalArgumentException {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("Username can't be null");
		}
	}

	private User getUserFromRepository(final String username) throws UserNotFoundException {
		final User user = userRepository.getUser(username);
		if (user == null) {
			throw new UserNotFoundException(String.format("User %s not found", username));
		}
		return user;
	}

	private void validatePassword(final String password, final User user) throws UserBadCredentialsException {
		if (!isPasswordCorrect(user, password)) {
			throw new UserBadCredentialsException("Wrong password");
		}
	}

	private boolean isPasswordCorrect(final User user, final String password) {
		final PasswordGenerator passwordGenerator = new PasswordGenerator(password, user.getSalt());
		return passwordGenerator.isValidHash(user.getPasswordHash());
	}

	private final UsersDao userRepository;
}
