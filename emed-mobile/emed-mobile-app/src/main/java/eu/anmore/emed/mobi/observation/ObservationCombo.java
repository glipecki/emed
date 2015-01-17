package eu.anmore.emed.mobi.observation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.observation.PredefinedObservationType;

public class ObservationCombo extends LinearLayout implements ObservationInputField {

	public static final String CHOOSE_LABEL = "Wybierz";

	public ObservationCombo(final Context context, final PredefinedObservationType observationType) {
		super(context);
		this.observationType = observationType;

		this.comboSpinner = getSpinner(context, this.observationType);
		this.validationErrorPlaceHolder = getValidationErrorPlaceHolder(context);

		setOrientation(LinearLayout.VERTICAL);
		addView(comboSpinner);
		addView(validationErrorPlaceHolder);

		hideValidationErrorMessage();
	}

	@Override
	public String getRawValue() {
		final String rawValue = String.valueOf(this.comboSpinner.getSelectedItem());
		return rawValue != CHOOSE_LABEL ? rawValue : null;
	}

	@Override
	public PredefinedObservationType getObservationType() {
		return observationType;
	}

	@Override
	public View asWidget() {
		return this;
	}

	@Override
	public void setValidationErrorMessage(final int errorMessage) {
		validationErrorPlaceHolder.setText(errorMessage);
		validationErrorPlaceHolder.setVisibility(View.VISIBLE);
	}

	private TextView getValidationErrorPlaceHolder(final Context context) {
		final TextView textView = new TextView(context);
		textView.setTextColor(context.getResources().getColor(R.color.error_message_text_color));
		return textView;
	}

	private Spinner getSpinner(final Context context, final PredefinedObservationType observationType) {
		final Spinner spinner = new Spinner(context);
		spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
				getPossibleValuesWithChoose(observationType)));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
				hideValidationErrorMessage();
			}

			@Override
			public void onNothingSelected(final AdapterView<?> parent) {
			}

		});
		return spinner;
	}

	private void hideValidationErrorMessage() {
		validationErrorPlaceHolder.setVisibility(View.GONE);
	}

	private List<String> getPossibleValuesWithChoose(final PredefinedObservationType observationType) {
		final List<String> possibleValues = new ArrayList<String>();
		possibleValues.add(CHOOSE_LABEL);
		possibleValues.addAll(observationType.getPossibleValues());
		return possibleValues;
	}

	private final PredefinedObservationType observationType;

	private final Spinner comboSpinner;

	private final TextView validationErrorPlaceHolder;

}
