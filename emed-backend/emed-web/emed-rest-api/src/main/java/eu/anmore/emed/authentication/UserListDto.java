package eu.anmore.emed.authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mmiedzinski
 */
public class UserListDto {

	/** */
	public static UserListDto valueOf(final List<User> users) {
		final UserListDto userListDto = new UserListDto();
		final List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (final User user : users) {
			userDtoList.add(UserDto.valueOf(user));
		}
		userListDto.setUsers(userDtoList);
		return userListDto;
	}

	/** */
	public List<UserDto> getUsers() {
		return users;
	}

	/** */
	public void setUsers(final List<UserDto> users) {
		this.users = users;
	}

	/** */
	public List<User> asUserList() {
		final List<User> usersToReturn = new ArrayList<User>();
		for (final UserDto user : users) {
			usersToReturn.add(user.asUser());
		}
		return usersToReturn;
	}

	private List<UserDto> users;
}
