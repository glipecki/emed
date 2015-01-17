package eu.anmore.emed.mobi.patients;

import java.util.List;

import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

/**
 * Load currently admitted patients task.
 * 
 * @author Grzegorz Lipecki
 */
public class LoadPatientsTask {

	public static LoadPatientsAction action() {
		return new LoadPatientsAction();
	}

	public static LoadPatientsResult result(final List<Patient> patients) {
		return new LoadPatientsResult(patients);
	}

	/**
	 * Load currently admitted patients action.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static class LoadPatientsAction implements TaskAction {

		public LoadPatientsAction() {
			super();
		}

	}

	/**
	 * Load currently admitted patients result.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static class LoadPatientsResult implements TaskResult {

		public LoadPatientsResult(final List<Patient> patients) {
			super();
			this.patients = patients;
		}

		public List<Patient> getPatients() {
			return patients;
		}

		private final List<Patient> patients;
	}

}
