package eu.anmore.emed.authentication;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for {@link AuthenticationBeanImpl}.
 * 
 * @author mmiedzinski
 */
public class AuthenticationBeanTest {

	@Before
	public void setup() {
		initAuthenticationBean();
	}

	@Test
	public void shouldAuthenticateUser() throws UserNotFoundException, UserBadCredentialsException {
		logger.trace(">>> shouldAuthenticateUser");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		// when
		final User authenticationResult = authenticationBean.authenticate(TestCredentials.username,
				TestCredentials.password);

		// then
		assertEquals("Authentication result user should have username equal to test data username",
				TestCredentials.username, authenticationResult.getUsername());

		logger.trace("<<< shouldAuthenticateUser");
	}

	@Test(expected = UserNotFoundException.class)
	public void shouldThrowExceptionIfRepositoryReturnsNull() throws UserNotFoundException, UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionIfRepositoryReturnsNull");

		try {
			// when
			authenticationBean.authenticate(TestCredentials.wrongUsername, TestCredentials.wrongPassword);
		} finally {
			logger.trace("<<< shouldThrowExceptionIfRepositoryReturnsNull");
		}
	}

	@Test(expected = UserNotFoundException.class)
	public void shouldThrowExceptionWhileValidatingNonExistingUser() throws UserNotFoundException,
			UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionWhileValidatingNonExistingUser");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		try {
			// when
			authenticationBean.authenticate(TestCredentials.wrongUsername, TestCredentials.wrongPassword);
		} finally {
			logger.trace("<<< shouldThrowExceptionWhileValidatingNonExistingUser");
		}
	}

	@Test(expected = UserBadCredentialsException.class)
	public void shouldThrowExceptionWhileValidatingWithWrongPassword() throws UserNotFoundException,
			UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionWhileValidatingWithWrongPassword");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		try {
			// when
			authenticationBean.authenticate(TestCredentials.username, TestCredentials.wrongPassword);
		} finally {
			logger.trace("<<< shouldThrowExceptionWhileValidatingWithWrongPassword");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForWrongArgumentsNullUser() throws UserNotFoundException,
			UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionWhileValidatingWithWrongPassword");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		try {
			// when
			authenticationBean.authenticate(null, TestCredentials.wrongPassword);
		} finally {
			logger.trace("<<< shouldThrowExceptionWhileValidatingWithWrongPassword");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForWrongArgumentsNullPassword() throws UserNotFoundException,
			UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionForWrongArgumentsNullPassword");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		try {
			// when
			authenticationBean.authenticate(TestCredentials.username, null);
		} finally {
			logger.trace("<<< shouldThrowExceptionForWrongArgumentsNullPassword");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForWrongArgumentsNullUserAndPassword() throws UserNotFoundException,
			UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionForWrongArgumentsNullUserAndPassword");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		try {
			// when
			authenticationBean.authenticate(null, null);
		} finally {
			logger.trace("<<< shouldThrowExceptionForWrongArgumentsNullUserAndPassword");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForWrongArgumentsBlankUsername() throws UserNotFoundException,
			UserBadCredentialsException {
		logger.trace(">>> shouldThrowExceptionForWrongArgumentsBlankUsername");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUser());

		try {
			// when
			authenticationBean.authenticate(EMPTY_STRING, TestCredentials.password);
		} finally {
			logger.trace("<<< shouldThrowExceptionForWrongArgumentsBlankUsername");
		}
	}

	@Test
	public void shouldAuthenticateUserIfNoSalt() throws UserNotFoundException, UserBadCredentialsException {
		logger.trace(">>> shouldThrowEshouldAuthenticateUserIfNoSaltxceptionWhileRepositoryReturnsNull");

		// given
		Mockito.when(userRepository.getUser(TestCredentials.username)).thenReturn(TestCredentials.getUserWithoutSalt());

		try {
			// when
			authenticationBean.authenticate(TestCredentials.username, TestCredentials.password);
		} finally {
			logger.trace("<<< shouldAuthenticateUserIfNoSalt");
		}
	}

	private static final String EMPTY_STRING = "";

	private static Logger logger = LoggerFactory.getLogger(AuthenticationBeanTest.class);

	private static class TestCredentials {
		static User getUser() {
			return new User(username, "nn", "nn", new ArrayList<String>(), passwordHash, salt, true);
		}

		static User getUserWithoutSalt() {
			return new User(username, "nn", "nn", new ArrayList<String>(), passwordHashWithoutSalt, null, true);
		}

		static String username = "test-user";
		static String password = "password";
		static String salt = "salt";
		static String passwordHash = "c88e9c67041a74e0357befdff93f87dde0904214";
		static String passwordHashWithoutSalt = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";

		static String wrongUsername = "wrong-username";

		static String wrongPassword = "wrong-password";
	}

	private void initAuthenticationBean() {
		authenticationBean = new AuthenticationBeanImpl(initUserRepository());
	}

	private UsersDao initUserRepository() {
		userRepository = Mockito.mock(UsersDao.class);
		return userRepository;
	}

	private AuthenticationBean authenticationBean;

	private UsersDao userRepository;
}
