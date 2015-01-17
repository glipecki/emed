package eu.anmore.emed.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link UsersDao}.
 * 
 * @author Grzegorz Lipecki
 */
public class UsersDaoImpl implements UsersDao {

	public UsersDaoImpl(final UsersMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	@Transactional
	public User getUser(final String username) throws UserNotFoundException {
		final List<UserEntity> users = userMapper.queryUsers(UsersQuery.getBuilder().usernameEquals(username).build());
		if (users.size() == 0) {
			throw new UserNotFoundException("User not found");
		}
		return users.get(0).getUser();
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		final List<UserEntity> users = userMapper.queryUsers(UsersQuery.getBuilder().build());
		final List<User> result = new ArrayList<>();
		for (final UserEntity userEntity : users) {
			result.add(userEntity.getUser());
		}
		return result;
	}

	@Override
	@Transactional
	public User insert(final UserDiff userDiff) {
		final UserEntity userEntity = getUserEntity(userDiff);
		userMapper.insert(userEntity);
		return userEntity.getUser();
	}

	@Override
	@Transactional
	public void edit(final UserDiff userDiff) {
		userMapper.edit(userDiff);
	}

	@Override
	@Transactional
	public void blockUser(final String username) {
		userMapper.blockUser(username);
	}

	@Override
	@Transactional
	public void unblockUser(final String username) {
		userMapper.unblockUser(username);
	}

	@Override
	@Transactional
	public void addPermission(final String username, final String permission) {
		final int userId = userMapper.getUserId(username);
		final int permissionId = userMapper.getPermissionId(permission);
		userMapper.addPermission(new UserPermissionEntity(userId, permissionId));
	}

	@Override
	@Transactional
	public void removePermission(final String username, final String permission) {
		final int userId = userMapper.getUserId(username);
		final int permissionId = userMapper.getPermissionId(permission);
		userMapper.removePermission(new UserPermissionEntity(userId, permissionId));
	}

	/** Just to fulfill CGLIB requirements */
	UsersDaoImpl() {
		userMapper = null;
	}

	private UserEntity getUserEntity(final UserDiff userDiff) {
		return new UserEntity(userDiff.firstName.getNewValue(), userDiff.getSurname().getNewValue(),
				userDiff.getUsername(), userDiff.getPasswordHash().getNewValue(), userDiff.getSalt().getNewValue(),
				true);
	}

	private final UsersMapper userMapper;
}
