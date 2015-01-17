package eu.anmore.emed.authentication;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link User}.
 * 
 * @author mmiedzinski
 */
public class UserTest {

	@Test
	public void shouldCreateUserAndFillAllField() {
		// given
		final List<String> permissions = preparePermissionList();

		// when
		final User user = new User(SAMPLE_USERNAME, "nn", "nn", permissions, SAMPLE_PASSWORD_HASH, SAMPLE_SALT, true);

		// then
		Assert.assertEquals("Incorrect value of username.", SAMPLE_USERNAME, user.getUsername());
		Assert.assertEquals("Incorrect value of password hash.", SAMPLE_PASSWORD_HASH, user.getPasswordHash());
		Assert.assertEquals("Incorrect value of salt.", SAMPLE_SALT, user.getSalt());
		Assert.assertEquals("Incorrect value of permissions.", permissions, user.getPermissions());
	}

	@Test
	public void shouldCreateUser() {
		// given
		final List<String> permissions = preparePermissionList();

		// when
		final User user = new User(SAMPLE_USERNAME, "nn", "nn", permissions, true);

		// then
		Assert.assertEquals("Incorrect value of username.", SAMPLE_USERNAME, user.getUsername());
		Assert.assertEquals("Incorrect value of permissions.", permissions, user.getPermissions());
		Assert.assertNull("Incorrect value of password hash.", user.getPasswordHash());
		Assert.assertNull("Incorrect value of salt.", user.getSalt());
	}

	private static final String SAMPLE_SALT = "43634654654654";

	private static final String SAMPLE_PASSWORD_HASH = "763946320743027432";

	private static final String SAMPLE_USERNAME = "username";

	private List<String> preparePermissionList() {
		final String permission = "permission";
		final List<String> permissions = new ArrayList<String>();
		permissions.add(permission);
		permissions.add(permission);
		return permissions;
	}
}
