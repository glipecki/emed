package eu.anmore.emed.mobi.patientcard.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.patientcard.fluidbalance.GroupBalanceAdapter;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationNorm;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.observation.PredefinedObservationType;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.widget.builder.TextViewBuilder;
import eu.anmore.mvpdroid.widget.builder.WidgetBuilder;

public class TableObservationTabView {

	public static TextView getTextView(final WidgetBuilder wb, final String text, final Context context) {
		return getStandardTableCellText(wb.textView().text(Html.fromHtml(text)), context).get();
	}

	public static TextView getTextView(final WidgetBuilder wb, final int textRef, final Context context) {
		return getStandardTableCellText(wb.textView().text(textRef), context).get();
	}

	public static TextViewBuilder getStandardTableCellText(final TextViewBuilder viewBuilder, final Context context) {
		return viewBuilder.textSize(16).padding(5, 10, 5, 10);
	}

	public TableObservationTabView(final Activity activityProxy, final ObservationGroup observationsGroup,
			final ApplicationState appState, final boolean seamlessParameterWidth, final Patient activePatient) {
		this.activityProxy = activityProxy;
		this.observationsGroup = observationsGroup;
		this.appState = appState;
		this.seamlessParameterWidth = seamlessParameterWidth;
		this.activePatient = activePatient;
		this.customTypes = appState.getCustomObservationTypes();

		this.wb = new WidgetBuilder(activityProxy);
		this.hoursRow = wb.tableRow().get();
	}

	public TableObservationTabView(final Activity activityProxy, final ObservationGroup observationsGroup,
			final ApplicationState appState, final boolean seamlessParameterWidth,
			final GroupBalanceAdapter balanceAdapter, final Patient activePatient) {
		this(activityProxy, observationsGroup, appState, seamlessParameterWidth, activePatient);
		this.balanceAdapter = balanceAdapter;
	}

	public View getView() {
		tabView = getTabView();
		loadObservationProgressBar = (ProgressBar) tabView.findViewById(R.id.table_observation_progress);
		final List<ObservationType> observationTypes = createParametersPanel(tabView);
		final Map<ObservationType, TableRow> observationRows = createObservationsPanel(tabView, hoursRow,
				observationTypes);
		observationsDrawer = new TableObservationsDrawer(observationTypes, hoursRow, observationRows, activityProxy,
				seamlessParameterWidth, appState, activePatient);
		return tabView;
	}

	public void setObservations(final List<Observation> observations, final List<ObservationNorm> observationNorms) {
		final HorizontalScrollView scv = (HorizontalScrollView) tabView.findViewById(R.id.table_scroll_view);
		scv.post(new Runnable() {
			@Override
			public void run() {
				observationsDrawer.setObservations(observations, getAsNormsMap(observationNorms));
				setBalance(observations);
				scv.post(new Runnable() {
					@Override
					public void run() {
						scv.fullScroll(View.FOCUS_RIGHT);
						scv.post(new Runnable() {
							@Override
							public void run() {
								loadObservationProgressBar.setVisibility(View.GONE);
							}
						});
					}
				});
			}

		});
	}

	private static final int SEAMLESS_WIDTH = 150;

	private void setBalance(final List<Observation> observations) {
		if (balanceAdapter != null) {
			balanceAdapter.setObservations(observations);
		}
	}

	private Map<String, ObservationNorm> getAsNormsMap(final List<ObservationNorm> observationNorms) {
		final Map<String, ObservationNorm> map = new HashMap<String, ObservationNorm>();
		if (observationNorms != null) {
			for (final ObservationNorm observationNorm : observationNorms) {
				map.put(observationNorm.getObservationType().getKey(), observationNorm);
			}
		}
		return map;
	}

	private LinearLayout getTabView() {
		final LinearLayout tabLayout = (LinearLayout) activityProxy.getLayoutInflater().inflate(
				R.layout.table_observations_tab, null, false);
		tabLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f));
		return tabLayout;
	}

	private Map<ObservationType, TableRow> createObservationsPanel(final LinearLayout tabView, final TableRow hoursRow,
			final List<ObservationType> observationTypes) {
		final LinearLayout observationsView = (LinearLayout) tabView.findViewById(R.id.table_tab_observations);

		final TableLayout table = wb.table().get();
		observationsView.addView(table);

		table.addView(hoursRow);

		final Map<ObservationType, TableRow> observationRows = new HashMap<ObservationType, TableRow>();
		for (final ObservationType observationType : observationTypes) {
			observationRows.put(observationType, wb.tableRow().get());
			table.addView(observationRows.get(observationType));
		}
		return observationRows;
	}

	private List<ObservationType> createParametersPanel(final LinearLayout tabView) {
		final WidgetBuilder wb = new WidgetBuilder(activityProxy);
		final LinearLayout parametersLayout = (LinearLayout) tabView.findViewById(R.id.table_tab_parameters);

		if (seamlessParameterWidth) {
			parametersLayout.getLayoutParams().width = SEAMLESS_WIDTH;
		}

		// parameters table
		final TableLayout table = wb.table().get();
		parametersLayout.addView(table);

		// hours row place holder
		final TableRow hoursTableRow = wb.tableRow().get();
		final TextView hoursTextView = getTextView(wb, R.string.hours, activityProxy);
		if (seamlessParameterWidth) {
			hoursTextView.setWidth(SEAMLESS_WIDTH);
		} else {
			final android.widget.TableRow.LayoutParams lpar = new TableRow.LayoutParams(
					android.widget.TableRow.LayoutParams.MATCH_PARENT,
					android.widget.TableRow.LayoutParams.WRAP_CONTENT);
			lpar.gravity = Gravity.LEFT;
			hoursTextView.setLayoutParams(lpar);
		}
		hoursTableRow.addView(hoursTextView);
		table.addView(hoursTableRow);

		// observation titles
		final List<ObservationType> observationTypes = new ArrayList<ObservationType>();
		for (final PredefinedObservationType observationType : appState.filterPredefinedObservationTypes(
				activePatient.getId(), observationsGroup.getObservationTypes())) {
			observationTypes.add(observationType);

			final TableRow observationTypeRow = wb.tableRow().get();
			table.addView(observationTypeRow);

			final TextView observationTypeText = getTextView(wb, observationType.getVisibleName(), activityProxy);

			final android.widget.TableRow.LayoutParams lpar = new TableRow.LayoutParams(
					android.widget.TableRow.LayoutParams.MATCH_PARENT,
					android.widget.TableRow.LayoutParams.WRAP_CONTENT);
			lpar.gravity = Gravity.LEFT;
			observationTypeText.setLayoutParams(lpar);

			observationTypeRow.addView(observationTypeText);
		}
		if (customTypes.containsKey(observationsGroup)) {
			for (final CustomObservationType customObservationType : appState.filterCustomObservationTypes(
					activePatient.getId(), customTypes.get(observationsGroup))) {
				observationTypes.add(customObservationType);

				final TableRow observationTypeRow = wb.tableRow().get();
				table.addView(observationTypeRow);

				final TextView observationTypeText = getTextView(wb, customObservationType.getVisibleName(),
						activityProxy);

				final android.widget.TableRow.LayoutParams lpar = new TableRow.LayoutParams(
						android.widget.TableRow.LayoutParams.MATCH_PARENT,
						android.widget.TableRow.LayoutParams.WRAP_CONTENT);
				lpar.gravity = Gravity.LEFT;
				observationTypeText.setLayoutParams(lpar);

				observationTypeRow.addView(observationTypeText);
			}
		}
		return observationTypes;
	}

	private final ApplicationState appState;

	private final Patient activePatient;

	private GroupBalanceAdapter balanceAdapter;

	private final boolean seamlessParameterWidth;

	private final Map<ObservationGroup, List<CustomObservationType>> customTypes;

	private final ObservationGroup observationsGroup;

	private final Activity activityProxy;

	private TableObservationsDrawer observationsDrawer;

	private final WidgetBuilder wb;

	private ProgressBar loadObservationProgressBar;

	private final TableRow hoursRow;

	private LinearLayout tabView;

}
