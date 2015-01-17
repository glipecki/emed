package eu.anmore.emed.mobi.patientcard;

import java.util.List;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmissionTypesResult;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmisstionTypesAction;
import eu.anmore.emed.observation.AdmissionObservationType;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskActionHandler;
import eu.anmore.mvpdroid.async.TaskResult;

public class LoadAdmisstionTypesTaskHandler extends ObservationsRestConnector implements
		TaskActionHandler<LoadAdmisstionTypesAction, LoadAdmissionTypesResult> {

	public static class LoadAdmisstionTypesAction implements TaskAction {
		public LoadAdmisstionTypesAction(final int patientId) {
			this.patientId = patientId;
		}

		public int getPatientId() {
			return patientId;
		}

		private final int patientId;

	}

	public static class LoadAdmissionTypesResult implements TaskResult {
		public LoadAdmissionTypesResult(final List<AdmissionObservationType> admissionTypes) {
			this.admissionTypes = admissionTypes;
		}

		public List<AdmissionObservationType> getAdmissionTypes() {
			return admissionTypes;
		}

		private final List<AdmissionObservationType> admissionTypes;
	}

	@Inject
	public LoadAdmisstionTypesTaskHandler(final ApplicationConfigurationFacade config) {
		super(config.getServerAddress());
	}

	@Override
	public LoadAdmissionTypesResult handle(final LoadAdmisstionTypesAction taskAction) {
		final List<AdmissionObservationType> types = getAdmissionObservation(taskAction.getPatientId());
		return new LoadAdmissionTypesResult(types);
	}

}
