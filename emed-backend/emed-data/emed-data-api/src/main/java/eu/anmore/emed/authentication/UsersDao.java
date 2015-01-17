package eu.anmore.emed.authentication;

import java.util.List;

/**
 * Users repository.
 * 
 * @author Grzegorz Lipecki
 */
public interface UsersDao {

	User getUser(String username) throws UserNotFoundException;

	/**
	 * Get all users.
	 * 
	 * @return
	 */
	List<User> getUsers();

	/**
	 * Insert user into system.
	 * 
	 * @param userDiff
	 * @return
	 */
	User insert(UserDiff userDiff);

	/**
	 * Edit user.
	 * 
	 * @param userDiff
	 * @return
	 */
	void edit(UserDiff userDiff);

	/**
	 * Block user.
	 * 
	 * @param username
	 * @return
	 */
	void blockUser(String username);

	/**
	 * Unblock user.
	 * 
	 * @param username
	 * @return
	 */
	void unblockUser(String username);

	void addPermission(String username, String permission);

	void removePermission(String username, String permission);
}
