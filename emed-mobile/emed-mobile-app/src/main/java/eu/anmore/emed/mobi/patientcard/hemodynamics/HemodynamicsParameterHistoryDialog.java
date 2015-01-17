package eu.anmore.emed.mobi.patientcard.hemodynamics;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.PredefinedObservationType;

public class HemodynamicsParameterHistoryDialog extends DialogFragment {

	public HemodynamicsParameterHistoryDialog(final PredefinedObservationType parameterType,
			final List<Observation> observations, final ApplicationState appState) {
		this.parameterType = parameterType;
		this.observations = observations;
		this.appState = appState;
	}

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		Collections.sort(observations, new Comparator<Observation>() {

			@Override
			public int compare(final Observation lhs, final Observation rhs) {
				return -1 * lhs.getDate().compareTo(rhs.getDate());
			}

		});

		final LayoutInflater inflater = getActivity().getLayoutInflater();
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final View dialogView = inflater.inflate(R.layout.hemodynamics_p_history, null);

		final TextView title = (TextView) dialogView.findViewById(R.id.hemodynamics_p_history_title);
		title.setText(parameterType.getVisibleName());

		final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
		final TableLayout table = (TableLayout) dialogView.findViewById(R.id.hemodynamics_p_history_table);

		for (final Observation observation : observations) {
			table.addView(getObservationRow(sdf, table, observation));
		}

		builder.setView(dialogView).setTitle(R.string.hemodynamics_parameter_history);
		return builder.create();
	}

	private TableRow getObservationRow(final SimpleDateFormat sdf, final TableLayout table,
			final Observation observation) {
		final TableRow tableRow = new TableRow(getActivity());

		final TextView row = new TextView(getActivity());
		row.setText(String.format("%-35s %s", sdf.format(observation.getDate()),
				appState.getObservationTypeFor(observation.getTypeKey()).getDisplayRawValue(observation.getValue())));
		table.addView(row);

		return tableRow;
	}

	private final ApplicationState appState;

	private final PredefinedObservationType parameterType;

	private final List<Observation> observations;

}
