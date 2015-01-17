package eu.anmore.emed.mobi.patientcard;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.MobiPlaceManager;

public final class PatientCardMenuBuilder {

	@Inject
	public PatientCardMenuBuilder(final MobiPlaceManager placeManager) {
		this.placeManager = placeManager;
	}

	public boolean onMenuCreate(final Menu menu, final OnMenuItemClickListener refreshMenuClicked,
			final int patientId, final int admissionId) {
		// TODO (glipecki): refactor: duplicated code
		addPatientsListMenuOption(menu);
		addLogoutMenuOption(menu);
		addRefreshMenuOption(menu, refreshMenuClicked);
		addSetAdmissionParameters(menu, patientId, admissionId);
		return true;
	}

	private void addRefreshMenuOption(final Menu menu, final OnMenuItemClickListener refreshMenuClicked) {
		final MenuItem refreshMenuOption = menu.add("Odśwież");
		refreshMenuOption.setOnMenuItemClickListener(refreshMenuClicked);
	}

	private void addSetAdmissionParameters(final Menu menu, final int patientId, final int admissionId) {
		final MenuItem setAdmissionParametersMenuOption = menu.add("Ustaw śledzone parametry");
		setAdmissionParametersMenuOption.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				placeManager.openSetAdmissionParameters(patientId, admissionId);
				return true;
			}
		});
	}

	private void addPatientsListMenuOption(final Menu menu) {
		// TODO (glipecki): l18n
		final MenuItem patientsListMenuEntry = menu.add("Lista pacjentów");
		patientsListMenuEntry.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		patientsListMenuEntry.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(final MenuItem item) {
				placeManager.openPatientList();
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

	private final MobiPlaceManager placeManager;

}