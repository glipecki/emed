package eu.anmore.emed.mobi.patients;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskActionHandler;

/**
 * Load currently admitted patients task action handler.
 * 
 * @author Grzegorz Lipecki
 */
public class LoadPatientsTaskHandler implements
		TaskActionHandler<LoadPatientsTask.LoadPatientsAction, LoadPatientsTask.LoadPatientsResult> {

	@Inject
	public LoadPatientsTaskHandler(final PatientsRestConnector patientsService) {
		this.patientsService = patientsService;
	}

	@Override
	public LoadPatientsTask.LoadPatientsResult handle(final LoadPatientsTask.LoadPatientsAction taskAction) {
		final List<Patient> patientsWithoutAdmissions = patientsService.getAdmitted();
		final List<Patient> patients = new ArrayList<Patient>();
		for (final Patient patient : patientsWithoutAdmissions) {
			// XXX (Grzegorz Lipecki): we should use specialized service instead
			// of calling general services multiple
			// times
			patients.add(patientsService.getPatientWithAdmissionList(patient.getId()));
		}
		return LoadPatientsTask.result(patients);
	}

	private final PatientsRestConnector patientsService;

}
