package eu.anmore.emed.mobi.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import roboguice.inject.InjectView;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import eu.anmore.emed.mobi.R;
import eu.anmore.mvpdroid.test.MockedStringEditable;
import eu.anmore.mvpdroid.test.ReflectionUtils;
import eu.anmore.mvpdroid.test.ViewMockBuilder;
import eu.anmore.mvpdroid.toast.AndroidToast;

@RunWith(RobolectricTestRunner.class)
public class LoginViewTest {

	@Before
	public void setup() {
		usernameErrorMessageTextViewMock = mock(TextView.class);
		passwordErrorMessageTextViewMock = mock(TextView.class);
		usernameEditTextMock = mock(EditText.class);
		passwordEditTextMock = mock(EditText.class);
		loginButtonMock = mock(Button.class);
		toastMock = mock(AndroidToast.class);

		view = ViewMockBuilder.get(new LoginViewImpl(toastMock))
				.with(USERNAME_ERROR_MESSAGE_TEXT_VIEW_FIELD, usernameErrorMessageTextViewMock)
				.with(PASSWORD_ERROR_MESSAGE_TEXT_VIEW_FIELD, passwordErrorMessageTextViewMock)
				.with(USERNAME_EDIT_TEXT_FIELD, usernameEditTextMock)
				.with(PASSWORD_EDIT_TEXT_FIELD, passwordEditTextMock).with(LOGIN_BUTTON_FIELD, loginButtonMock).build();
	}

	@Test
	public void shouldDefineUsernameErrorMessageHolder() {
		// given
		final int viewId = R.id.loginPresenter_usernameErrorMessageTextView;

		// when
		final Field field = ReflectionUtils.getInheritedField(LoginViewImpl.class, USERNAME_ERROR_MESSAGE_TEXT_VIEW_FIELD);

		// then
		assertNotNull(viewId);
		assertNotNull(field);
		final InjectView injectViewAnnotation = field.getAnnotation(InjectView.class);
		assertNotNull(injectViewAnnotation);
		assertEquals(viewId, injectViewAnnotation.value());
	}

	@Test
	public void shouldDefinePasswordErrorMessageHolder() {
		// given
		final int viewId = R.id.loginPresenter_passwordErrorMessageTextView;

		// when
		final Field field = ReflectionUtils.getInheritedField(LoginViewImpl.class, PASSWORD_ERROR_MESSAGE_TEXT_VIEW_FIELD);

		// then
		assertNotNull(viewId);
		assertNotNull(field);
		final InjectView injectViewAnnotation = field.getAnnotation(InjectView.class);
		assertNotNull(injectViewAnnotation);
		assertEquals(viewId, injectViewAnnotation.value());
	}

	@Test
	public void shouldSetUsernmaeError() {
		// given
		final int errorMessage = getRandomResourceId();

		// when
		view.setUsernameError(errorMessage);

		// then
		verify(usernameErrorMessageTextViewMock).setText(errorMessage);
	}

	@Test
	public void shouldDefineSetPasswordErrorMethod() {
		// given
		final int errorMessage = getRandomResourceId();

		// when
		view.setPasswordError(errorMessage);

		// then
		verify(passwordErrorMessageTextViewMock).setText(errorMessage);
	}

	@Test
	public void shouldDefineUsernameEditText() {
		// given
		final int viewId = R.id.loginPresenter_usernameEditText;

		// when
		final Field field = ReflectionUtils.getInheritedField(LoginViewImpl.class, USERNAME_EDIT_TEXT_FIELD);

		// then
		assertNotNull(viewId);
		assertNotNull(field);
		final InjectView injectViewAnnotation = field.getAnnotation(InjectView.class);
		assertNotNull(injectViewAnnotation);
		assertEquals(viewId, injectViewAnnotation.value());
	}

	@Test
	public void shouldGetUsernameFromWidget() {
		// given
		final String widgetValue = "username-from-widget";
		when(usernameEditTextMock.getText()).thenReturn(new MockedStringEditable(widgetValue));

		// when
		final String result = view.getUsername();

		// then
		assertEquals(widgetValue, result);
	}

	@Test
	public void shouldDefinePasswordEditText() {
		// given
		final int viewId = R.id.loginPresenter_passwordEditText;

		// when
		final Field field = ReflectionUtils.getInheritedField(LoginViewImpl.class, PASSWORD_EDIT_TEXT_FIELD);

		// then
		assertNotNull(viewId);
		assertNotNull(field);
		final InjectView injectViewAnnotation = field.getAnnotation(InjectView.class);
		assertNotNull(injectViewAnnotation);
		assertEquals(viewId, injectViewAnnotation.value());
	}

	@Test
	public void shouldGetPasswordFromWidget() {
		// given
		final String widgetValue = "username-from-widget";
		when(passwordEditTextMock.getText()).thenReturn(new MockedStringEditable(widgetValue));

		// when
		final String result = view.getPassword();

		// then
		assertEquals(widgetValue, result);
	}

	@Test
	public void shouldDefineLoginButton() {
		// given
		final int viewId = R.id.loginPresenter_loginButton;

		// when
		final Field field = ReflectionUtils.getInheritedField(LoginViewImpl.class, LOGIN_BUTTON_FIELD);

		// then
		assertNotNull(viewId);
		assertNotNull(field);
		final InjectView injectViewAnnotation = field.getAnnotation(InjectView.class);
		assertNotNull(injectViewAnnotation);
		assertEquals(viewId, injectViewAnnotation.value());
	}

	@Test
	public void shouldSetLoginButtonListener() {
		// given
		final OnClickListener onClickListener = mock(OnClickListener.class);

		// when
		view.setLoginButtonClickHandler(onClickListener);

		// then
		verify(loginButtonMock).setOnClickListener(onClickListener);
	}

	@Test
	public void shouldAddUsernameChangeListener() {
		// given
		final TextWatcher usernameChangeListener = mock(TextWatcher.class);

		// when
		view.addUsernameChangeListener(usernameChangeListener);

		// then
		verify(usernameEditTextMock).addTextChangedListener(usernameChangeListener);
	}

	@Test
	public void shouldAddPasswordListener() {
		// given
		final TextWatcher passwordChangeListener = mock(TextWatcher.class);

		// when
		view.addPasswordChangeListener(passwordChangeListener);

		// then
		verify(passwordEditTextMock).addTextChangedListener(passwordChangeListener);
	}

	@Test
	public void shouldClearUsernameErrorMessages() {
		// given
		view.setUsernameError(1);

		// when
		view.clearUsernameErrorMessage();

		// then
		verify(usernameErrorMessageTextViewMock, times(1)).setVisibility(View.GONE);
	}

	@Test
	public void shouldClearPasswordErrorMessages() {
		// given
		view.setPasswordError(1);

		// when
		view.clearPasswordErrorMessage();

		// then
		verify(passwordErrorMessageTextViewMock, times(1)).setVisibility(View.GONE);
	}

	@Test
	public void shouldChangeVisiblityOfUsernameErrorMessagesOnSet() {
		// given

		// when
		view.setUsernameError(1);

		// then
		verify(usernameErrorMessageTextViewMock, times(1)).setVisibility(View.VISIBLE);
	}

	@Test
	public void shouldChangeVisiblityOfPasswordErrorMessagesOnSet() {
		// given

		// when
		view.setPasswordError(1);

		// then
		verify(passwordErrorMessageTextViewMock, times(1)).setVisibility(View.VISIBLE);
	}

	@Test
	public void shouldShowConnectionError() {
		// given
		final int connectionErrorMessage = R.string.connection_error;

		// when
		view.showConnectionError(connectionErrorMessage);

		// then
		verify(toastMock, times(1)).showShortToast(connectionErrorMessage);
	}

	private int getRandomResourceId() {
		return 5;
	}

	private static final String PASSWORD_ERROR_MESSAGE_TEXT_VIEW_FIELD = "passwordErrorMessageTextView";

	private static final String USERNAME_ERROR_MESSAGE_TEXT_VIEW_FIELD = "usernameErrorMessageTextView";

	private static final String USERNAME_EDIT_TEXT_FIELD = "usernameEditText";

	private static final String PASSWORD_EDIT_TEXT_FIELD = "passwordEditText";

	private static final String LOGIN_BUTTON_FIELD = "loginButton";

	private LoginPresenter.LoginView view;

	private TextView usernameErrorMessageTextViewMock;

	private TextView passwordErrorMessageTextViewMock;

	private EditText usernameEditTextMock;

	private EditText passwordEditTextMock;

	private Button loginButtonMock;

	private AndroidToast toastMock;

}
