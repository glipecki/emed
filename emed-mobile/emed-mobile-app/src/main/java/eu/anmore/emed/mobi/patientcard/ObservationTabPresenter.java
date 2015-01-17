package eu.anmore.emed.mobi.patientcard;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiPlaceManager;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.patientcard.PatientCardPresenter.PatientCardView;
import eu.anmore.emed.mobi.patientcard.bloodchemistry.BloodChemistryObservationTab;
import eu.anmore.emed.mobi.patientcard.cbc.CbcObservationTab;
import eu.anmore.emed.mobi.patientcard.coagulationpanel.CoagulationPanelObservationTab;
import eu.anmore.emed.mobi.patientcard.fluidbalance.FluidBalanceObservationTab;
import eu.anmore.emed.mobi.patientcard.gasometry.GasometryObservationTab;
import eu.anmore.emed.mobi.patientcard.hemodynamics.HemodynamicsObservationTab;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.logger.LogCatLogger;
import eu.anmore.mvpdroid.logger.Logger;

public class ObservationTabPresenter {

	@Inject
	public ObservationTabPresenter(final ApplicationState appState,
			final HemodynamicsObservationTab hemodynamicsObservationTab,
			final FluidBalanceObservationTab fluidBalanceObservationTab,
			final GasometryObservationTab gasometryObservationTab, final CbcObservationTab cbcObservationTab,
			final BloodChemistryObservationTab bloodChemistryObservationTab,
			final CoagulationPanelObservationTab coagulationPanelObservationTab, final MobiPlaceManager placeManager) {
		this.appState = appState;
		this.hemodynamicsObservationTab = hemodynamicsObservationTab;
		this.fluidBalanceObservationTab = fluidBalanceObservationTab;
		this.gasometryObservationTab = gasometryObservationTab;
		this.cbcObservationTab = cbcObservationTab;
		this.bloodChemistryObservationTab = bloodChemistryObservationTab;
		this.coagulationPanelObservationTab = coagulationPanelObservationTab;
		this.placeManager = placeManager;
	}

	public void onCreate() {
		activeObservationTab = hemodynamicsObservationTab;
	}

	public void setProxy(final Activity proxy) {
		this.proxy = proxy;
	}

	public void refreshView() {
		if (activeObservationTab != null && activePatient != null) {
			log.debug("> Refreshing view [activeTab={}, activePatient={}]", activeObservationTab, activePatient);
			patientCardView.setObservationTab(getView(activePatient));
			activeObservationTab.refreshView(activePatient);
			log.debug("< view refreshed");
		}
	}

	protected void showAddObservationsDialog() {
		// placeManager.openAddObservation(activePatient.getId(),
		// Arrays.asList(HR, SAT, ABP, CVP, PAP, LAP, CO));
		final List<ObservationType> observationTypes = getObservationTypes(activeObservationTab.getObservationGroups());
		placeManager.openAddObservation(activePatient.getId(), observationTypes);
	}

	void setActivePatient(final Patient activePatient) {
		this.activePatient = activePatient;
		refreshView();
	}

	View getView(final Patient activePatient) {
		return activeObservationTab.getView(proxy, activePatient);
	}

	void setPatientCardView(final PatientCardView patientCardView) {
		this.patientCardView = patientCardView;
		patientCardView.setObservationGroupSelectionListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				if (view instanceof Button) {
					final Button button = (Button) view;
					log.info("User choosed new observations tab [{}]", button.getText().toString());
					switch (button.getId()) {
					case R.id.patient_card_hemodynamics:
						setActiveObservationTab(hemodynamicsObservationTab);
						break;
					case R.id.patient_card_gasometry:
						setActiveObservationTab(gasometryObservationTab);
						break;
					case R.id.patient_card_fluidbalance:
						setActiveObservationTab(fluidBalanceObservationTab);
						break;
					case R.id.patient_card_cbc:
						setActiveObservationTab(cbcObservationTab);
						break;
					case R.id.patient_card_bloodchemistry:
						setActiveObservationTab(bloodChemistryObservationTab);
						break;
					case R.id.patient_card_coagulationpanel:
						setActiveObservationTab(coagulationPanelObservationTab);
						break;
					}
				}
			}

		});
		patientCardView.setAddObservationsClickHandler(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				showAddObservationsDialog();
			}

		});
	}

	private static final Logger log = LogCatLogger.getLogger(ObservationTabPresenter.class);

	private List<ObservationType> getObservationTypes(final List<ObservationGroup> observationGroups) {
		final List<ObservationType> types = new ArrayList<ObservationType>();
		for (final ObservationGroup group : observationGroups) {
			types.addAll(group.getObservationTypes());
			types.addAll(appState.getCustomObservationTypes(group));
		}
		return types;
	}

	private void setActiveObservationTab(final ObservationTab observationTab) {
		log.debug("> Changing active tab [old={}, new={}]", activeObservationTab, observationTab);
		if (observationTab != activeObservationTab && activeObservationTab.isReady()) {
			activeObservationTab = observationTab;
			refreshView();
			log.debug("< active tab changed [{}]", activeObservationTab);
		} else {
			log.debug("< nothing to change");
		}
	}

	private final ApplicationState appState;

	private final MobiPlaceManager placeManager;

	private final HemodynamicsObservationTab hemodynamicsObservationTab;

	private final FluidBalanceObservationTab fluidBalanceObservationTab;

	private final GasometryObservationTab gasometryObservationTab;

	private final CbcObservationTab cbcObservationTab;

	private final BloodChemistryObservationTab bloodChemistryObservationTab;

	private final CoagulationPanelObservationTab coagulationPanelObservationTab;

	private PatientCardView patientCardView;

	private Activity proxy;

	private ObservationTab activeObservationTab;

	private Patient activePatient;

}
