package eu.anmore.emed.mobi;

import eu.anmore.emed.authentication.Authentication;
import eu.anmore.emed.mobi.login.LoginPresenter.LoginView;
import eu.anmore.emed.mobi.login.LoginViewImpl;
import eu.anmore.emed.mobi.login.authenticate.AuthenticationRestConnector;
import eu.anmore.emed.mobi.observation.AddObservationPresenter.AddObservationView;
import eu.anmore.emed.mobi.observation.AddObservationViewImpl;
import eu.anmore.emed.mobi.parameters.CustomObservationsPresenter.CustomObservationsView;
import eu.anmore.emed.mobi.parameters.CustomObservationsViewImpl;
import eu.anmore.emed.mobi.patientcard.PatientCardPresenter.PatientCardView;
import eu.anmore.emed.mobi.patientcard.PatientCardViewImpl;
import eu.anmore.emed.mobi.patients.PatientListPresenter.PatientListView;
import eu.anmore.emed.mobi.patients.PatientListViewImpl;
import eu.anmore.mvpdroid.module.AndroidModule;
import eu.anmore.mvpdroid.task.runner.TaskRunner;
import eu.anmore.mvpdroid.toast.AndroidToast;
import eu.anmore.mvpdroid.toast.DefaultAndroidToast;

/**
 * Module configuration.
 * 
 * @author Grzegorz Lipecki
 */
public class MobiModule extends AndroidModule {

	@Override
	protected void configure() {
		bind(Authentication.class).to(AuthenticationRestConnector.class);
		bind(AndroidToast.class).to(DefaultAndroidToast.class);

		bind(LoginView.class).to(LoginViewImpl.class);
		bind(PatientListView.class).to(PatientListViewImpl.class);
		bind(PatientCardView.class).to(PatientCardViewImpl.class);
		bind(AddObservationView.class).to(AddObservationViewImpl.class);
		bind(CustomObservationsView.class).to(CustomObservationsViewImpl.class);
		bind(TaskRunner.class).to(MobiTaskRunner.class);
	}

}
