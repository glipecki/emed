package eu.anmore.emed.mobi.parameters;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.parameters.CustomObservationsTaskHandler.AddCustomObservationAction;
import eu.anmore.emed.mobi.parameters.CustomObservationsTaskHandler.AddCustomObservationResult;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskActionHandler;
import eu.anmore.mvpdroid.async.TaskResult;

public class CustomObservationsTaskHandler extends ObservationsRestConnector implements
		TaskActionHandler<AddCustomObservationAction, AddCustomObservationResult> {

	public static class AddCustomObservationAction implements TaskAction {

		public AddCustomObservationAction(final CustomObservationType customObservationType) {
			this.customObservationType = customObservationType;
		}

		public CustomObservationType getCustomObservationType() {
			return customObservationType;
		}

		private final CustomObservationType customObservationType;

	}

	public static class AddCustomObservationResult implements TaskResult {

	}

	@Inject
	public CustomObservationsTaskHandler(final ApplicationConfigurationFacade config) {
		super(config.getServerAddress());
	}

	@Override
	public AddCustomObservationResult handle(final AddCustomObservationAction taskAction) {
		addCustomObservationType(taskAction.getCustomObservationType());
		return new AddCustomObservationResult();
	}
}
