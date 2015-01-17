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
 * Integration test for adding new user.
 * 
 * @author mmiedzinski
 */
public class AddUserItTest extends BaseItTest {

	@Before
	public void setUp() {
		usersDao = dataAuthenticationInjector.getUserRepository();
	}

	@Test
	public void shouldInsertPatient() throws Exception {
		// given
		final UserDiff userDiff = UserDiffBuilder.get().username("testUsername123").firstName("name")
				.surname("surname").password("q1w2e3r4").build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(userDiff);

		// when
		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// then
		final UserDto userDto = getUserDtoFromRsponse(response);
		final User user = usersDao.getUser(userDto.getUsername());
		assertThat(user.getFirstName()).isEqualTo(userDto.getFirstName());
		assertThat(user.getSurname()).isEqualTo(userDto.getSurname());
	}

	@Test
	public void shouldAuthenticateNewUser() throws Exception {
		// given
		final String username = "usernameTesthash76";
		final String password = "password2343";
		final UserDiff userDiff = UserDiffBuilder.get().username(username).firstName("name").surname("surname")
				.password(password).build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(userDiff);
		getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// when
		final byte[] response = getMockMvc()
				.perform(
						post(AuthenticationRestDescriptor.getAuthenticateRestUrl(username)).contentType(
								APPLICATION_JSON_UTF8).body(convertObjectToJsonBytes(password))).andReturn()
				.getResponse().getContentAsByteArray();

		// then
		final UserDto userDto = getUserDtoFromRsponse(response);
		assertThat(userDto).isNotNull();
	}

	private MockHttpServletRequestBuilder getRequestBuilder(final UserDiff userDiff) throws IOException {
		return post(AuthenticationRestDescriptor.getAddUserRestUrl()).contentType(APPLICATION_JSON_UTF8).body(
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
