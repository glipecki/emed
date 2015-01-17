package eu.anmore.emed.authentication;

import java.util.List;

/**
 * User SQL mapper.
 * 
 * @author Grzegorz Lipecki
 */
public interface UsersMapper {

	List<UserEntity> queryUsers(UsersQuery query);

	int getUserId(String username);

	int getPermissionId(String permission);

	void insert(UserEntity userEntity);

	void edit(UserDiff userDiff);

	/**
	 * Block user.
	 * 
	 * @param username
	 */
	void blockUser(String username);

	/**
	 * Unblock user.
	 * 
	 * @param username
	 */
	void unblockUser(String username);

	void addPermission(UserPermissionEntity userPermissionEntity);

	void removePermission(UserPermissionEntity userPermissionEntity);
}
