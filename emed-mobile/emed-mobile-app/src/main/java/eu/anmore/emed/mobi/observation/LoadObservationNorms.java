package eu.anmore.emed.mobi.observation;

import java.util.List;

import eu.anmore.emed.observation.ObservationNorm;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

public class LoadObservationNorms {

	public static LoadObservationNormsAction action() {
		return new LoadObservationNormsAction();
	}

	public static LoadObservationNormsResult result(final List<ObservationNorm> norms) {
		return new LoadObservationNormsResult(norms);
	}

	public static class LoadObservationNormsAction implements TaskAction {

		public LoadObservationNormsAction() {
			super();
		}

	}

	public static class LoadObservationNormsResult implements TaskResult {

		public LoadObservationNormsResult(final List<ObservationNorm> norms) {
			super();
			this.norms = norms;
		}

		public List<ObservationNorm> getNorms() {
			return norms;
		}

		private final List<ObservationNorm> norms;

	}

}
