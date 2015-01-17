package eu.anmore.emed.mobi.patientcard;

import java.util.List;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes.LoadCustomObservationTypesAction;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes.LoadCustomObservationTypesResult;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskActionHandler;

public class LoadCustomObservationTypesTaskHandler extends ObservationsRestConnector
		implements
		TaskActionHandler<LoadCustomObservationTypes.LoadCustomObservationTypesAction, LoadCustomObservationTypes.LoadCustomObservationTypesResult> {

	@Inject
	public LoadCustomObservationTypesTaskHandler(final ApplicationConfigurationFacade config) {
		super(config.getServerAddress());
	}

	@Override
	public LoadCustomObservationTypesResult handle(final LoadCustomObservationTypesAction taskAction) {
		final List<CustomObservationType> types = getCustomObservationTypes();
		return LoadCustomObservationTypes.result(types);
	}

}
