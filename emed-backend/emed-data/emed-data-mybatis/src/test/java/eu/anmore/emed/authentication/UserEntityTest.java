package eu.anmore.emed.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Test class for {@link UserEntity}.
 *
 * @author mmiedzinski
 * @author Grzegorz Lipecki
 */
public class UserEntityTest {

	@Test
	public void shouldCreateUserObject() {
		// given
		final UserEntity userEntity = getSampleUserEntity();

		// when
		final User user = userEntity.getUser();

		// then
		assertNotNull(user);
		assertEquals(userEntity.getUsername(), user.getUsername());
		assertEquals(userEntity.getPasswordHash(), user.getPasswordHash());
		assertEquals(userEntity.getSalt(), user.getSalt());
		assertEquals(userEntity.getPermissions(), user.getPermissions());
	}

	private UserEntity getSampleUserEntity() {
		final UserEntity userEntity = new UserEntity();
		userEntity.setUsername(SAMPLE_USERNAME);
		userEntity.setPasswordHash(SAMPLE_PASSWORD_HASH);
		userEntity.setSalt(SAMPLE_SALT);
		userEntity.setPermissions(new ArrayList<String>());
		return userEntity;
	}

	private static final String SAMPLE_SALT = "43634654654654";

	private static final String SAMPLE_PASSWORD_HASH = "763946320743027432";

	private static final String SAMPLE_USERNAME = "username";
}
