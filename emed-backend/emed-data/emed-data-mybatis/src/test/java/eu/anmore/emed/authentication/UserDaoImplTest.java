package eu.anmore.emed.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link UsersDaoImpl}.
 *
 * @author mmiedzinski
 * @author Grzegorz Lipecki
 */
public class UserDaoImplTest {

	@Before
	public void setUp() {
		userMapperMock = mock(UsersMapper.class);
		userDao = new UsersDaoImpl(userMapperMock);
	}

	@Test
	public void shouldGetUserByUserName() throws UserNotFoundException {
		// given
		final UserEntity userEntity = getUserEntity();
		when(userMapperMock.queryUsers(any(UsersQuery.class))).thenReturn(Arrays.asList(userEntity));

		// when
		final User result = userDao.getUser(USERNAME);

		// then
		assertNotNull(result);
		assertEquals(userEntity.getUsername(), result.getUsername());
	}

	@Test(expected = UserNotFoundException.class)
	public void shouldThrowExceptionWhenUserNotFound() throws UserNotFoundException {
		// given
		when(userMapperMock.queryUsers(any(UsersQuery.class))).thenReturn(new ArrayList<UserEntity>());

		// when
		userDao.getUser(USERNAME);

		// then
	}

	private UserEntity getUserEntity() {
		final UserEntity userEntity = new UserEntity();
		userEntity.setUsername(USERNAME);
		return userEntity;
	}

	private UsersDao userDao;

	private static final String USERNAME = "username";

	private UsersMapper userMapperMock;
}
