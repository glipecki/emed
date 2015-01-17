package eu.anmore.emed.mobi.login;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiPlaceManager;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask.AuthenticateResult;
import eu.anmore.emed.mobi.login.credentials.CredentialsValidator;
import eu.anmore.emed.mobi.login.credentials.ValidationError;
import eu.anmore.emed.mobi.user.User;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.handler.listener.TextChangedListener;
import eu.anmore.mvpdroid.presenter.Presenter;
import eu.anmore.mvpdroid.view.View;

/**
 * Login screen presenter.
 * 
 * @author Grzegorz Lipecki
 */
public class LoginPresenter extends Presenter<LoginPresenter.LoginView> {

	@Inject
	public LoginPresenter(final LoginView loginView, final MobiPlaceManager placeManager,
			final MobiTaskRunner taskRunner, final ApplicationConfigurationFacade configuration,
			final ApplicationState applicationState, final RecentUsersListAdapter recentUsersListAdapter) {
		super(loginView);
		this.placeManager = placeManager;
		this.taskRunner = taskRunner;
		this.configuration = configuration;
		this.applicationState = applicationState;
		this.recentUsersListAdapter = recentUsersListAdapter;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu, final MenuInflater menuInflater) {
		if (configuration.isDeveloperMode()) {
			menuInflater.inflate(R.menu.menu, menu);

			final MenuItem changeServerAddress = menu.findItem(R.id.menu_change_server_address);
			changeServerAddress.setOnMenuItemClickListener(new ChangeServerAddress(configuration, getProxy()));

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.applicationState.logout();

		getView().clearUsernameErrorMessage();
		getView().clearPasswordErrorMessage();

		getView().setRecentUsersListAdapter(recentUsersListAdapter);
		getView().setRecentUserClickListener(new RecentUserListItemClickListener(this));

		getView().setLoginButtonClickHandler(new LoginButtonClickHandler(this));
		getView().addUsernameChangeListener(new UsernameChangedListener(this));
		getView().addPasswordChangeListener(new PasswordChangedListener(this));
	}

	public void onRecentUserSelected(final User user) {
		getView().setUsername(user.getUsername());
	}

	public interface LoginView extends View {

		String getUsername();

		String getPassword();

		void setUsername(String string);

		void setRecentUsersListAdapter(ListAdapter recentUsersListAdapter);

		void setRecentUserClickListener(OnItemClickListener recentUserClickListener);

		void setLoginButtonClickHandler(OnClickListener onClickListener);

		void setUsernameError(int errorMessage);

		void setPasswordError(int errorMessage);

		void showConnectionError(int connectionErrorMessage);

		void addUsernameChangeListener(TextWatcher usernameChangeListener);

		void addPasswordChangeListener(TextWatcher passwordChangeListener);

		void clearUsernameErrorMessage();

		void clearPasswordErrorMessage();

		void showLoginProgressBar();

		void hideLoginProgressBar();

	}

	static class LoginButtonClickHandler implements OnClickListener {

		@Override
		public void onClick(final android.view.View v) {
			presenter.onLoginButtonClick();
		}

		LoginButtonClickHandler(final LoginPresenter presenter) {
			this.presenter = presenter;
		}

		private final LoginPresenter presenter;

	}

	static class AuthenticateCallback implements TaskCallback<AuthenticateResult> {

		public AuthenticateCallback(final LoginPresenter loginPresenter) {
			this.presenter = loginPresenter;
		}

		@Override
		public void onSuccess(final AuthenticateResult taskResult) {
			presenter.onAuthenticateSuccess(taskResult);
		}

		@Override
		public void onFailure(final Throwable caught) {
			presenter.onAuthenticateFailure(caught);
		};

		private final LoginPresenter presenter;
	}

	static class CredentialsChangedListener extends TextChangedListener {

		public CredentialsChangedListener(final LoginPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			presenter.validateCredentials();
		}

		private final LoginPresenter presenter;

	}

	static class UsernameChangedListener extends TextChangedListener {

		public UsernameChangedListener(final LoginPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			presenter.markUsernameValidationErrors();
		}

		private final LoginPresenter presenter;

	}

	static class PasswordChangedListener extends TextChangedListener {

		public PasswordChangedListener(final LoginPresenter presenter) {
			this.presenter = presenter;
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			presenter.markPasswordValidationErrors();
		}

		private final LoginPresenter presenter;

	}

	static final class ChangeServerAddress implements OnMenuItemClickListener {

		public ChangeServerAddress(final ApplicationConfigurationFacade configuration, final Context context) {
			this.configuration = configuration;
			this.context = context;
		}

		@Override
		public boolean onMenuItemClick(final MenuItem item) {
			final EditText serverAddressEditText = new EditText(context);
			serverAddressEditText.setText(configuration.getServerAddress());

			new AlertDialog.Builder(context).setTitle("Podaj adres serwera").setMessage("Adres serwera")
					.setView(serverAddressEditText).setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(final DialogInterface dialog, final int which) {
							final String newServerAddress = serverAddressEditText.getText().toString();
							configuration.setServerAddress(newServerAddress);
						}

					}).show();
			return true;
		}

		private final ApplicationConfigurationFacade configuration;

		private final Context context;
	}

	@Override
	protected boolean onBackPressed() {
		getProxy().moveTaskToBack(true);
		return true;
	}

	private static final class RecentUserListItemClickListener implements OnItemClickListener {

		public RecentUserListItemClickListener(final LoginPresenter presenter) {
			super();
			this.presenter = presenter;
		}

		@Override
		public void onItemClick(final AdapterView<?> parent, final android.view.View view, final int position,
				final long id) {
			final Object user = parent.getItemAtPosition(position);
			if (user instanceof User) {
				presenter.onRecentUserSelected((User) user);
			} else {
				throw new RuntimeException("Something wrong happend, expected User from list, was: " + user);
			}
		}

		private final LoginPresenter presenter;

	}

	private void onLoginButtonClick() {
		final boolean credentialsWasValid = validateCredentials();
		if (credentialsWasValid) {
			getView().showLoginProgressBar();
			taskRunner.executeWithRetry(AuthenticateTask.action(getView().getUsername(), getView().getPassword()),
					new AuthenticateCallback(this));
		}
	}

	private boolean validateCredentials() {
		final boolean validUsername = markUsernameValidationErrors();
		final boolean validPassword = markPasswordValidationErrors();

		return validUsername && validPassword;
	}

	private boolean markUsernameValidationErrors() {
		getView().clearUsernameErrorMessage();
		final List<ValidationError> usernameValidationErrors = validateUsername();
		markValidationErrors(usernameValidationErrors);
		return usernameValidationErrors.isEmpty();
	}

	private List<ValidationError> validateUsername() {
		final String username = getView().getUsername();
		return CredentialsValidator.get(username, null).nonEmptyUsername().validate();
	}

	private boolean markPasswordValidationErrors() {
		getView().clearPasswordErrorMessage();
		final List<ValidationError> passwordValidationErrors = validatePassword();
		markValidationErrors(passwordValidationErrors);
		return passwordValidationErrors.isEmpty();
	}

	private List<ValidationError> validatePassword() {
		final String password = getView().getPassword();
		return CredentialsValidator.get(null, password).nonEmptyPassword().validate();
	}

	private void onAuthenticateSuccess(final AuthenticateResult result) {
		if (result.isAuthenticated()) {
			configuration.addRecentUser(result.getUser());
			applicationState.setAuthenticatedUser(result.getUser());
			placeManager.openPatientList();
		} else {
			getView().setUsernameError(R.string.wrong_user_credentials);
		}
		getView().hideLoginProgressBar();
	}

	private void onAuthenticateFailure(final Throwable caught) {
		getView().hideLoginProgressBar();
		getView().showConnectionError(R.string.connection_error);
	}

	private void markValidationErrors(final List<ValidationError> validationErrors) {
		for (final ValidationError validationError : validationErrors) {
			switch (validationError) {
			case EMPTY_USERNAME:
				getView().setUsernameError(validationError.getErrorMessage());
				break;
			case EMPTY_PASSWORD:
				getView().setPasswordError(validationError.getErrorMessage());
				break;
			}
		}
	}

	private final ApplicationState applicationState;

	private final RecentUsersListAdapter recentUsersListAdapter;

	private final MobiPlaceManager placeManager;

	private final MobiTaskRunner taskRunner;

	private final ApplicationConfigurationFacade configuration;

}
