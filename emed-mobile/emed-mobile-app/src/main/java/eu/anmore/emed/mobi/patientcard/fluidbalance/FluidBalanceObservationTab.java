package eu.anmore.emed.mobi.patientcard.fluidbalance;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.patientcard.ObservationTab;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask.GetObservationsResult;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskCallback;

public class FluidBalanceObservationTab implements ObservationTab {

	@Inject
	public FluidBalanceObservationTab(final MobiTaskRunner taskRunner, final ApplicationState appState) {
		this.taskRunner = taskRunner;
		this.appState = appState;
		this.ready = false;
	}

	@Override
	public View getView(final Activity activityProxy, final Patient acvivePatient) {
		tabView = new FluidBalanceTabView(activityProxy, appState, acvivePatient);
		return tabView.getView();
	}

	@Override
	public void refreshView(final Patient activePatient) {
		this.ready = false;
		loadObservationsFor(activePatient);
	}

	@Override
	public List<ObservationGroup> getObservationGroups() {
		return Arrays.asList(ObservationGroup.COLLOIDS, ObservationGroup.DIURESIS, ObservationGroup.DRAINAGE,
				ObservationGroup.PROBEIN, ObservationGroup.PROBEOUT, ObservationGroup.BOLUSY, ObservationGroup.WLEWY,
				ObservationGroup.STOOLEEMESIS);
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	private void loadObservationsFor(final Patient activePatient) {
		final List<ObservationGroup> groups = getObservationGroups();
		taskRunner.executeWithRetry(GetObservationsTask.action(activePatient.getId(), groups), new UpdateObservations(
				groups, activePatient));
	}

	private boolean ready;

	private final MobiTaskRunner taskRunner;

	private final ApplicationState appState;

	private FluidBalanceTabView tabView;

	private final class UpdateObservations implements TaskCallback<GetObservationsResult> {

		@Override
		public void onSuccess(final GetObservationsResult taskResult) {
			tabView.setObservations(activePatient, type, taskResult.getObservations());
			FluidBalanceObservationTab.this.ready = true;
		}

		@Override
		public void onFailure(final Throwable caught) {
			FluidBalanceObservationTab.this.ready = true;
		}

		private UpdateObservations(final List<ObservationGroup> groups, final Patient activePatient) {
			this.type = groups;
			this.activePatient = activePatient;
		}

		private final Patient activePatient;

		private final List<ObservationGroup> type;

	}

}
