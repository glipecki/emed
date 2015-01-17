package eu.anmore.emed.mobi.patientcard.fluidbalance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.patientcard.fluidbalance.GroupBalanceAdapter.BalanceType;
import eu.anmore.emed.mobi.patientcard.table.TableObservationTabView;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.patient.Patient;

public class FluidBalanceTabView {

	public FluidBalanceTabView(final Activity proxy, final ApplicationState appState, final Patient acvivePatient) {
		this.proxy = proxy;
		this.appState = appState;
		this.acvivePatient = acvivePatient;

		this.tabView = getTabView();
		this.progressBar = findViewById(R.id.fluid_balance_progress);
		this.tabelPanel = findViewById(R.id.fluid_balance_panel);
		this.detailsPanel = findViewById(R.id.fluid_balance_balance_details_panel);
		this.totalBalanceDetailsCheckbox = findViewById(R.id.fluid_balance_total_balance_details);

		this.koloidyObservations = findViewById(R.id.koloidy_observations);
		this.drenazObservations = findViewById(R.id.drenaz_observations);
		this.diurezaObservations = findViewById(R.id.diureza_observations);
		this.sondaInObservations = findViewById(R.id.sonda_in_observations);
		this.sondaOutObservations = findViewById(R.id.sonda_out_observations);
		this.bolusyObservations = findViewById(R.id.bolusy_observations);
		this.wlewyObservations = findViewById(R.id.wlewy_observations);
		this.stolecWymiotyObservations = findViewById(R.id.stolecwymioty_observations);

		this.koloidyCheckbox = findViewById(R.id.details_koloidy);
		this.drenazCheckbox = findViewById(R.id.details_drenaz);
		this.duirezaCheckbox = findViewById(R.id.details_diureza);
		this.sondaInCheckbox = findViewById(R.id.details_sonda_in);
		this.sondaOutCheckbox = findViewById(R.id.details_sonda_out);
		this.bolusyCheckbox = findViewById(R.id.details_bolusy);
		this.wlewyCheckbox = findViewById(R.id.details_wlewy);
		this.stolecWymiotyCheckbox = findViewById(R.id.details_stolecwymioty);

		this.totalBalanceField = findViewById(R.id.fluid_balance_balance_total);
		this.koloidyGroupBalance = findViewById(R.id.fluid_balance_balance_koloidy_group);
		this.krystaloidyGroupBalance = findViewById(R.id.fluid_balance_balance_krystaloidy_group);

		this.balanceKoloidy = findViewById(R.id.fluid_balance_balance_koloidy);
		this.balanceDrenaz = findViewById(R.id.fluid_balance_balance_drenaz);
		this.balanceDiureza = findViewById(R.id.fluid_balance_balance_diureza);
		this.balanceSondaIn = findViewById(R.id.fluid_balance_balance_sonda_in);
		this.balanceSondaOut = findViewById(R.id.fluid_balance_balance_sonda_out);
		this.balanceBolusy = findViewById(R.id.fluid_balance_balance_bolusy);
		this.balanceWlewy = findViewById(R.id.fluid_balance_balance_wlewy);

		createView();
		refreshView();
	}

	public View getView() {
		return tabView;
	}

	public void refresh() {
		refreshView();
	}

	public void setObservations(final Patient activePatient, final List<ObservationGroup> types,
			final List<Observation> observations) {
		for (final ObservationGroup type : types) {
			if (observationTabs.containsKey(type)) {
				observationTabs.get(type).setObservations(observations,
						appState.getObservationNorms(activePatient.getBirthday()));
			}
		}
		progressBar.setVisibility(View.GONE);
		tabelPanel.setVisibility(View.VISIBLE);
	}

	private static final class ChangeViewVisibility implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
			view.setVisibility(isChecked ? View.VISIBLE : View.GONE);
		}

		private ChangeViewVisibility(final LinearLayout view) {
			this.view = view;
		}

		private final LinearLayout view;
	}

	private void createView() {
		bindWithViewToHide(totalBalanceDetailsCheckbox, detailsPanel);
		bindWithViewToHide(koloidyCheckbox, koloidyObservations);
		bindWithViewToHide(drenazCheckbox, drenazObservations);
		bindWithViewToHide(duirezaCheckbox, diurezaObservations);
		bindWithViewToHide(sondaInCheckbox, sondaInObservations);
		bindWithViewToHide(sondaOutCheckbox, sondaOutObservations);
		bindWithViewToHide(bolusyCheckbox, bolusyObservations);
		bindWithViewToHide(wlewyCheckbox, wlewyObservations);
		bindWithViewToHide(stolecWymiotyCheckbox, stolecWymiotyObservations);

		final TotalBalanceAdapter totalBalanceAdapter = new TotalBalanceAdapter(totalBalanceField);
		final TotalBalanceAdapter koloidyBalanceAdapter = new TotalBalanceAdapter(totalBalanceAdapter, "koloidyGrupa",
				koloidyGroupBalance);
		final TotalBalanceAdapter krystaloidyBalanceAdapter = new TotalBalanceAdapter(totalBalanceAdapter,
				"krystaloidyGrupa", krystaloidyGroupBalance);
		final Date today = new Date();
		koloidyObservations.addView(getTab(
				ObservationGroup.COLLOIDS,
				new DayBalanceAdapter(koloidyBalanceAdapter, ObservationGroup.COLLOIDS, balanceKoloidy,
						BalanceDirection.PLUS, today)).getView());
		drenazObservations.addView(getTab(
				ObservationGroup.DRAINAGE,
				new DayBalanceAdapter(koloidyBalanceAdapter, ObservationGroup.DRAINAGE, balanceDrenaz,
						BalanceDirection.MINUS, BalanceType.LAST, today)).getView());
		diurezaObservations.addView(getTab(
				ObservationGroup.DIURESIS,
				new DayBalanceAdapter(krystaloidyBalanceAdapter, ObservationGroup.DIURESIS, balanceDiureza,
						BalanceDirection.MINUS, today)).getView());
		sondaInObservations.addView(getTab(
				ObservationGroup.PROBEIN,
				new DayBalanceAdapter(totalBalanceAdapter, ObservationGroup.PROBEIN, balanceSondaIn,
						BalanceDirection.PLUS, today)).getView());
		sondaOutObservations.addView(getTab(
				ObservationGroup.PROBEOUT,
				new DayBalanceAdapter(totalBalanceAdapter, ObservationGroup.PROBEOUT, balanceSondaOut,
						BalanceDirection.MINUS, today)).getView());
		bolusyObservations.addView(getTab(
				ObservationGroup.BOLUSY,
				new DayBalanceAdapter(krystaloidyBalanceAdapter, ObservationGroup.BOLUSY, balanceBolusy,
						BalanceDirection.PLUS, today)).getView());
		wlewyObservations.addView(getTab(
				ObservationGroup.WLEWY,
				new DayBalanceAdapter(krystaloidyBalanceAdapter, ObservationGroup.WLEWY, balanceWlewy,
						BalanceDirection.PLUS, today)).getView());
		stolecWymiotyObservations.addView(getTab(ObservationGroup.STOOLEEMESIS).getView());
	}

	private TableObservationTabView getTab(final ObservationGroup observationGroup) {
		return getTab(observationGroup, null);
	}

	private TableObservationTabView getTab(final ObservationGroup type, final GroupBalanceAdapter balanceAdapter) {
		if (!observationTabs.containsKey(type)) {
			observationTabs.put(type, new TableObservationTabView(proxy, type, appState, true, balanceAdapter,
					acvivePatient));
		}
		return observationTabs.get(type);
	}

	private void bindWithViewToHide(final CheckBox checkbox, final LinearLayout view) {
		view.setVisibility(checkbox.isChecked() ? View.VISIBLE : View.GONE);
		checkbox.setOnCheckedChangeListener(new ChangeViewVisibility(view));
	}

	private void refreshView() {
		tabelPanel.setVisibility(View.GONE);
		detailsPanel.setVisibility(totalBalanceDetailsCheckbox.isChecked() ? View.VISIBLE : View.GONE);
	}

	@SuppressWarnings("unchecked")
	private <T extends View> T findViewById(final Integer resId) {
		return (T) tabView.findViewById(resId);
	}

	private LinearLayout getTabView() {
		final LinearLayout tabLayout = (LinearLayout) proxy.getLayoutInflater().inflate(R.layout.fluid_balance_tab,
				null, false);
		tabLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f));
		return tabLayout;
	}

	private final Patient acvivePatient;

	private final TextView koloidyGroupBalance;

	private final TextView krystaloidyGroupBalance;

	private final TextView balanceKoloidy;

	private final TextView balanceDrenaz;

	private final TextView balanceDiureza;

	private final TextView balanceSondaIn;

	private final TextView balanceSondaOut;

	private final TextView balanceBolusy;

	private final TextView balanceWlewy;

	private final TextView totalBalanceField;

	private final ApplicationState appState;

	private final LinearLayout stolecWymiotyObservations;

	private final CheckBox stolecWymiotyCheckbox;

	private final LinearLayout sondaOutObservations;

	private final CheckBox sondaOutCheckbox;

	private final Map<ObservationGroup, TableObservationTabView> observationTabs = new HashMap<ObservationGroup, TableObservationTabView>();

	private final CheckBox koloidyCheckbox;

	private final CheckBox drenazCheckbox;

	private final CheckBox duirezaCheckbox;

	private final CheckBox sondaInCheckbox;

	private final CheckBox bolusyCheckbox;

	private final CheckBox wlewyCheckbox;

	private final LinearLayout koloidyObservations;

	private final LinearLayout diurezaObservations;

	private final LinearLayout drenazObservations;

	private final LinearLayout sondaInObservations;

	private final LinearLayout bolusyObservations;

	private final LinearLayout wlewyObservations;

	private final LinearLayout detailsPanel;

	private final CheckBox totalBalanceDetailsCheckbox;

	private final LinearLayout tabelPanel;

	private final Activity proxy;

	private final ProgressBar progressBar;

	private final LinearLayout tabView;

}
