package eu.anmore.emed.authentication;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;

/**
 * @author mmiedzinski
 */
public class AddPermissionItTest extends BaseItTest {

	@Before
	public void setUp() {
		usersDao = dataAuthenticationInjector.getUserRepository();
	}

	@Test
	public void shouldAddPermission() throws Exception {
		// given
		final String knownPermission = "ADMINISTRATOR";
		final String knownUsername = "glipecki";

		// when
		final MockHttpServletRequestBuilder serviceUrl = get(AuthenticationRestDescriptor.getAddPermissionRestUrl(
				knownUsername, knownPermission));
		getMockMvc().perform(serviceUrl);

		// then
		assertThat(usersDao.getUser(knownUsername).getPermissions()).contains(knownPermission);
	}

	private UsersDao usersDao;

	@Autowired
	private DataAuthenticationInjector dataAuthenticationInjector;
}
