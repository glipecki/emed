package eu.anmore.emed.mobi.patients;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiPlaceManager;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.SecurityHelper;
import eu.anmore.emed.mobi.patients.LoadPatientsTask.LoadPatientsResult;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.presenter.Presenter;
import eu.anmore.mvpdroid.task.runner.TaskRunner;
import eu.anmore.mvpdroid.view.View;

/**
 * Currently admitted patients screen presenter.
 * 
 * @author Grzegorz Lipecki
 */
public class PatientListPresenter extends Presenter<PatientListPresenter.PatientListView> {

	@Inject
	public PatientListPresenter(final PatientListView view, final Activity context,
			final MobiPlaceManager placeManager, final MobiTaskRunner taskRunner,
			final PatientsGridAdapter patientsGridAdapter, final ApplicationState applicationState,
			final SecurityHelper securityHelper) {
		super(view);
		this.placeManager = placeManager;
		this.taskRunner = taskRunner;
		this.patientsGridAdapter = patientsGridAdapter;
		this.applicationState = applicationState;
		this.securityHelper = securityHelper;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getView().setPatientsGridAdapter(patientsGridAdapter);
		getView().setPatientSelectionListener(new PatientSelectionListener(this));

		getView().showLoadingMark();
		taskRunner.executeWithRetry(LoadPatientsTask.action(), new LoadPatientsCallback(this));
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu, final MenuInflater menuInflater) {
		// TODO (glipecki): refactor: duplicated code
		addRefreshMenuOption(menu);
		// addCustomParametersMenuOption(menu);
		addLogoutMenuOption(menu);
		return true;
	}

	public void setPatients(final List<Patient> patients) {
		patientsGridAdapter.clear();
		patientsGridAdapter.addAll(patients);
		getView().showPatientsGrid();
	}

	public void onPatientSelected(final Patient patient) {
		placeManager.openPatientCard(patient);
	}

	public interface PatientListView extends View {

		void setPatientsGridAdapter(PatientsGridAdapter patientsGridAdapter);

		void showLoadingMark();

		void showPatientsGrid();

		void setPatientSelectionListener(final OnItemClickListener patientSelectionListener);

	}

	@Override
	protected void onResume() {
		securityHelper.verifyAuthentication();
	}

	@Override
	protected boolean onBackPressed() {
		return true;
	}

	private static final class PatientSelectionListener implements OnItemClickListener {

		public PatientSelectionListener(final PatientListPresenter patientListPresenter) {
			this.presenter = patientListPresenter;
		}

		@Override
		public void onItemClick(final AdapterView<?> parent, final android.view.View view, final int position,
				final long id) {
			final Object patient = parent.getItemAtPosition(position);
			if (patient instanceof Patient) {
				presenter.onPatientSelected((Patient) patient);
			} else {
				throw new RuntimeException("Something wrong happend, expected Patient from list, was: " + patient);
			}
		}

		private final PatientListPresenter presenter;
	}

	private static class LoadPatientsCallback implements TaskCallback<LoadPatientsResult> {

		public LoadPatientsCallback(final PatientListPresenter patientListPresenter) {
			this.presenter = patientListPresenter;
		}

		@Override
		public void onSuccess(final LoadPatientsResult taskResult) {
			presenter.setPatients(taskResult.getPatients());
		}

		@Override
		public void onFailure(final Throwable caught) {
			// TODO (glipecki): stub
		}

		private final PatientListPresenter presenter;

	}

	private void addCustomParametersMenuOption(final Menu menu) {
		final MenuItem customParametersMenuItem = menu.add("Parametry użytkownika");
		customParametersMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				taskRunner.executeWithRetry(LoadPatientsTask.action(), new LoadPatientsCallback(
						PatientListPresenter.this));
				placeManager.openCustomParameters();
				return true;
			}
		});
	}

	private void addLogoutMenuOption(final Menu menu) {
		final MenuItem logoutMenuEntry = menu.add("Zakończ pracę");
		logoutMenuEntry.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		logoutMenuEntry.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				placeManager.logoutCurrentUser();
				return true;
			}
		});
	}

	private void addRefreshMenuOption(final Menu menu) {
		// TODO (Grzegorz Lipecki): l18n
		final MenuItem refreshMenuItem = menu.add("Odśwież");
		// TODO (Grzegorz Lipecki): handle this cute refresh 'correct way'
		refreshMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				getView().showLoadingMark();
				taskRunner.executeWithRetry(LoadPatientsTask.action(), new LoadPatientsCallback(
						PatientListPresenter.this));
				return true;
			}
		});
	}

	private final SecurityHelper securityHelper;

	private final ApplicationState applicationState;

	private final MobiPlaceManager placeManager;

	private final PatientsGridAdapter patientsGridAdapter;

	private final TaskRunner taskRunner;

}
