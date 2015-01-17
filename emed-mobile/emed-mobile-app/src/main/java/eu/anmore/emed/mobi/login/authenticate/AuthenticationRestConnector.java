package eu.anmore.emed.mobi.login.authenticate;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.inject.Inject;

import eu.anmore.emed.authentication.Authentication;
import eu.anmore.emed.authentication.AuthenticationRestDescriptor;
import eu.anmore.emed.authentication.User;
import eu.anmore.emed.authentication.UserBadCredentialsException;
import eu.anmore.emed.authentication.UserDiff;
import eu.anmore.emed.authentication.UserDto;
import eu.anmore.emed.authentication.UserNotFoundException;
import eu.anmore.emed.mobi.ApplicationConfigurationFacade;

/**
 * Authentication module REST connector.
 * 
 * @author Grzegorz Lipecki
 */
public class AuthenticationRestConnector implements Authentication {

	@Inject
	public AuthenticationRestConnector(final ApplicationConfigurationFacade applicationState) {
		initRestTemplate();
		this.applicationState = applicationState;
	}

	@Override
	public User authenticate(final String username, final String password) throws UserNotFoundException,
			UserBadCredentialsException {
		final String authenticateUrl = String.format("%s%s%s", applicationState.getServerAddress(),
				AuthenticationRestDescriptor.ROOT_URI, AuthenticationRestDescriptor.AUTHENTICATE_URL_PATTERN);

		try {
			return restTemplate.postForObject(authenticateUrl, password, UserDto.class, username).asUser();
		} catch (final HttpClientErrorException httpClientErrorException) {
			if (httpClientErrorException.getStatusCode() == HttpStatus
					.valueOf(AuthenticationRestDescriptor.INVALID_CREDENTIALS)) {
				throw new UserBadCredentialsException("wrong credentials");
			}
			throw httpClientErrorException;
		}
	}

	@Override
	public List<User> getUsers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public User addUser(final UserDiff userDiff) {
		throw new UnsupportedOperationException();
	}

	@Override
	public User editUser(final UserDiff userDiff) throws UserNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isUnique(final String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean blockUser(final String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unblockUser(final String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPermission(final String username, final String permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePermission(final String username, final String permission) {
		// TODO Auto-generated method stub
		return false;
	}

	private void initRestTemplate() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().clear();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(1000);
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(1000);
	}

	private final ApplicationConfigurationFacade applicationState;

	private RestTemplate restTemplate;

}
