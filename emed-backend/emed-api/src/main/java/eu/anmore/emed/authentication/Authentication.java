package eu.anmore.emed.authentication;

import java.util.List;

public interface Authentication {

	User authenticate(final String username, final String password) throws UserNotFoundException,
			UserBadCredentialsException;

	/**
	 * Check that username is unique.
	 * 
	 * @param username
	 * @return
	 */
	boolean isUnique(String username);

	/**
	 * Get all user.
	 * 
	 * @return
	 */
	List<User> getUsers();

	/**
	 * Add user into system.
	 * 
	 * @param userDiff
	 * @return
	 */
	User addUser(UserDiff userDiff);

	/**
	 * Edit user.
	 * 
	 * @param userDiff
	 * @return
	 * @throws UserNotFoundException
	 */
	User editUser(UserDiff userDiff) throws UserNotFoundException;

	/**
	 * Block user.
	 * 
	 * @param username
	 */
	boolean blockUser(String username);

	/**
	 * Unblock user.
	 * 
	 * @param username
	 */
	boolean unblockUser(String username);

	boolean addPermission(String username, String permission);

	boolean removePermission(String username, String permission);
}
