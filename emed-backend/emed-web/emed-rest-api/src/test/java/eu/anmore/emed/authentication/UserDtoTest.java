package eu.anmore.emed.authentication;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test class for {@link UserDto}.
 * 
 * @author mmiedzinski
 */
public class UserDtoTest {

	@Test
	public void shouldCreateUserDtoFromUser() {
		// given
		final User user = new User(USERNAME, "nn", "nn", new ArrayList<String>(), true);

		// when
		final UserDto result = UserDto.valueOf(user);

		// then
		Assert.assertNotNull("Result shouldn't be null.", result);
		Assert.assertEquals("Incorrect value of username.", user.getUsername(), result.getUsername());
		Assert.assertEquals("Incorrect value of permissions.", user.getPermissions(), result.getPermissions());
	}

	@Test
	public void shouldCreateUserFromDto() {
		// given
		final UserDto userDto = prepareUserDto();

		// when
		final User result = userDto.asUser();

		// then
		Assert.assertNotNull("Result shouldn't be null.", result);
		Assert.assertEquals("Incorrect value of username.", userDto.getUsername(), result.getUsername());
		Assert.assertEquals("Incorrect value of permissions.", userDto.getPermissions(), result.getPermissions());
	}

	private static final String USERNAME = "username";

	private UserDto prepareUserDto() {
		final UserDto userDto = new UserDto();
		userDto.setPermissions(new ArrayList<String>());
		userDto.setUsername(USERNAME);
		return userDto;
	}
}
