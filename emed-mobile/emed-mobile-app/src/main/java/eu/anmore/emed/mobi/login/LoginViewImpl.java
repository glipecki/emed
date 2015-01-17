package eu.anmore.emed.mobi.login;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.R;
import eu.anmore.mvpdroid.toast.AndroidToast;
import eu.anmore.mvpdroid.view.BaseView;

/**
 * Login screen view implementation.
 * 
 * @author Grzegorz Lipecki
 */
@ContentView(R.layout.login)
public class LoginViewImpl extends BaseView implements LoginPresenter.LoginView {

	@Inject
	public LoginViewImpl(final AndroidToast toast) {
		this.toast = toast;
	}

	@Override
	public String getUsername() {
		return usernameEditText.getText().toString();
	}

	@Override
	public String getPassword() {
		return passwordEditText.getText().toString();
	}

	@Override
	public void setUsernameError(final int errorMessage) {
		usernameErrorMessageTextView.setText(errorMessage);
		usernameErrorMessageTextView.setVisibility(VISIBLE);
	}

	@Override
	public void setPasswordError(final int errorMessage) {
		passwordErrorMessageTextView.setText(errorMessage);
		passwordErrorMessageTextView.setVisibility(VISIBLE);
	}

	@Override
	public void setLoginButtonClickHandler(final OnClickListener onClickListener) {
		loginButton.setOnClickListener(onClickListener);
	}

	@Override
	public void addUsernameChangeListener(final TextWatcher usernameChangeListener) {
		usernameEditText.addTextChangedListener(usernameChangeListener);
	}

	@Override
	public void addPasswordChangeListener(final TextWatcher passwordChangeListener) {
		passwordEditText.addTextChangedListener(passwordChangeListener);
	}

	@Override
	public void clearUsernameErrorMessage() {
		usernameErrorMessageTextView.setVisibility(GONE);
	}

	@Override
	public void clearPasswordErrorMessage() {
		passwordErrorMessageTextView.setVisibility(GONE);
	}

	@Override
	public void showConnectionError(final int connectionErrorMessage) {
		toast.showShortToast(connectionErrorMessage);
	}

	@Override
	public void setRecentUsersListAdapter(final ListAdapter recentUsersListAdapter) {
		recentUsersListView.setAdapter(recentUsersListAdapter);
	}

	@Override
	public void setRecentUserClickListener(final OnItemClickListener recentUserClickListener) {
		recentUsersListView.setOnItemClickListener(recentUserClickListener);
	}

	@Override
	public void setUsername(final String username) {
		usernameEditText.setText(username);
		passwordEditText.setText("");
		clearPasswordErrorMessage();
		passwordEditText.requestFocus();
	}

	@Override
	public void showLoginProgressBar() {
		loginButton.setVisibility(View.GONE);
		loginProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoginProgressBar() {
		loginProgressBar.setVisibility(View.GONE);
		loginButton.setVisibility(View.VISIBLE);
	}

	private final AndroidToast toast;

	@InjectView(R.id.loginPresenter_usernameEditText)
	private EditText usernameEditText;

	@InjectView(R.id.loginPresenter_passwordEditText)
	private EditText passwordEditText;

	@InjectView(R.id.loginPresenter_loginButton)
	private Button loginButton;

	@InjectView(R.id.loginPresenter_usernameErrorMessageTextView)
	private TextView usernameErrorMessageTextView;

	@InjectView(R.id.loginPresenter_passwordErrorMessageTextView)
	private TextView passwordErrorMessageTextView;

	@InjectView(R.id.loginPresenter_recentUsers)
	private ListView recentUsersListView;

	@InjectView(R.id.login_login_progress)
	private ProgressBar loginProgressBar;

}
