package eu.anmore.emed.authentication;

import java.util.List;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import eu.anmore.emed.BaseServiceRestConnector;

/**
 * @author Grzegorz Lipecki
 */
public class AuthenticationServiceRestConnector extends BaseServiceRestConnector implements AuthenticationConnector {

	public AuthenticationServiceRestConnector(final String serverUri) {
		this.serverUri = normalizeUri(serverUri);
		this.authenticateUrl = prepareAuthenticateUrl();
	}

	public AuthenticationServiceRestConnector(final String serverUri, final String username, final String password) {
		super(username, password);
		this.serverUri = normalizeUri(serverUri);
		this.authenticateUrl = prepareAuthenticateUrl();
	}

	public void setRestTemplate(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public User authenticate(final String username, final String password) {
		UserDto authenticatedUser = null;
		try {
			authenticatedUser = authenticateRestCall(username, password);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
		}
		return authenticatedUser.asUser();
	}

	@Override
	public List<User> getUsers() {
		UserListDto users = null;
		try {
			final String uri = serverUri + AuthenticationRestDescriptor.getUserListRestUrl();
			users = restTemplate.getForObject(uri, UserListDto.class);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
		}
		return users.asUserList();
	}

	@Override
	public User addUser(final UserDiff userDiff) {
		final String uri = String.format("%s%s", serverUri, AuthenticationRestDescriptor.getAddUserRestUrl());
		final UserDto userDto = restTemplate.postForObject(uri, userDiff, UserDto.class);
		return userDto.asUser();
	}

	@Override
	public User editUser(final UserDiff userDiff) throws UserNotFoundException {
		final String uri = String.format("%s%s", serverUri, AuthenticationRestDescriptor.getEditUserRestUrl());
		final UserDto userDto = restTemplate.postForObject(uri, userDiff, UserDto.class);
		return userDto.asUser();
	}

	@Override
	public boolean isUnique(final String username) {
		try {
			final String uri = serverUri + AuthenticationRestDescriptor.getIsUniqueRestUrl(username);
			return restTemplate.getForObject(uri, Boolean.class);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
			return false;
		}
	}

	@Override
	public boolean blockUser(final String username) {
		try {
			final String uri = serverUri + AuthenticationRestDescriptor.getBlockUserRestUrl(username);
			return restTemplate.getForObject(uri, Boolean.class);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
			return false;
		}
	}

	@Override
	public boolean unblockUser(final String username) {
		try {
			final String uri = serverUri + AuthenticationRestDescriptor.getUnblockUserRestUrl(username);
			return restTemplate.getForObject(uri, Boolean.class);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
			return false;
		}
	}

	@Override
	public boolean addPermission(final String username, final String permission) {
		try {
			final String uri = serverUri + AuthenticationRestDescriptor.getAddPermissionRestUrl(username, permission);
			return restTemplate.getForObject(uri, Boolean.class);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
			return false;
		}
	}

	@Override
	public boolean removePermission(final String username, final String permission) {
		try {
			final String uri = serverUri
					+ AuthenticationRestDescriptor.getRemovePermissionRestUrl(username, permission);
			return restTemplate.getForObject(uri, Boolean.class);
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
			return false;
		}
	}

	/** {serverUri}/{module}/{method} */
	private static final String CONNECTION_PATTERN = "%s/%s/%s";

	private static final String BACKSLASH = "/";

	private UserDto authenticateRestCall(final String username, final String password) {
		return restTemplate.postForObject(authenticateUrl, password, UserDto.class, username);
	}

	private String prepareAuthenticateUrl() {
		return String.format(CONNECTION_PATTERN, this.serverUri, normalizeUri(AuthenticationRestDescriptor.ROOT_URI),
				normalizeUri(AuthenticationRestDescriptor.AUTHENTICATE_URL_PATTERN));
	}

	/**
	 * Normalized URI wont starts or ends with "/".
	 * 
	 * @param uri
	 * @return
	 */
	private String normalizeUri(final String uri) {
		return removeEndBackslashIfExists(removePrecedentBackshlashIfExists(uri));
	}

	private String removeEndBackslashIfExists(String uri) {
		if (uri.endsWith(BACKSLASH)) {
			uri = uri.substring(0, uri.length() - 1);
		}
		return uri;
	}

	private String removePrecedentBackshlashIfExists(String uri) {
		if (uri.startsWith(BACKSLASH)) {
			uri = uri.substring(1);
		}
		return uri;
	}

	private final String serverUri;

	private final String authenticateUrl;
}
