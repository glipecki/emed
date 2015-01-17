package eu.anmore.emed.authentication;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;

/**
 * Integration test for edit user.
 * 
 * @author mmiedzinski
 */
public class EditUserItTest extends BaseItTest {

	@Before
	public void setUp() {
		usersDao = dataAuthenticationInjector.getUserRepository();
	}

	@Test
	public void shouldEditPatient() throws Exception {
		// given
		final String newFirstname = "newFirstanme";
		final UserDiff userDiff = UserDiffBuilder.get(addUser("32432524")).firstName(newFirstname).build();

		// when
		final MockHttpServletRequestBuilder requestBuilder = getEditUserRequestBuilder(userDiff);
		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// then
		final UserDto userDtoFromRsponse = getUserDtoFromRsponse(response);
		assertThat(userDtoFromRsponse).isNotNull();
	}

	@Test
	public void shouldAuthenticateEditedUser() throws Exception {
		// given
		final String newPassword = "newPassword";
		final UserDiff userDiff = UserDiffBuilder.get(addUser("gfgsgsd")).password(newPassword).build();
		final MockHttpServletRequestBuilder requestBuilder = getEditUserRequestBuilder(userDiff);
		getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// when
		final byte[] response = getMockMvc()
				.perform(
						post(AuthenticationRestDescriptor.getAuthenticateRestUrl("gfgsgsd")).contentType(
								APPLICATION_JSON_UTF8).body(convertObjectToJsonBytes(newPassword))).andReturn()
				.getResponse().getContentAsByteArray();

		// then
		final UserDto userDto = getUserDtoFromRsponse(response);
		assertThat(userDto).isNotNull();
	}

	private User addUser(final String username) throws Exception {
		final UserDiff userDiff = UserDiffBuilder.get().username(username).firstName("name").surname("surname")
				.password("q1w2e3r4").build();
		final MockHttpServletRequestBuilder requestBuilder = getAddUserRequestBuilder(userDiff);
		getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();
		return usersDao.getUser(username);
	}

	private MockHttpServletRequestBuilder getAddUserRequestBuilder(final UserDiff userDiff) throws IOException {
		return post(AuthenticationRestDescriptor.getAddUserRestUrl()).contentType(APPLICATION_JSON_UTF8).body(
				convertObjectToJsonBytes(userDiff));
	}

	private MockHttpServletRequestBuilder getEditUserRequestBuilder(final UserDiff userDiff) throws IOException {
		return post(AuthenticationRestDescriptor.getEditUserRestUrl()).contentType(APPLICATION_JSON_UTF8).body(
				convertObjectToJsonBytes(userDiff));
	}

	private UserDto getUserDtoFromRsponse(final byte[] response) throws IOException, JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.reader(UserDto.class).readValue(response);
	}

	private UsersDao usersDao;

	@Autowired
	private DataAuthenticationInjector dataAuthenticationInjector;
}
