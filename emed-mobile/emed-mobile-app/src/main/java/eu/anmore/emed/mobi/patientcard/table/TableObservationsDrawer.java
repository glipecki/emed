package eu.anmore.emed.mobi.patientcard.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TableRow;
import android.widget.TextView;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationNorm;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.widget.builder.WidgetBuilder;
import eu.anmore.utils.DateUtil;

public class TableObservationsDrawer {

	public TableObservationsDrawer(final List<ObservationType> observationTypes, final TableRow hoursRow,
			final Map<ObservationType, TableRow> observationRows, final Activity activityProxy,
			final boolean seamlessParameterWidth, final ApplicationState appState, final Patient activePatient) {
		this.observationTypes = observationTypes;
		this.hoursRow = hoursRow;
		this.observationRows = observationRows;
		this.activityProxy = activityProxy;
		this.seamlessParameterWidth = seamlessParameterWidth;
		this.appState = appState;
		this.activePatient = activePatient;
		this.wb = new WidgetBuilder(activityProxy);
		this.sdf = new SimpleDateFormat("H:mm");
		this.nextDayWithYearSdf = new SimpleDateFormat("dd/MM");
		this.nextDaySdf = new SimpleDateFormat("dd/MM");
	}

	public void setObservations(final List<Observation> observations,
			final Map<String, ObservationNorm> observationNorms) {
		final List<Observation> filteredObservations = appState.filterObservations(activePatient.getId(), observations);
		final Map<Date, Map<String, Observation>> observationsByBatch = getObservationsByBatchId(filteredObservations);
		final Map<Date, Map<String, Observation>> sortedObservationBatchesByDate = getObservationBatchesSortedByDate(observationsByBatch);
		Date lastDate = null;
		for (final Entry<Date, Map<String, Observation>> observationsBatch : sortedObservationBatchesByDate.entrySet()) {
			final Date batchDate = observationsBatch.getKey();
			if (DateUtil.isOtherDay(lastDate, batchDate)) {
				drawObservationsBatch(getNextDayColumnFormatter(batchDate), new HashMap<String, Observation>(), false,
						true, observationNorms);
			}
			drawObservationsBatch(sdf.format(batchDate), observationsBatch.getValue(), true, false, observationNorms);
			lastDate = batchDate;
		}
	}

	private static final int SEAMLESS_CELL_WIDTH = 60;

	private String getNextDayColumnFormatter(final Date batchDate) {
		return batchDate.getYear() == new Date().getYear() ? nextDaySdf.format(batchDate) : nextDayWithYearSdf
				.format(batchDate);
	}

	private Map<Date, Map<String, Observation>> getObservationBatchesSortedByDate(
			final Map<Date, Map<String, Observation>> observationsByBatch) {
		return new TreeMap<Date, Map<String, Observation>>(observationsByBatch);
	}

	private void drawObservationsBatch(final String hoursText, final Map<String, Observation> observations,
			final boolean border, final boolean grayed, final Map<String, ObservationNorm> observationNorms) {
		final TextView hoursView = TableObservationTabView.getTextView(wb, hoursText, activityProxy);
		if (grayed) {
			hoursView.setBackgroundColor(activityProxy.getResources().getColor(R.color.separator_color));
			hoursView.setTextColor(Color.BLACK);
		}
		if (seamlessParameterWidth) {
			hoursView.setWidth(SEAMLESS_CELL_WIDTH);
		}
		hoursRow.addView(hoursView);

		for (final ObservationType observationType : observationTypes) {
			final String cellText = getObservationCellText(observations, observationType);
			final TextView valueTextView = TableObservationTabView.getTextView(wb, cellText, activityProxy);
			if (border) {
				valueTextView.setBackgroundResource(R.drawable.cell_shape);
			}
			if (grayed) {
				valueTextView.setBackgroundColor(activityProxy.getResources().getColor(R.color.separator_color));
			}
			if (cellText != null && !cellText.isEmpty() && observationNorms.containsKey(observationType.getKey())) {
				final ObservationNorm norm = observationNorms.get(observationType.getKey());
				switch (norm.getNormStatus(cellText)) {
				case BELOW:
					valueTextView.setBackgroundColor(Color.YELLOW);
					valueTextView.setTextColor(Color.BLACK);
					break;
				case OVER:
					valueTextView.setBackgroundColor(Color.RED);
					valueTextView.setTextColor(Color.BLACK);
					break;
				case GOOD:
				default:
					break;
				}
			}
			observationRows.get(observationType).addView(valueTextView);
		}
	}

	private Observation getFirstObservation(final Map<String, Observation> observations) {
		final Iterator<Entry<String, Observation>> observationsIterator = observations.entrySet().iterator();
		if (observationsIterator.hasNext()) {
			return observationsIterator.next().getValue();
		} else {
			throw new RuntimeException("No observations");
		}
	}

	private String getObservationCellText(final Map<String, Observation> observations,
			final ObservationType observationType) {
		final String cellText;
		if (observations.containsKey(observationType.getKey())) {
			cellText = String.valueOf(observations.get(observationType.getKey()).getValue());
		} else {
			cellText = "";
		}
		return cellText;
	}

	private Map<Date, Map<String, Observation>> getObservationsByBatchId(final List<Observation> observations) {
		final Map<Date, Map<String, Observation>> observationsByBatchDate = new HashMap<Date, Map<String, Observation>>();
		for (final Observation observation : observations) {
			final Date batchDate = observation.getDate();
			if (batchDate != null) {
				final String typeKey = observation.getTypeKey();
				if (!observationsByBatchDate.containsKey(batchDate)) {
					observationsByBatchDate.put(batchDate, new HashMap<String, Observation>());
				}
				observationsByBatchDate.get(batchDate).put(typeKey, observation);
			}
		}
		return observationsByBatchDate;
	}

	private final Patient activePatient;

	private final ApplicationState appState;

	private final boolean seamlessParameterWidth;

	private final Activity activityProxy;

	private final SimpleDateFormat nextDaySdf;

	private final SimpleDateFormat nextDayWithYearSdf;

	private final SimpleDateFormat sdf;

	private final WidgetBuilder wb;

	private final TableRow hoursRow;

	private final List<ObservationType> observationTypes;

	private final Map<ObservationType, TableRow> observationRows;

}
