package eu.anmore.emed.mobi.patientcard.addmisontypes;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmissionTypesResult;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmisstionTypesAction;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SaveAdmissionTypesTaskHandler.SaveAdmissionTypesAction;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SaveAdmissionTypesTaskHandler.SaveAdmissionTypesResult;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SetAdmissionParametersPresenter.SetAdmissionParametersView;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SetAdmissionParametersPresenter.SetAdmissionParametersView.SaveCallback;
import eu.anmore.emed.observation.AdmissionObservationType;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.presenter.Presenter;
import eu.anmore.mvpdroid.task.runner.TaskRunner;
import eu.anmore.mvpdroid.toast.AndroidToast;

public class SetAdmissionParametersPresenter extends Presenter<SetAdmissionParametersView> {

	@Inject
	public SetAdmissionParametersPresenter(final SetAdmissionParametersViewImpl view, final TaskRunner taskRunner,
			final AndroidToast toast, final ApplicationState appState) {
		super(view);
		this.taskRunner = taskRunner;
		this.toast = toast;
		this.appState = appState;
	}

	public interface SetAdmissionParametersView extends eu.anmore.mvpdroid.view.View {

		public interface SaveCallback {
			void save(List<String> types);
		}

		void setPatientId(int patinetId);

		void setSaveCallback(SaveCallback saveCallback);

	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		getView().setPatientId(getPatinetId());
		getView().setSaveCallback(new SaveCallback() {

			@Override
			public void save(final List<String> types) {
				final int admissionId = getAdmissionId();
				final List<AdmissionObservationType> admissionTypes = new ArrayList<AdmissionObservationType>();
				for (final String type : types) {
					admissionTypes.add(new AdmissionObservationType(admissionId, type, true));
				}
				taskRunner.executeWithRetry(new SaveAdmissionTypesAction(admissionId, admissionTypes),
						new TaskCallback<SaveAdmissionTypesResult>() {

							@Override
							public void onSuccess(final SaveAdmissionTypesResult taskResult) {
								onAdmissionParametersSaved();
							}

							@Override
							public void onFailure(final Throwable caught) {
								toast.showShortToast("Nie udało zapisać się śledzonych parametrów przyjęcia. Spróbuj ponownie.");
							}

						});

			}
		});
	}

	private void onAdmissionParametersSaved() {
		toast.showShortToast("Pomyślnie zapisano śledzone parametry przyjęcia");
		taskRunner.executeWithRetry(new LoadAdmisstionTypesAction(getPatinetId()),
				new TaskCallback<LoadAdmissionTypesResult>() {

					@Override
					public void onSuccess(final LoadAdmissionTypesResult taskResult) {
						appState.putPatientAdmissionObservationTypes(getPatinetId(), taskResult.getAdmissionTypes());
						getProxy().finish();
					}

					@Override
					public void onFailure(final Throwable caught) {
						toast.showShortToast("Nie udało się pobrać typów parametrów śledzonych w ramach obserwacji. Proszę spróbować ponownie.");
					}

				});
	}

	private int getPatinetId() {
		return getIntentParameter(SetAdmissionParametersProxy.PATIENT_ID, Integer.class);
	}

	private int getAdmissionId() {
		return getIntentParameter(SetAdmissionParametersProxy.ADMISSION_ID, Integer.class);
	}

	private final ApplicationState appState;

	private final AndroidToast toast;

	private final TaskRunner taskRunner;

}
