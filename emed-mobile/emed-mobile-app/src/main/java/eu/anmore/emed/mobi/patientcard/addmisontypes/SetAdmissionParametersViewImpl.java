package eu.anmore.emed.mobi.patientcard.addmisontypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SetAdmissionParametersPresenter.SetAdmissionParametersView;
import eu.anmore.emed.observation.AdmissionObservationType;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.observation.PredefinedObservationType;
import eu.anmore.mvpdroid.view.BaseView;
import eu.anmore.mvpdroid.widget.builder.WidgetBuilder;

@ContentView(R.layout.set_admission_parameters)
public class SetAdmissionParametersViewImpl extends BaseView implements SetAdmissionParametersView {

	@Inject
	public SetAdmissionParametersViewImpl(final ApplicationState appState) {
		this.appState = appState;
	}

	@Override
	public void onCreate() {
		getContext().setTitle("Parametry śledzone w ramach przyjęcia");
		wb = new WidgetBuilder(getContext());
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				SetAdmissionParametersViewImpl.this.save();
			}
		});

	}

	@Override
	public void setPatientId(final int patientId) {
		createTable(patientId);
	}

	@Override
	public void setSaveCallback(final SaveCallback saveCallback) {
		this.saveCallback = saveCallback;
	}

	private void save() {
		if (saveCallback != null) {
			final List<String> types = new ArrayList<String>();
			for (final Entry<String, CheckBox> e : typeCheckBoxes.entrySet()) {
				if (e.getValue().isChecked()) {
					types.add(e.getKey());
				}
			}
			saveCallback.save(types);
		}
	}

	private void createTable(final int patientId) {
		final List<AdmissionObservationType> admissionTypes = appState.getPatientAdmissionTypes(patientId);
		for (final ObservationGroup group : ObservationGroup.values()) {
			if (group != ObservationGroup.CUSTOM) {
				final TableRow row = wb.tableRow().get();
				row.addView(wb.textView().text(Html.fromHtml(group.getVisibleName())).padding(5, 10, 5, 10)
						.textSize(20).get());
				table.addView(row);

				createGroup(admissionTypes, group);
			}
		}
	}

	private void createGroup(final List<AdmissionObservationType> admissionTypes, final ObservationGroup group) {
		for (final PredefinedObservationType type : group.getObservationTypes()) {
			addTypeRow(admissionTypes, type);
		}

		final List<CustomObservationType> customTypes = appState.getCustomObservationTypes(group);
		for (final CustomObservationType type : customTypes) {
			addTypeRow(admissionTypes, type);
		}
	}

	private void addTypeRow(final List<AdmissionObservationType> admissionTypes, final ObservationType type) {
		final String typeKey = type.getKey();

		final TableRow row = wb.tableRow().get();

		row.addView(wb.textView().text(Html.fromHtml(type.getVisibleName())).gravity(Gravity.RIGHT).get());
		typeCheckBoxes.put(typeKey, wb.checkBox().checked(isDirectAdmissionType(admissionTypes, typeKey)).get());
		row.addView(typeCheckBoxes.get(typeKey));

		table.addView(row);
	}

	private boolean isDirectAdmissionType(final List<AdmissionObservationType> admissionTypes, final String typeKey) {
		for (final AdmissionObservationType admissionObservationType : admissionTypes) {
			if (typeKey.equals(admissionObservationType.getTypeKey()) && admissionObservationType.getDirect()) {
				return true;
			}
		}
		return false;
	}

	private final Map<String, CheckBox> typeCheckBoxes = new HashMap<String, CheckBox>();

	private final ApplicationState appState;

	private WidgetBuilder wb;

	@InjectView(R.id.set_aparam_table)
	private TableLayout table;

	@InjectView(R.id.set_aparam_save)
	private Button saveButton;

	private SaveCallback saveCallback;

}
