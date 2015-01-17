package eu.anmore.emed.mobi.observation;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.observation.AddObservationsTask.AddObservationsAction;
import eu.anmore.emed.mobi.observation.AddObservationsTask.AddObservationsResult;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskActionHandler;
import eu.anmore.mvpdroid.logger.LogCatLogger;
import eu.anmore.mvpdroid.logger.Logger;

public class ObservationsTaskHandler extends ObservationsRestConnector implements
		TaskActionHandler<AddObservationsTask.AddObservationsAction, AddObservationsTask.AddObservationsResult> {

	@Inject
	public ObservationsTaskHandler(final ApplicationConfigurationFacade config, final ApplicationState appState) {
		super(config.getServerAddress());
		this.appState = appState;
	}

	@Override
	public AddObservationsResult handle(final AddObservationsAction taskAction) {
		for (int retry = 0; retry < 3; ++retry) {
			try {
				addObservations(taskAction.getPatientId(), taskAction.getObservations(), appState
						.getAuthenticatedUser().getUsername());
				return AddObservationsTask.result();
			} catch (final Throwable th) {
				log.warn("Can't connect to REST service with error [try={}, err={}]", retry + 1, th.getMessage());
				try {
					synchronized (this) {
						wait(1000);
					}
				} catch (final InterruptedException e) {
				}
			}
		}
		log.error("Can't connect to REST service");
		throw new RuntimeException("Can't add observation via REST service");
	}

	private static final Logger log = LogCatLogger.getLogger(ObservationsTaskHandler.class);

	private final ApplicationState appState;

}
