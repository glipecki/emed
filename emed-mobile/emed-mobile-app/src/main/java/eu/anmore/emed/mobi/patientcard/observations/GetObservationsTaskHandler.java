package eu.anmore.emed.mobi.patientcard.observations;

import java.util.List;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask.GetObservationsAction;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask.GetObservationsResult;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskActionHandler;
import eu.anmore.mvpdroid.logger.LogCatLogger;
import eu.anmore.mvpdroid.logger.Logger;

public class GetObservationsTaskHandler implements
		TaskActionHandler<GetObservationsTask.GetObservationsAction, GetObservationsTask.GetObservationsResult> {

	@Inject
	public GetObservationsTaskHandler(final ApplicationConfigurationFacade configuration) {
		this.restConnector = new ObservationsRestConnector(configuration.getServerAddress());
	}

	@Override
	public GetObservationsResult handle(final GetObservationsAction taskAction) {
		log.info("> Getting observations [{}]", taskAction);

		final List<Observation> observations = restConnector.getObservations(taskAction.getPatientId(),
				taskAction.getGroupKey());
		final GetObservationsResult result = GetObservationsTask.result(observations);

		log.info("< observations [observations count={}]", result.getObservations().size());
		return result;
	}

	private static final Logger log = LogCatLogger.getLogger(GetObservationsTaskHandler.class);

	private final ObservationsRestConnector restConnector;

}
