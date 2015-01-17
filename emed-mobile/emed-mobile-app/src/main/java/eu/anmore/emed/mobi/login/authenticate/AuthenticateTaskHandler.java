package eu.anmore.emed.mobi.login.authenticate;

import com.google.inject.Inject;

import eu.anmore.emed.authentication.Authentication;
import eu.anmore.emed.authentication.UserBadCredentialsException;
import eu.anmore.emed.authentication.UserNotFoundException;
import eu.anmore.emed.mobi.user.User;
import eu.anmore.mvpdroid.async.TaskActionHandler;
import eu.anmore.mvpdroid.logger.LogCatLogger;

/**
 * Authenticate task action handler.
 * 
 * @author Grzegorz Lipecki
 */
public class AuthenticateTaskHandler implements
		TaskActionHandler<AuthenticateTask.AuthenticateAction, AuthenticateTask.AuthenticateResult> {

	@Inject
	public AuthenticateTaskHandler(final AuthenticationRestConnector authenticationRestConnector) {
		this.authenticationService = authenticationRestConnector;
	}

	@Override
	public AuthenticateTask.AuthenticateResult handle(final AuthenticateTask.AuthenticateAction taskAction) {
		try {
			final eu.anmore.emed.authentication.User user = authenticationService.authenticate(
					taskAction.getUsername(), taskAction.getPassword());
			return new AuthenticateTask.AuthenticateResult(true, new User(taskAction.getUsername(),
					user.getFirstName(), user.getSurname()));
		} catch (final UserBadCredentialsException userBadCredentialsException) {
			logger.debug(userBadCredentialsException.getMessage());
			return AuthenticateTask.resultFail();
		} catch (final UserNotFoundException userNotFoundException) {
			logger.debug(userNotFoundException.getMessage());
			return AuthenticateTask.resultFail();
		} catch (final Throwable e) {
			logger.debug("Connection error: {}", e);
			throw new RuntimeException(e);
		}
	}

	private final LogCatLogger logger = LogCatLogger.getLogger(AuthenticateTaskHandler.class);

	private final Authentication authenticationService;

}
