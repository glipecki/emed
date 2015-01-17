package eu.anmore.emed.mobi.parameters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.R;
import eu.anmore.emed.observation.CustomObservationType;

public class CustomObservationsAdapter extends ArrayAdapter<CustomObservationType> {

	@Inject
	public CustomObservationsAdapter(final Context context, final LayoutInflater layoutInflater) {
		super(context, LAYOUT_ID);
		this.layoutInflater = layoutInflater;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final View view = layoutInflater.inflate(LAYOUT_ID, null);
		getTextView(view, R.id.custom_observation_type_name).setText(getItem(position).getVisibleName());
		getTextView(view, R.id.custom_observation_type_group_name).setText(
				getItem(position).getObservationGroup().getVisibleName());
		return view;
	}

	private static final int LAYOUT_ID = R.layout.custom_observation_list_item;

	private TextView getTextView(final View view, final int resId) {
		return (TextView) view.findViewById(resId);
	}

	private final LayoutInflater layoutInflater;

}
