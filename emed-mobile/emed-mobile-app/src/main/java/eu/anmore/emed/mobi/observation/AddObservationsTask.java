package eu.anmore.emed.mobi.observation;

import java.util.List;

import eu.anmore.emed.observation.Observation;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

/**
 * Add observations task.
 * 
 * @author glipecki
 * 
 */
public class AddObservationsTask {

	/**
	 * Creates add observations task.
	 * 
	 * @param patientId
	 * @param observations
	 * @return
	 */
	public static AddObservationsAction action(final Integer patientId, final List<Observation> observations) {
		return new AddObservationsAction(patientId, observations);
	}

	/**
	 * Creates add observations result.
	 * 
	 * @return
	 */
	public static AddObservationsResult result() {
		return new AddObservationsResult();
	}

	public static final class AddObservationsAction implements TaskAction {

		public AddObservationsAction(final Integer patientId, final List<Observation> observations) {
			super();
			this.patientId = patientId;
			this.observations = observations;
		}

		public Integer getPatientId() {
			return patientId;
		}

		public List<Observation> getObservations() {
			return observations;
		}

		@Override
		public String toString() {
			return String.format("AddObservationsAction [patientId=%s, observations=%s]", patientId, observations);
		}

		private final Integer patientId;

		private final List<Observation> observations;

	}

	public static final class AddObservationsResult implements TaskResult {

	}

}
