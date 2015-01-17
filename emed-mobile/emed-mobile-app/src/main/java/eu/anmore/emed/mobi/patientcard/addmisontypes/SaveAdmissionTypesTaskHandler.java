package eu.anmore.emed.mobi.patientcard.addmisontypes;

import java.util.List;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SaveAdmissionTypesTaskHandler.SaveAdmissionTypesAction;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SaveAdmissionTypesTaskHandler.SaveAdmissionTypesResult;
import eu.anmore.emed.observation.AdmissionObservationType;
import eu.anmore.emed.observation.ObservationsRestConnector;
import eu.anmore.mvpdroid.async.TaskAction;
import eu.anmore.mvpdroid.async.TaskActionHandler;
import eu.anmore.mvpdroid.async.TaskResult;

public class SaveAdmissionTypesTaskHandler extends ObservationsRestConnector implements
		TaskActionHandler<SaveAdmissionTypesAction, SaveAdmissionTypesResult> {

	public static class SaveAdmissionTypesAction implements TaskAction {

		public SaveAdmissionTypesAction(final int admissionId, final List<AdmissionObservationType> types) {
			super();
			this.admissionId = admissionId;
			this.types = types;
		}

		public int getAdmissionId() {
			return admissionId;
		}

		public List<AdmissionObservationType> getTypes() {
			return types;
		}

		private final int admissionId;

		private final List<AdmissionObservationType> types;
	}

	public static class SaveAdmissionTypesResult implements TaskResult {
		public SaveAdmissionTypesResult() {
		}
	}

	@Inject
	public SaveAdmissionTypesTaskHandler(final ApplicationConfigurationFacade config) {
		super(config.getServerAddress());
	}

	@Override
	public SaveAdmissionTypesResult handle(final SaveAdmissionTypesAction taskAction) {
		setAdmissionObservations(taskAction.getAdmissionId(), taskAction.getTypes());
		return new SaveAdmissionTypesResult();
	}

}
