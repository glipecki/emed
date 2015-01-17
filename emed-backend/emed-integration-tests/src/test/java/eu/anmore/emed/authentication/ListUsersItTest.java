package eu.anmore.emed.authentication;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;

/**
 * Tests for listing users.
 * 
 * @author mmiedzinski
 */
public class ListUsersItTest extends BaseItTest {

	@Test
	public void shouldListAllUsers() throws Exception {
		// given

		// when
		final MockHttpServletRequestBuilder serviceUrl = get(AuthenticationRestDescriptor.getUserListRestUrl());
		final String serviceResult = getMockMvc().perform(serviceUrl).andReturn().getResponse().getContentAsString();

		// then
		assertThat(serviceResult).isNotEmpty();
	}
}
