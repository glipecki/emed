package eu.anmore.emed.mobi.patientcard.table;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.view.View;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.patientcard.ObservationTab;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask.GetObservationsResult;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskCallback;

public abstract class OneGroupTableObservationTab implements ObservationTab {

	public OneGroupTableObservationTab(final MobiTaskRunner taskRunner, final ApplicationState appState) {
		this.taskRunner = taskRunner;
		this.appState = appState;
		this.ready = false;
	}

	@Override
	public View getView(final Activity activityProxy, final Patient acvivePatient) {
		tabView = new TableObservationTabView(activityProxy, getObservationGroup(), appState, false, acvivePatient);
		return tabView.getView();
	}

	@Override
	public void refreshView(final Patient activePatient) {
		this.ready = false;
		taskRunner.executeWithRetry(GetObservationsTask.action(activePatient.getId(), getObservationGroup().getKey()),
				new TaskCallback<GetObservationsResult>() {

					@Override
					public void onSuccess(final GetObservationsResult taskResult) {
						tabView.setObservations(taskResult.getObservations(),
								appState.getObservationNorms(activePatient.getBirthday()));
						OneGroupTableObservationTab.this.ready = true;
					}

					@Override
					public void onFailure(final Throwable caught) {
						OneGroupTableObservationTab.this.ready = true;
					}

				});
	}

	@Override
	public List<ObservationGroup> getObservationGroups() {
		return Arrays.asList(getObservationGroup());
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	public TableObservationTabView getTabView() {
		return tabView;
	}

	public MobiTaskRunner getTaskRunner() {
		return taskRunner;
	}

	public ApplicationState getAppState() {
		return appState;
	}

	protected abstract ObservationGroup getObservationGroup();

	private boolean ready;

	private final MobiTaskRunner taskRunner;

	private final ApplicationState appState;

	private TableObservationTabView tabView;

}
