package eu.anmore.emed.mobi.parameters;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.parameters.CustomObservationsPresenter.CustomObservationsView;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.mvpdroid.view.BaseView;

@ContentView(R.layout.custom_observations)
public class CustomObservationsViewImpl extends BaseView implements CustomObservationsView {

	@Override
	public void onCreate() {
		getContext().setTitle("Parametry użytkownika");

		final List<ObservationGroupAdapter> adapters = new ArrayList<ObservationGroupAdapter>();
		for (final ObservationGroup group : ObservationGroup.values()) {
			adapters.add(new ObservationGroupAdapter(group));
		}
		newObservationGroupSpinner.setAdapter(new ArrayAdapter<ObservationGroupAdapter>(getContext(),
				android.R.layout.simple_list_item_1, adapters));
	}

	@Override
	public void setCustomObservationsAdapter(final ListAdapter customObservationsAdapter) {
		customObservationsListView.setAdapter(customObservationsAdapter);
	}

	@Override
	public void setAddCustomObservationClickHandler(final AddCustomObservationsClickHandler clickHandler) {
		addNewObservationTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final ObservationGroup observationGroup = ((ObservationGroupAdapter) newObservationGroupSpinner
						.getSelectedItem()).getObservationGroup();
				clickHandler.onAddCustomParameter(new CustomObservationType(getTypeKey(observationGroup),
						newObservationVisibleNameEditText.getText().toString(), observationGroup));
			}

			private String getTypeKey(final ObservationGroup observationGroup) {
				return observationGroup.getKey() + "_"
						+ newObservationKeyEditText.getText().toString().replace(" ", "").trim().toUpperCase();
			}
		});
	}

	@Override
	public void showObservationsList() {
		progress.setVisibility(View.GONE);
		panel.setVisibility(View.VISIBLE);
	}

	@InjectView(R.id.custom_observations_key)
	private EditText newObservationKeyEditText;

	@InjectView(R.id.custom_observations_visible_name)
	private EditText newObservationVisibleNameEditText;

	@InjectView(R.id.custom_observations_groups)
	private Spinner newObservationGroupSpinner;

	@InjectView(R.id.custom_observations_add)
	private Button addNewObservationTypeButton;

	@InjectView(R.id.custom_observations_progress)
	private ProgressBar progress;

	@InjectView(R.id.custom_observations_panel)
	private View panel;

	@InjectView(R.id.custom_observations_existing)
	private ListView customObservationsListView;

	private final class ObservationGroupAdapter {

		public ObservationGroupAdapter(final ObservationGroup group) {
			super();
			this.group = group;
		}

		@Override
		public String toString() {
			return group.getVisibleName();
		}

		public ObservationGroup getObservationGroup() {
			return group;
		}

		private final ObservationGroup group;

	}

}
