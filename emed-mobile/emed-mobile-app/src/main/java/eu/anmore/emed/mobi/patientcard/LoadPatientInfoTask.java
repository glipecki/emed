package eu.anmore.emed.mobi.patientcard;

import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

/**
 * Load patient data task.
 * 
 * @author glipecki
 * 
 */
public class LoadPatientInfoTask {

	public static LoadPatientInfoAction action(final int patientId) {
		return new LoadPatientInfoAction(patientId);
	}

	public static LoadPatientInfoResult result(final Patient patient) {
		return new LoadPatientInfoResult(patient);
	}

	/**
	 * Load patient data action.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static class LoadPatientInfoAction implements TaskAction {

		public LoadPatientInfoAction(final int patientId) {
			super();
			this.patientId = patientId;
		}

		public int getPatientId() {
			return patientId;
		}

		private final int patientId;

	}

	/**
	 * Load patient data result.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static class LoadPatientInfoResult implements TaskResult {

		public LoadPatientInfoResult(final Patient patient) {
			super();
			this.patient = patient;
		}

		public Patient getPatient() {
			return patient;
		}

		private final Patient patient;

	}

}
