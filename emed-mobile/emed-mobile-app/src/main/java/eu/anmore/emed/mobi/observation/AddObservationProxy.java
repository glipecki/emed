package eu.anmore.emed.mobi.observation;

import eu.anmore.mvpdroid.presenter.ActivityProxy;

/**
 * Okno dodawania obserwacji.
 * 
 * @author glipecki
 */
public class AddObservationProxy extends
		ActivityProxy<AddObservationPresenter, AddObservationPresenter.AddObservationView> {

	public static final String OBSERVATION_TYPES_SEPARATOR = ";";

	public static final String OBSERVATION_TYPES = "observationTypes";

	public static final String ACTIVE_PATIENT_ID = "activePatientId";

	public AddObservationProxy() {
		super(AddObservationPresenter.class);
	}

}
