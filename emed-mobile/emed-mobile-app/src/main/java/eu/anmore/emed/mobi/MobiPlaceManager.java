package eu.anmore.emed.mobi;

import java.util.List;

import android.app.Activity;
import android.content.Intent;

import com.google.inject.Inject;

import eu.anmore.emed.admission.Admission;
import eu.anmore.emed.mobi.login.LoginProxy;
import eu.anmore.emed.mobi.observation.AddObservationProxy;
import eu.anmore.emed.mobi.parameters.CustomObservationsProxy;
import eu.anmore.emed.mobi.patientcard.PatientCardProxy;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SetAdmissionParametersProxy;
import eu.anmore.emed.mobi.patients.PatientListProxy;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.placemanager.AndroidAbstractPlaceManager;
import eu.anmore.mvpdroid.toast.AndroidToast;
import eu.anmore.utils.StringStringMapBuilder;

/**
 * Application place manager.
 * 
 * @author Grzegorz Lipecki
 */
public class MobiPlaceManager extends AndroidAbstractPlaceManager {

	@Inject
	public MobiPlaceManager(final Activity activity, final AndroidToast toast) {
		super(activity);
		this.toast = toast;
	}

	/**
	 * Opens patient list activity.
	 */
	public void openPatientList() {
		startActivity(PatientListProxy.class);
	}

	/**
	 * Opens patient card.
	 * 
	 * @param patient
	 */
	public void openPatientCard(final Patient patient) {
		if (!patient.getAdmissionList().isEmpty()) {
			final String patientId = String.valueOf(patient.getId());
			final String admissionId = String.valueOf(getLastAdmissionId(patient));
			startActivity(PatientCardProxy.class, StringStringMapBuilder.of(PatientCardProxy.PATIENT_ID, patientId)
					.entry(PatientCardProxy.ADMISSION_ID, admissionId).get());
		} else {
			toast.showShortToast("Nie można otworzyć karty pacjenta.");
		}
	}

	/**
	 * Logout.
	 */
	public void logoutCurrentUser() {
		startActivity(LoginProxy.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}

	/**
	 * Add observation.
	 */
	public void openAddObservation(final Integer activePatientId, final List<ObservationType> observationTypes) {
		final StringBuilder params = new StringBuilder();
		for (final ObservationType observationType : observationTypes) {
			params.append(observationType.getKey()).append(AddObservationProxy.OBSERVATION_TYPES_SEPARATOR);
		}
		final StringStringMapBuilder paramsBuilder = StringStringMapBuilder.of(AddObservationProxy.OBSERVATION_TYPES,
				params.toString());
		paramsBuilder.entry(AddObservationProxy.ACTIVE_PATIENT_ID, String.valueOf(activePatientId));
		startActivity(AddObservationProxy.class, paramsBuilder.get());

	}

	public void openCustomParameters() {
		startActivity(CustomObservationsProxy.class);
	}

	public void openSetAdmissionParameters(final int patientId, final int admissionId) {
		startActivity(
				SetAdmissionParametersProxy.class,
				StringStringMapBuilder.of(SetAdmissionParametersProxy.PATIENT_ID, String.valueOf(patientId))
						.entry(SetAdmissionParametersProxy.ADMISSION_ID, String.valueOf(admissionId)).get());
	}

	private int getLastAdmissionId(final Patient patient) {
		int maxId = 0;
		for (final Admission admission : patient.getAdmissionList()) {
			if (admission.getDischargeDate() == null) {
				return admission.getId();
			}
			if (admission.getId() > maxId) {
				maxId = admission.getId();
			}
		}
		return maxId;
	}

	private final AndroidToast toast;

}
