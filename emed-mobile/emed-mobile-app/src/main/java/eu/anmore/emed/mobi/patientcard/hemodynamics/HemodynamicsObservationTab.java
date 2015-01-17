package eu.anmore.emed.mobi.patientcard.hemodynamics;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static eu.anmore.emed.observation.PredefinedObservationType.CO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.patientcard.ObservationTab;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask.GetObservationsResult;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.observation.PredefinedObservationType;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.logger.LogCatLogger;
import eu.anmore.mvpdroid.logger.Logger;
import eu.anmore.mvpdroid.toast.AndroidToast;
import eu.anmore.utils.DateUtil;

public class HemodynamicsObservationTab implements ObservationTab {

	@Inject
	public HemodynamicsObservationTab(final MobiTaskRunner taskRunner, final AndroidToast androidToast,
			final ApplicationConfigurationFacade config, final ApplicationState appState) {
		this.taskRunner = taskRunner;
		this.androidToast = androidToast;
		this.config = config;
		this.appState = appState;
		this.ready = false;
	}

	@Override
	public View getView(final Activity activityProxy, final Patient activePatient) {
		this.activityProxy = activityProxy;

		container = new LinearLayout(activityProxy);
		container.setOrientation(LinearLayout.VERTICAL);
		container.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

		container.addView(createHeader(activityProxy));
		container.addView(createMainView(activityProxy, activePatient));

		container.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		return container;
	}

	@Override
	public void refreshView(final Patient activePatient) {
		this.ready = false;
		taskRunner.executeWithRetry(
				GetObservationsTask.action(activePatient.getId(), ObservationGroup.HEMODYNAMICS.getKey()),
				new TaskCallback<GetObservationsTask.GetObservationsResult>() {

					@Override
					public void onSuccess(final GetObservationsResult taskResult) {
						drawChart(appState.filterObservations(activePatient.getId(), taskResult.getObservations()));
						HemodynamicsObservationTab.this.ready = true;
					}

					@Override
					public void onFailure(final Throwable caught) {
						androidToast.showShortToast(R.string.get_observations_error);
						HemodynamicsObservationTab.this.ready = true;
					}
				});
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public List<ObservationGroup> getObservationGroups() {
		return Arrays.asList(ObservationGroup.HEMODYNAMICS);
	}

	private static final int MAX_Y_VALUE = 250;

	private static final int AN_HOUR = 1000 * 60 * 60;

	private static final Logger log = LogCatLogger.getLogger(HemodynamicsObservationTab.class);

	private void drawChart(final List<Observation> observations) {
		log.info("> Redraw chart");

		Collections.sort(observations, new Comparator<Observation>() {

			@Override
			public int compare(final Observation lhs, final Observation rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}

		});
		this.observations = observations;

		chartView.removeAllViews();
		chartView.addView(getObservationsChart(observations));

		log.info("< chart redrawed");
	}

	private GraphicalView getObservationsChart(final List<Observation> observations) {
		Collections.sort(observations, new Comparator<Observation>() {

			@Override
			public int compare(final Observation lhs, final Observation rhs) {
				return lhs.getDate().compareTo(rhs.getDate());
			}

		});

		final LocalDate startDate = !observations.isEmpty() ? new LocalDate(DateUtil.getAsDayStart(observations.get(0)
				.getDate())) : new LocalDate();
		final Date endDate = new Date();

		final Map<String, XYSeries> series = getObservationsAsSeries(observations);
		final boolean multipleScales = series.containsKey(PredefinedObservationType.CO.getKey());
		final XYMultipleSeriesRenderer renderer = getChartRenderer(multipleScales, startDate, endDate);
		final XYMultipleSeriesDataset dataset = getChartDataset(series, renderer);

		return getTimeChart(dataset, renderer, "HH:mm");
	}

	private Map<String, XYSeries> getObservationsAsSeries(final List<Observation> observations) {
		final Map<String, XYSeries> series = new HashMap<String, XYSeries>();
		for (final Observation observation : observations) {
			final ObservationType observationType = appState.getObservationTypeFor(observation.getTypeKey());
			final String observationTypeKey = observationType.getKey();
			if (!series.containsKey(observationTypeKey)) {
				series.put(
						observationTypeKey,
						new CustomXYSeries(observationType.getVisibleName(), observationTypeKey
								.equals(PredefinedObservationType.CO.getKey()) ? 1 : 0));
			}
			series.get(observationTypeKey).add(observation.getDate().getTime(),
					observationType.getGraphRawValue(observation.getValue()));
		}
		return series;
	}

	private XYMultipleSeriesDataset getChartDataset(final Map<String, XYSeries> series,
			final XYMultipleSeriesRenderer renderer) {
		final XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (final Entry<String, XYSeries> entry : series.entrySet()) {
			final XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
			xySeriesRenderer.setColor(HemodynamicsParameterTypeColors.getColor(entry.getKey()));
			xySeriesRenderer.setLineWidth(config.getChartLineWidth());
			xySeriesRenderer.setDisplayChartValues(true);
			xySeriesRenderer.setDisplayChartValuesDistance(0);
			xySeriesRenderer.setChartValuesTextSize(config.getChartValuesTextSize());
			xySeriesRenderer.setDisplayBoundingPoints(true);
			xySeriesRenderer.setPointStrokeWidth(config.getChartPointWidth());
			xySeriesRenderer.setPointStyle(config.getChartPointStyle());
			renderer.addSeriesRenderer(xySeriesRenderer);
			dataset.addSeries(entry.getValue());
		}
		return dataset;
	}

	private XYMultipleSeriesRenderer getChartRenderer(final boolean multipleScales, final LocalDate startDate,
			final Date endDate) {
		final XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(multipleScales ? 2 : 1);
		renderer.setAntialiasing(true);
		renderer.setShowGrid(true);
		renderer.setShowLabels(true);
		renderer.setYLabelsPadding(15f);
		renderer.setXRoundedLabels(false);
		renderer.setAxisTitleTextSize(config.getChartAxisTextSize());
		renderer.setLabelsTextSize(config.getChartLablesTextSize());
		renderer.setYAxisMin(-5);
		renderer.setYAxisMax(MAX_Y_VALUE);
		renderer.setXAxisMin(DateTime.now().minusDays(1).getMillis());
		renderer.setXAxisMax(DateTime.now().plusHours(2).getMillis());
		if (multipleScales) {
			renderer.setYAxisMin(-1, 1);
			renderer.setYAxisMax(6, 1);
			renderer.setXAxisMin(DateTime.now().minusDays(1).getMillis(), 1);
			renderer.setXAxisMax(DateTime.now().plusHours(2).getMillis(), 1);

			renderer.setYLabelsColor(1, HemodynamicsParameterTypeColors.getColor(CO));
		}
		renderer.setShowLegend(false);

		final List<LocalDate> dates = new ArrayList<LocalDate>();
		final int days = Days.daysBetween(startDate, new LocalDate(endDate)).getDays() + 1;
		for (int i = 0; i < days; i++) {
			final LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
			dates.add(d);
		}
		for (final LocalDate localDate : dates) {
			renderer.addXTextLabel(localDate.toDate().getTime(), daySdf.format(localDate.toDate()));
		}
		renderer.setXLabelsAngle(55);
		renderer.setXLabelsPadding(10);

		renderer.setPanLimits(new double[] { startDate.toDate().getTime() - 3 * AN_HOUR,
				endDate.getTime() + 6 * AN_HOUR, -5, MAX_Y_VALUE });

		return renderer;
	}

	private GraphicalView getTimeChart(final XYMultipleSeriesDataset dataset, final XYMultipleSeriesRenderer renderer,
			final String format) {
		final CustomTimeChart chart = new CustomTimeChart(dataset, renderer);
		chart.setDateFormat(format);
		return new GraphicalView(activityProxy, chart);
	}

	private View createMainView(final Context context, final Patient activePatient) {
		final LinearLayout mainView = new LinearLayout(context);
		mainView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
		mainView.setOrientation(LinearLayout.HORIZONTAL);

		mainView.addView(createChartView(context));
		mainView.addView(createParametersMenu(context, activePatient));

		return mainView;
	}

	private View createParametersMenu(final Context context, final Patient activePatient) {
		final LinearLayout parametersMenu = new LinearLayout(context);
		parametersMenu.setOrientation(LinearLayout.VERTICAL);
		parametersMenu.setLayoutParams(new LinearLayout.LayoutParams(150, MATCH_PARENT));
		parametersMenu.setBackgroundColor(Color.BLACK);

		final List<PredefinedObservationType> observationTypes = appState.filterPredefinedObservationTypes(
				activePatient.getId(), ObservationGroup.HEMODYNAMICS.getObservationTypes());
		for (final PredefinedObservationType parameterType : observationTypes) {
			final Button parameterButton = new Button(context);
			parameterButton.setText(parameterType.getVisibleName());
			parameterButton.setTextColor(HemodynamicsParameterTypeColors.getColor(parameterType));
			parameterButton.setPadding(5, 10, 5, 10);
			parameterButton.setWidth(130);
			parameterButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					showParameterHistory(parameterType);
				}
			});
			parametersMenu.addView(parameterButton);
		}

		return parametersMenu;
	}

	private void showParameterHistory(final PredefinedObservationType parameterType) {
		log.info("showing parameter history [{}]", parameterType);
		final List<Observation> parameterObservations = new ArrayList<Observation>();
		for (final Observation observation : observations) {
			if (observation.getTypeKey().equals(parameterType.getKey())) {
				parameterObservations.add(observation);
			}
		}
		final HemodynamicsParameterHistoryDialog dialog = new HemodynamicsParameterHistoryDialog(parameterType,
				parameterObservations, appState);
		dialog.show(activityProxy.getFragmentManager(), "hemodynamicsParameterHistoryDialog");
	}

	private View createChartView(final Context context) {
		chartView = new LinearLayout(context);
		chartView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1));
		return chartView;
	}

	private View createHeader(final Context context) {
		final LinearLayout header = new LinearLayout(context);
		header.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

		return header;
	}

	private final SimpleDateFormat daySdf = new SimpleDateFormat("dd/MM");

	private boolean ready;

	private final ApplicationState appState;

	private final ApplicationConfigurationFacade config;

	private final AndroidToast androidToast;

	private List<Observation> observations = new ArrayList<Observation>();

	private Activity activityProxy;

	private final MobiTaskRunner taskRunner;

	private LinearLayout container;

	private LinearLayout chartView;

}
