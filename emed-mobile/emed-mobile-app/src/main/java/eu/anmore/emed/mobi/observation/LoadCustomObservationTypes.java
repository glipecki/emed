package eu.anmore.emed.mobi.observation;

import java.util.List;

import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskResult;

public class LoadCustomObservationTypes {

	public static LoadCustomObservationTypesAction action() {
		return new LoadCustomObservationTypesAction();
	}

	public static LoadCustomObservationTypesResult result(final List<CustomObservationType> types) {
		return new LoadCustomObservationTypesResult(types);
	}

	public static class LoadCustomObservationTypesAction implements TaskAction {

		public LoadCustomObservationTypesAction() {
			super();
		}

	}

	public static class LoadCustomObservationTypesResult implements TaskResult {

		public LoadCustomObservationTypesResult(final List<CustomObservationType> types) {
			super();
			this.types = types;
		}

		public List<CustomObservationType> getTypes() {
			return types;
		}

		private final List<CustomObservationType> types;

	}

}
