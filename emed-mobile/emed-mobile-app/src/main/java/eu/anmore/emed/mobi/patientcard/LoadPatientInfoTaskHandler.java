package eu.anmore.emed.mobi.patientcard;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.patientcard.LoadPatientInfoTask.LoadPatientInfoAction;
import eu.anmore.emed.mobi.patientcard.LoadPatientInfoTask.LoadPatientInfoResult;
import eu.anmore.emed.mobi.patients.PatientsRestConnector;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskActionHandler;

public class LoadPatientInfoTaskHandler implements
		TaskActionHandler<LoadPatientInfoTask.LoadPatientInfoAction, LoadPatientInfoTask.LoadPatientInfoResult> {

	@Inject
	public LoadPatientInfoTaskHandler(final PatientsRestConnector patientsService) {
		this.patientsService = patientsService;
	}

	@Override
	public LoadPatientInfoResult handle(final LoadPatientInfoAction taskAction) {
		final Patient patient = patientsService.getPatientWithAdmissionList(taskAction.getPatientId());
		return LoadPatientInfoTask.result(patient);
	}

	private final PatientsRestConnector patientsService;

}
