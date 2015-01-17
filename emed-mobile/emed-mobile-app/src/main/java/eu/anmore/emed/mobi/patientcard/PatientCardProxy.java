package eu.anmore.emed.mobi.patientcard;

import eu.anmore.mvpdroid.presenter.ActivityProxy;

public class PatientCardProxy extends ActivityProxy<PatientCardPresenter, PatientCardPresenter.PatientCardView> {

	public static final String PATIENT_ID = "patientId";

	public static final String ADMISSION_ID = "admissionId";

	public PatientCardProxy() {
		super(PatientCardPresenter.class);
	}

}
