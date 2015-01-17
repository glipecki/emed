package eu.anmore.emed.mobi.observation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.observation.AddObservationPresenter.AddObservationView;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.observation.PredefinedObservationType;
import eu.anmore.mvpdroid.toast.AndroidToast;
import eu.anmore.mvpdroid.view.BaseView;

/**
 * Okno dodawania obserwacji.
 * 
 * @author glipecki
 */
@ContentView(R.layout.add_observation)
public class AddObservationViewImpl extends BaseView implements AddObservationView {

	@Inject
	public AddObservationViewImpl(final AndroidToast toast, final ApplicationState appState) {
		this.toast = toast;
		this.appState = appState;
	}

	@Override
	public void onCreate() {
		observationFields.clear();

		getContext().setTitle(R.string.add_observation_title);

		final Date currentDate = new Date();

		dateDate.setOnTouchListener(new ChangeDateListener(getContext()));
		dateDate.setText(new SimpleDateFormat(ChangeDateListener.DATE_FORMAT).format(currentDate));

		dateTime.setOnTouchListener(new ChangeTimeListener(getContext()));
		dateTime.setText(new SimpleDateFormat(ChangeTimeListener.TIME_FORMAT).format(currentDate));
	}

	@Override
	public void setObservationTypes(final Integer activePatientId, final List<ObservationType> observationTypes) {
		final List<ObservationType> filteredObservationTypes = appState.filterDirectObservationTypes(activePatientId,
				observationTypes);
		if (hasManyGroups(filteredObservationTypes)) {
			final Map<ObservationGroup, List<ObservationType>> map = new ObservationListToMapConverter(
					filteredObservationTypes).convert();
			buildObservationsTable(map);
		} else {
			buildObservationsTable(filteredObservationTypes);
		}
	}

	@Override
	public void setAddObservationsClickHandler(final AddObservationsClickHandler clickHandler) {
		this.clickHandler = clickHandler;
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				onAddObservationClick();
			}

		});
	}

	private boolean hasManyGroups(final List<ObservationType> observationTypes) {
		if (observationTypes.size() > 0) {
			final List<ObservationGroup> anyGroups = observationTypes.get(0).getObservationGroups();
			for (final ObservationType observationType : observationTypes) {
				if (!observationType.getObservationGroups().contains(anyGroups.get(0))) {
					return true;
				}
			}
		}
		return false;
	}

	private Map<ObservationType, String> getObservationValues() {
		final Map<ObservationType, String> values = new HashMap<ObservationType, String>();
		for (final ObservationInputField field : observationFields) {
			values.put(field.getObservationType(), field.getRawValue());
		}
		return values;
	}

	private Date getDate(final String dateTime, final String dateDate) {
		try {
			return new SimpleDateFormat(String.format("%s %s", ChangeDateListener.DATE_FORMAT,
					ChangeTimeListener.TIME_FORMAT)).parse(String.format("%s %s", dateDate, dateTime));
		} catch (final ParseException e) {
			return null;
		}
	}

	private void buildObservationsTable(final Map<ObservationGroup, List<ObservationType>> map) {
		for (final ObservationGroup group : map.keySet()) {
			final TableRow tableRow = getTableRow();
			final TextView label = new TextView(getContext());
			label.setText(group.getVisibleName());
			label.setTextSize(20);
			tableRow.addView(label);
			observationTypesTable.addView(tableRow);

			buildObservationsTable(map.get(group));
		}
	}

	private void buildObservationsTable(final List<ObservationType> observationTypes) {
		for (final ObservationType observationType : observationTypes) {
			final TableRow tableRow = getTableRow();

			final TextView label = getObservationLabel(observationType);
			tableRow.addView(label);

			final ObservationInputField observationEditText = getObservationInputField(observationType);
			observationFields.add(observationEditText);
			tableRow.addView(observationEditText.asWidget());

			observationTypesTable.addView(tableRow);
		}
	}

	private TableRow getTableRow() {
		return new TableRow(getContext());
	}

	private ObservationInputField getObservationInputField(final ObservationType observationType) {
		switch (observationType.getType()) {
		case DOMAIN:
			return getObservationCombo(observationType);
		case NUMERIC:
			return getObservationEditText(observationType);
		default:
			throw new UnsupportedOperationException();
		}
	}

	private ObservationInputField getObservationEditText(final ObservationType observationType) {
		final ObservationEditText observationEditText = new ObservationEditText(getContext(), observationType);
		setInputFieldLayout(observationEditText);
		observationEditText.setEms(10);
		observationEditText.setInputType(InputType.TYPE_CLASS_PHONE);
		observationEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					onAddObservationClick();
				}
				return false;
			}
		});
		return observationEditText;
	}

	private ObservationInputField getObservationCombo(final ObservationType observationType) {
		final ObservationCombo observationCombo = new ObservationCombo(getContext(),
				(PredefinedObservationType) observationType);
		setInputFieldLayout(observationCombo);

		return observationCombo;
	}

	private void setInputFieldLayout(final View view) {
		TableRow.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new LayoutParams();
			view.setLayoutParams(layoutParams);
		}
		layoutParams.width = 0;
		layoutParams.weight = 2;
	}

	private TextView getObservationLabel(final ObservationType observationType) {
		final TextView label = new TextView(getContext());
		final String labelText = getObservationTypeLabelText(observationType.getVisibleName(),
				observationType.getFullName());
		label.setText(Html.fromHtml(labelText));
		TableRow.LayoutParams layoutParams = (LayoutParams) label.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new LayoutParams();
			label.setLayoutParams(layoutParams);
		}
		layoutParams.width = 0;
		layoutParams.gravity = Gravity.RIGHT | Gravity.CENTER;
		layoutParams.rightMargin = 10;
		layoutParams.weight = 2;
		label.setGravity(Gravity.RIGHT);
		label.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
		label.setPadding(0, 10, 0, 10);

		// final int labelTextLines = labelText.length() / MAX_LABEL_LINE_LENGTH
		// + 1;
		// final int height = labelTextLines * LABEL_LINE_HEIGHT;
		// label.setHeight((int)
		// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height,
		// getContext()
		// .getResources().getDisplayMetrics()));

		return label;
	}

	private String getObservationTypeLabelText(final String visibleName, final String fullName) {
		return visibleName.equals(fullName) ? visibleName : String.format("%s (%s)", fullName, visibleName);
	}

	private void onAddObservationClick() {
		if (validateObservationValues().isEmpty()) {
			if (clickHandler != null) {
				final Date date = getDate(dateTime.getText().toString(), dateDate.getText().toString());
				final Map<ObservationType, String> values = getObservationValues();
				clickHandler.onAddObservation(date, values);
			}
		} else {
			toast.showShortToast("Proszę sprawdzić poprawność wprowadzanych danych");
		}
	}

	private Map<ObservationType, List<ValidationError>> validateObservationValues() {
		final Map<ObservationType, List<ValidationError>> validationErrors = new HashMap<ObservationType, List<ValidationError>>();
		for (final ObservationInputField field : observationFields) {
			final ObservationType fieldObservationType = field.getObservationType();
			final String rawValue = field.getRawValue();
			final List<ValidationError> fieldValidationErrors = new ArrayList<ValidationError>();

			if (fieldObservationType.isMandatory() && isEmpty(rawValue)) {
				fieldValidationErrors.add(ValidationError.EMPTY);
				field.setValidationErrorMessage(R.string.mandatory);
			}

			if (!fieldValidationErrors.isEmpty()) {
				validationErrors.put(field.getObservationType(), fieldValidationErrors);
			}
		}
		return validationErrors;
	}

	private boolean isEmpty(final String string) {
		return string == null || string.isEmpty();
	}

	private final ApplicationState appState;

	private final AndroidToast toast;

	private AddObservationsClickHandler clickHandler;

	private final List<ObservationInputField> observationFields = new ArrayList<ObservationInputField>();

	@InjectView(R.id.observation_add_table)
	private TableLayout observationTypesTable;

	@InjectView(R.id.observation_add_date_date)
	private EditText dateDate;

	@InjectView(R.id.observation_add_date_time)
	private EditText dateTime;

	@InjectView(R.id.observations_add_add)
	private Button addButton;

}
