package eu.anmore.emed.mobi.patientcard;

import java.util.List;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.observation.LoadObservationNorms;
import eu.anmore.emed.mobi.observation.LoadObservationNorms.LoadObservationNormsAction;
import eu.anmore.emed.mobi.observation.LoadObservationNorms.LoadObservationNormsResult;
import eu.anmore.emed.observation.ObservationNorm;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskActionHandler;

public class LoadObservationNormsTaskHandler extends ObservationsRestConnector
		implements
		TaskActionHandler<LoadObservationNorms.LoadObservationNormsAction, LoadObservationNorms.LoadObservationNormsResult> {

	@Inject
	public LoadObservationNormsTaskHandler(final ApplicationConfigurationFacade config) {
		super(config.getServerAddress());
	}

	@Override
	public LoadObservationNormsResult handle(final LoadObservationNormsAction taskAction) {
		final List<ObservationNorm> norms = getObservationNorms();
		return LoadObservationNorms.result(norms);
	}

}
