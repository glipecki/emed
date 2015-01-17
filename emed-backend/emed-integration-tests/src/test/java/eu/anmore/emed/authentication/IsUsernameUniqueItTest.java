package eu.anmore.emed.authentication;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;

/**
 * Tests for check that username is unique.
 * 
 * @author mmiedzinski
 */
public class IsUsernameUniqueItTest extends BaseItTest {

	@Test
	public void shouldReturnTrue() throws Exception {
		// given
		final String uniqueUsername = "uniqueUsername";

		// when
		final MockHttpServletRequestBuilder serviceUrl = get(AuthenticationRestDescriptor
				.getIsUniqueRestUrl(uniqueUsername));
		final String serviceResult = getMockMvc().perform(serviceUrl).andReturn().getResponse().getContentAsString();

		// then
		assertThat(Boolean.valueOf(serviceResult)).isTrue();
	}
}
