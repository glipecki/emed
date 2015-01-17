package eu.anmore.emed.mobi.login;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiPlaceManager;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask.AuthenticateAction;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask.AuthenticateResult;
import eu.anmore.emed.mobi.user.User;
import eu.anmore.mvpdroid.async.TaskCallback;

@SuppressWarnings("unchecked")
@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

	@Before
	public void setup() {
		viewMock = mock(LoginPresenter.LoginView.class);
		placeManagerMock = mock(MobiPlaceManager.class);
		taskRunnerMock = mock(MobiTaskRunner.class);
		configurationMock = mock(ApplicationConfigurationFacade.class);
		recentUsersListAdapterMock = mock(RecentUsersListAdapter.class);
		applicationStateMock = mock(ApplicationState.class);

		presenter = new LoginPresenter(viewMock, placeManagerMock, taskRunnerMock, configurationMock,
				applicationStateMock, recentUsersListAdapterMock);
	}

	@Test
	public void shouldAddLoginButtonClickListener() {
		// given

		// when
		presenter.onCreate(null);

		// then
		verify(viewMock, times(1)).setLoginButtonClickHandler(any(LoginPresenter.LoginButtonClickHandler.class));
	}

	@Test
	public void shouldClearValidationErrorsOnInit() {
		// given

		// when
		presenter.onCreate(null);

		// then
		verify(viewMock, times(1)).clearUsernameErrorMessage();
		verify(viewMock, times(1)).clearPasswordErrorMessage();
	}

	@Test
	public void shouldAskForCredentialsAtLoginButtonClick() {
		// given

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock).getUsername();
		verify(viewMock).getPassword();
	}

	@Test
	public void shouldValidateValidCredentials() {
		// given
		final String username = "test-user";
		final String password = "test-password";

		mockViewCredentialsData(username, password);
		mockAuthenticateSuccessCallbackWithResult(true);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock, never()).setUsernameError(anyInt());
		verify(viewMock, never()).setPasswordError(anyInt());
	}

	@Test
	public void shouldShowErrorOnEmptyUsername() {
		// given
		final String username = "";
		final String password = "test-password";
		final int errorMessage = R.string.empty_username_error;

		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock, times(1)).setUsernameError(errorMessage);
		verify(viewMock, never()).setPasswordError(anyInt());
	}

	@Test
	public void shouldShowErrorOnEmptyPassword() {
		// given
		final String username = "test-user";
		final String password = "";
		final int errorMessage = R.string.empty_password_error;

		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock, times(1)).setPasswordError(errorMessage);
		verify(viewMock, never()).setUsernameError(anyInt());
	}

	@Test
	public void shouldTryToAuthenticateUserAtValidCredentials() {
		// given
		final String username = "test-user";
		final String password = "test-password";
		final AuthenticateAction authenticateAction = new AuthenticateAction(username, password);

		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(taskRunnerMock).executeWithRetry(refEq(authenticateAction), any(TaskCallback.class));
	}

	@Test
	public void shouldntAuthenticateOnEmptyUsernameError() {
		// given
		final String username = "";
		final String password = "test-password";

		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(taskRunnerMock, never()).execute(any(AuthenticateAction.class), any(TaskCallback.class));
	}

	@Test
	public void shouldntAuthenticateOnEmptyPasswordError() {
		// given
		final String username = "test-user";
		final String password = "";

		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(taskRunnerMock, never()).execute(any(AuthenticateAction.class), any(TaskCallback.class));
	}

	@Test
	public void sohuldShowErrorAtWrongAuthentication() {
		// given
		final String username = "test-user";
		final String password = "test-password";
		mockViewCredentialsData(username, password);
		mockAuthenticateSuccessCallbackWithResult(false);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock, times(1)).setUsernameError(R.string.wrong_user_credentials);
	}

	@Test
	public void shouldClearValidationErrorsOnLoginClick() {
		// given
		final String username = "test";
		final String password = "test";
		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock, times(1)).clearUsernameErrorMessage();
		verify(viewMock, times(1)).clearPasswordErrorMessage();
	}

	@Test
	public void shouldClearValidationErrorBeforeErrorsOnReClickLogin() {
		// given
		final String username = "";
		final String password = "";
		mockViewCredentialsData(username, password);
		mockAuthenticateSuccessCallbackWithResult(false);

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		final InOrder order = inOrder(viewMock);
		order.verify(viewMock).clearUsernameErrorMessage();
		order.verify(viewMock).setUsernameError(anyInt());
		order.verify(viewMock).clearPasswordErrorMessage();
		order.verify(viewMock).setPasswordError(anyInt());
	}

	@Test
	public void shouldAddCredentialsChangeListenerToUsername() {
		// given

		// when
		presenter.onCreate(null);

		// then
		verify(viewMock, times(1)).addUsernameChangeListener(any(LoginPresenter.UsernameChangedListener.class));
	}

	@Test
	public void shouldAddCredentialsChangeListenerToPassword() {
		// given

		// when
		presenter.onCreate(null);

		// then
		verify(viewMock, times(1)).addPasswordChangeListener(any(LoginPresenter.PasswordChangedListener.class));
	}

	@Test
	public void shouldClearValidationErrorsOnCredentialsChange() {
		// given

		// when
		new LoginPresenter.CredentialsChangedListener(presenter).afterTextChanged(null);

		// then
		verify(viewMock).clearUsernameErrorMessage();
		verify(viewMock).clearPasswordErrorMessage();
	}

	@Test
	public void shouldSimpleValidateOnlyUsernameAtUsernameChanged() {
		// given
		final int errorMessage = R.string.empty_username_error;
		final String username = "";
		final String password = "";
		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.UsernameChangedListener(presenter).afterTextChanged(null);

		// then
		verify(viewMock).setUsernameError(errorMessage);
		verify(viewMock, times(0)).setPasswordError(anyInt());
	}

	@Test
	public void shouldSimpleValidateOnlyPasswordAtPasswordChanged() {
		// given
		final int errorMessage = R.string.empty_password_error;
		final String username = "";
		final String password = "";
		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.PasswordChangedListener(presenter).afterTextChanged(null);

		// then
		verify(viewMock).setPasswordError(errorMessage);
		verify(viewMock, times(0)).setUsernameError(anyInt());
	}

	@Test
	public void shouldClearUsernameErrorsBeforeSimpleValidate() {
		// given
		final String username = "qwe";
		final String password = "";
		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.UsernameChangedListener(presenter).afterTextChanged(null);

		// then
		verify(viewMock).clearUsernameErrorMessage();
		verify(viewMock, times(0)).setUsernameError(anyInt());
		verify(viewMock, times(0)).setPasswordError(anyInt());
	}

	@Test
	public void shouldClearPasswordErrorsBeforeSimpleValidate() {
		// given
		final String username = "";
		final String password = "qwe";
		mockViewCredentialsData(username, password);

		// when
		new LoginPresenter.PasswordChangedListener(presenter).afterTextChanged(null);

		// then
		verify(viewMock).clearPasswordErrorMessage();
		verify(viewMock, times(0)).setPasswordError(anyInt());
		verify(viewMock, times(0)).setUsernameError(anyInt());
	}

	@Test
	public void shouldShowConnectionError() {
		// given
		final String username = "test-user";
		final String password = "test-password";

		mockViewCredentialsData(username, password);
		mockAuthenticateFailureCallbackWithResult(new RuntimeException());

		// when
		new LoginPresenter.LoginButtonClickHandler(presenter).onClick(null);

		// then
		verify(viewMock, times(1)).showConnectionError(R.string.connection_error);
	}

	private void mockViewCredentialsData(final String username, final String password) {
		when(viewMock.getUsername()).thenReturn(username);
		when(viewMock.getPassword()).thenReturn(password);
	}

	private void mockAuthenticateSuccessCallbackWithResult(final boolean result) {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(final InvocationOnMock invocation) throws Throwable {
				new LoginPresenter.AuthenticateCallback(presenter)
						.onSuccess(new AuthenticateResult(result, new User()));
				return null;
			}

		}).when(taskRunnerMock).executeWithRetry(any(AuthenticateAction.class),
				any(LoginPresenter.AuthenticateCallback.class));
	}

	private void mockAuthenticateFailureCallbackWithResult(final Throwable caught) {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(final InvocationOnMock invocation) throws Throwable {
				new LoginPresenter.AuthenticateCallback(presenter).onFailure(caught);
				return null;
			}

		}).when(taskRunnerMock).executeWithRetry(any(AuthenticateAction.class),
				any(LoginPresenter.AuthenticateCallback.class));
	}

	private LoginPresenter.LoginView viewMock;

	private LoginPresenter presenter;

	private MobiPlaceManager placeManagerMock;

	private MobiTaskRunner taskRunnerMock;

	private ApplicationConfigurationFacade configurationMock;

	private RecentUsersListAdapter recentUsersListAdapterMock;

	private ApplicationState applicationStateMock;

}
