package eu.anmore.emed.mobi.patientcard.observations;

import java.util.ArrayList;
import java.util.List;

import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

public class GetObservationsTask {

	public static GetObservationsAction action(final Integer patientId, final String groupKey) {
		return new GetObservationsAction(patientId, groupKey);
	}

	public static GetObservationsResult result(final List<Observation> observations) {
		return new GetObservationsResult(observations);
	}

	public static TaskAction action(final int patientId, final List<ObservationGroup> observationGroupKeys) {
		return new GetObservationsAction(patientId, observationGroupKeys);
	}

	public static final class GetObservationsAction implements TaskAction {

		public GetObservationsAction(final Integer patientId, final String groupKey) {
			super();
			this.patientId = patientId;
			this.observationGroupKeys.add(groupKey);
		}

		public GetObservationsAction(final int patientId, final List<ObservationGroup> observationGroupKeys) {
			this.patientId = patientId;
			for (final ObservationGroup group : observationGroupKeys) {
				this.observationGroupKeys.add(group.getKey());
			}
		}

		@Override
		public String toString() {
			return String.format("GetObservationsAction [observationGroupKeys=%s, patientId=%s]", observationGroupKeys,
					patientId);
		}

		public Integer getPatientId() {
			return patientId;
		}

		public List<String> getGroupKey() {
			return observationGroupKeys;
		}

		private final List<String> observationGroupKeys = new ArrayList<String>();

		private final Integer patientId;

	}

	public static final class GetObservationsResult implements TaskResult {

		public GetObservationsResult(final List<Observation> observations) {
			this.observations = observations;
		}

		public List<Observation> getObservations() {
			return observations;
		}

		@Override
		public String toString() {
			return String.format("GetObservationsResult [observations=%s]", observations);
		}

		private final List<Observation> observations;

	}

}
