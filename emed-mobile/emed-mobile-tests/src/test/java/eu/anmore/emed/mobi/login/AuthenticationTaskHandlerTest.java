package eu.anmore.emed.mobi.login;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.anmore.emed.authentication.User;
import eu.anmore.emed.authentication.UserBadCredentialsException;
import eu.anmore.emed.authentication.UserDto;
import eu.anmore.emed.authentication.UserNotFoundException;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask.AuthenticateAction;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask.AuthenticateResult;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTaskHandler;
import eu.anmore.emed.mobi.login.authenticate.AuthenticationRestConnector;

public class AuthenticationTaskHandlerTest {

	@Before
	public void setup() {
		authenticationServiceMock = mock(AuthenticationRestConnector.class);

		handler = new AuthenticateTaskHandler(authenticationServiceMock);
	}

	@Test
	public void shouldCallAuthenticationService() throws UserNotFoundException, UserBadCredentialsException {
		// given
		final String username = "username";
		final String password = "password";
		final UserDto userDto = new UserDto();
		final String firstName = "first-name";
		userDto.setFirstName(firstName);
		final User user = userDto.asUser();
		when(authenticationServiceMock.authenticate(username, password)).thenReturn(user);

		// when
		final AuthenticateResult result = handler.handle(new AuthenticateAction(username, password));

		// then
		assertEquals(result.getUser().getFirstName(), firstName);
	}

	@Test
	public void shouldHandleUserBadCredentialsException() throws UserNotFoundException, UserBadCredentialsException {
		// given
		final String username = "username";
		final String password = "password";
		when(authenticationServiceMock.authenticate(username, password)).thenThrow(new UserBadCredentialsException(""));

		// when
		final AuthenticateResult result = handler.handle(new AuthenticateAction(username, password));

		// then
		assertEquals(false, result.isAuthenticated());
	}

	@Test
	public void shouldHandleUserNotFoundException() throws UserNotFoundException, UserBadCredentialsException {
		// given
		final String username = "username";
		final String password = "password";
		when(authenticationServiceMock.authenticate(username, password)).thenThrow(new UserNotFoundException(""));

		// when
		final AuthenticateResult result = handler.handle(new AuthenticateAction(username, password));

		// then
		assertEquals(false, result.isAuthenticated());
	}

	@Test(expected = RuntimeException.class)
	public void shouldHandleRuntimeException() throws UserNotFoundException, UserBadCredentialsException {
		// given
		final String username = "username";
		final String password = "password";
		when(authenticationServiceMock.authenticate(username, password)).thenThrow(new RuntimeException(""));

		// when
		handler.handle(new AuthenticateAction(username, password));

		// then
	}

	private AuthenticationRestConnector authenticationServiceMock;

	private AuthenticateTaskHandler handler;

}
