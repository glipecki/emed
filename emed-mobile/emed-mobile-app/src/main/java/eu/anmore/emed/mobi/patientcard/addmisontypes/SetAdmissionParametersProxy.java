package eu.anmore.emed.mobi.patientcard.addmisontypes;

import eu.anmore.mvpdroid.presenter.ActivityProxy;

public class SetAdmissionParametersProxy extends
		ActivityProxy<SetAdmissionParametersPresenter, SetAdmissionParametersPresenter.SetAdmissionParametersView> {

	public static final String PATIENT_ID = "patientId";

	public static final String ADMISSION_ID = "admissionId";

	public SetAdmissionParametersProxy() {
		super(SetAdmissionParametersPresenter.class);
	}

}
