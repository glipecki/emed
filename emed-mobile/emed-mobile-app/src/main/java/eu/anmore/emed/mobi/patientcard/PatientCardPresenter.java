package eu.anmore.emed.mobi.patientcard;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiPlaceManager;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.SecurityHelper;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes.LoadCustomObservationTypesResult;
import eu.anmore.emed.mobi.observation.LoadObservationNorms;
import eu.anmore.emed.mobi.observation.LoadObservationNorms.LoadObservationNormsResult;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmissionTypesResult;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmisstionTypesAction;
import eu.anmore.emed.mobi.patientcard.LoadPatientInfoTask.LoadPatientInfoResult;
import eu.anmore.emed.observation.AdmissionObservationType;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.ObservationNorm;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.logger.LogCatLogger;
import eu.anmore.mvpdroid.logger.Logger;
import eu.anmore.mvpdroid.presenter.Presenter;
import eu.anmore.mvpdroid.toast.AndroidToast;
import eu.anmore.mvpdroid.view.View;

public class PatientCardPresenter extends Presenter<PatientCardPresenter.PatientCardView> {

	@Inject
	public PatientCardPresenter(final PatientCardView view, final MobiPlaceManager placeManager,
			final MobiTaskRunner taskRunner, final PatientCardMenuBuilder patientCardMenuBuilder,
			final AndroidToast androidToast, final ObservationTabPresenter observationTabPresenter,
			final SecurityHelper securityHelper, final ApplicationState appState) {
		super(view);
		this.placeManager = placeManager;
		this.taskRunner = taskRunner;
		this.patientCardMenuBuilder = patientCardMenuBuilder;
		this.androidToast = androidToast;
		this.securityHelper = securityHelper;

		this.observationTabPresenter = observationTabPresenter;
		this.appState = appState;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu, final MenuInflater menuInflater) {
		final int patientId = getPatientId();
		final int admissionId = getAdmissionId();
		return patientCardMenuBuilder.onMenuCreate(menu, new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				observationTabPresenter.refreshView();
				return true;
			}
		}, patientId, admissionId);
	}

	public void setPatient(final Patient patient) {
		// TODO (glipecki): check if patient has admissions.
		getView().setPatient(patient);
		getView().hideLoadingMark();

		observationTabPresenter.setActivePatient(patient);
	}

	public interface PatientCardView extends View {

		void setPatient(Patient patient);

		void setObservationTab(android.view.View view);

		void hideLoadingMark();

		void showLoadingMark();

		Context getContext();

		void setObservationGroupSelectionListener(OnClickListener onClickListener);

		void setAddObservationsClickHandler(OnClickListener addObservationsClickHandler);

	}

	public class AsyncActionSynchronization {

		public void setObservationNorms(final List<ObservationNorm> norms) {
			synchronized (this) {
				this.norms = norms;
				goWhenReady();
			}
		}

		public void setPatientId(final int patientId) {
			synchronized (this) {
				this.patientId = patientId;
				goWhenReady();
			}
		}

		public void setCustomObservationTypes(final List<CustomObservationType> customObservationTypes) {
			synchronized (this) {
				this.customObservationTypes = customObservationTypes;
				goWhenReady();
			}
		}

		public void setAdmissionTypes(final List<AdmissionObservationType> admissionTypes) {
			synchronized (this) {
				this.admissionTypes = admissionTypes;
				goWhenReady();
			}
		}

		public void skipCustomObservationTypes() {
			synchronized (this) {
				this.customObservationTypes = new ArrayList<CustomObservationType>();
				goWhenReady();
			}
		}

		public void skipObservationNorms() {
			synchronized (this) {
				this.norms = new ArrayList<ObservationNorm>();
				goWhenReady();
			}
		}

		public void skipAdmissionTypes() {
			synchronized (this) {
				this.admissionTypes = new ArrayList<AdmissionObservationType>();
				goWhenReady();
			}
		}

		private void goWhenReady() {
			synchronized (this) {
				if (norms != null && patientId != null && customObservationTypes != null && admissionTypes != null) {
					appState.setObservationNorms(norms);
					appState.setCustomObservationTypes(customObservationTypes);
					appState.putPatientAdmissionObservationTypes(patientId, admissionTypes);
					taskRunner.executeWithRetry(LoadPatientInfoTask.action(patientId), new LoadPatientInfoCallback(
							PatientCardPresenter.this, placeManager, androidToast));
				}
			}
		}

		private List<CustomObservationType> customObservationTypes = null;

		private List<ObservationNorm> norms = null;

		private List<AdmissionObservationType> admissionTypes = null;

		private Integer patientId = null;

	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		observationTabPresenter.setProxy(getProxy());
		observationTabPresenter.setPatientCardView(getView());
		observationTabPresenter.onCreate();

		getView().showLoadingMark();
		final int patientId = getPatientId();

		final AsyncActionSynchronization sync = new AsyncActionSynchronization();
		sync.setPatientId(patientId);
		taskRunner.executeWithRetry(LoadObservationNorms.action(), new TaskCallback<LoadObservationNormsResult>() {

			@Override
			public void onSuccess(final LoadObservationNormsResult taskResult) {
				sync.setObservationNorms(taskResult.getNorms());
			}

			@Override
			public void onFailure(final Throwable caught) {
				androidToast.showShortToast("Nie udało się pobrać norm dla obserwacji. Ostrzeżenie nie będą dostępne.");
				sync.skipObservationNorms();
			}

		});
		taskRunner.executeWithRetry(LoadCustomObservationTypes.action(),
				new TaskCallback<LoadCustomObservationTypesResult>() {

					@Override
					public void onSuccess(final LoadCustomObservationTypes.LoadCustomObservationTypesResult taskResult) {
						sync.setCustomObservationTypes(taskResult.getTypes());
					}

					@Override
					public void onFailure(final Throwable caught) {
						androidToast
								.showShortToast("Nie udało się pobrać typów obserwacji użytkownika. Zbiór parametrów jest ograniczony do predefiniowanych.");
						sync.skipCustomObservationTypes();
					}

				});
		taskRunner.executeWithRetry(new LoadAdmisstionTypesAction(patientId),
				new TaskCallback<LoadAdmissionTypesResult>() {

					@Override
					public void onSuccess(final LoadAdmissionTypesResult taskResult) {
						sync.setAdmissionTypes(taskResult.getAdmissionTypes());
					}

					@Override
					public void onFailure(final Throwable caught) {
						androidToast
								.showShortToast("Nie udało się pobrać typów parametrów śledzonych w ramach obserwacji. Proszę spróbować ponownie.");
						sync.skipAdmissionTypes();
					}

				});
	}

	@Override
	protected void onResume() {
		if (securityHelper.verifyAuthentication()) {
			observationTabPresenter.refreshView();
		}
	}

	@Override
	protected boolean onBackPressed() {
		return true;
	}

	private static final class LoadPatientInfoCallback implements TaskCallback<LoadPatientInfoResult> {
		public LoadPatientInfoCallback(final PatientCardPresenter patientCardPresenter,
				final MobiPlaceManager placeManager, final AndroidToast androidToast) {
			this.patientCardPresenter = patientCardPresenter;
			this.placeManager = placeManager;
			this.androidToast = androidToast;
		}

		@Override
		public void onSuccess(final LoadPatientInfoResult taskResult) {
			patientCardPresenter.setPatient(taskResult.getPatient());
		}

		@Override
		public void onFailure(final Throwable caught) {
			log.error("Can't download patient info [{}]", caught.getMessage());
			androidToast.showShortToast(R.string.err_cant_get_patient_info);
			placeManager.openPatientList();
		}

		private static final Logger log = LogCatLogger.getLogger(LoadPatientInfoCallback.class);

		private final MobiPlaceManager placeManager;

		private final AndroidToast androidToast;

		private final PatientCardPresenter patientCardPresenter;
	}

	private int getPatientId() {
		return getIntentParameter(PatientCardProxy.PATIENT_ID, Integer.class);
	}

	private int getAdmissionId() {
		return getIntentParameter(PatientCardProxy.ADMISSION_ID, Integer.class);
	}

	private final ApplicationState appState;

	private final SecurityHelper securityHelper;

	private final ObservationTabPresenter observationTabPresenter;

	private final AndroidToast androidToast;

	private final MobiPlaceManager placeManager;

	private final PatientCardMenuBuilder patientCardMenuBuilder;

	private final MobiTaskRunner taskRunner;

}
