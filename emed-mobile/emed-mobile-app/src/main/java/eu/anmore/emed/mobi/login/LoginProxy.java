package eu.anmore.emed.mobi.login;

import eu.anmore.mvpdroid.presenter.ActivityProxy;

/**
 * Login screen activity to mvp proxy.
 * 
 * @author Grzegorz Lipecki
 */
public class LoginProxy extends ActivityProxy<LoginPresenter, LoginPresenter.LoginView> {

	public LoginProxy() {
		super(LoginPresenter.class);
	}

}
