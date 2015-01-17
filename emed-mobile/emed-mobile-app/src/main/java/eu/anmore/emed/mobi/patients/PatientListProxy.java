package eu.anmore.emed.mobi.patients;

import eu.anmore.mvpdroid.presenter.ActivityProxy;

/**
 * Currently admitted patients actvity to mvp proxy.
 * 
 * @author Grzegorz Lipecki
 */
public class PatientListProxy extends ActivityProxy<PatientListPresenter, PatientListPresenter.PatientListView> {

	public PatientListProxy() {
		super(PatientListPresenter.class);
	}

}
