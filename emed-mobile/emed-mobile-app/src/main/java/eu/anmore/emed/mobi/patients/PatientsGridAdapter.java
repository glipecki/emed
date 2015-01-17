package eu.anmore.emed.mobi.patients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.R;
import eu.anmore.emed.patient.Patient;

public final class PatientsGridAdapter extends ArrayAdapter<Patient> {

	@Inject
	public PatientsGridAdapter(final Context context, final LayoutInflater layoutInflater) {
		super(context, LAYOUT_ID);
		this.layoutInflater = layoutInflater;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		return PatientTileView.of(getTileLayout(parent), getPatient(position)).getView();
	}

	private static final int LAYOUT_ID = R.layout.patients_patient_tile;

	private Patient getPatient(final int position) {
		return getItem(position);
	}

	private View getTileLayout(final ViewGroup parent) {
		return layoutInflater.inflate(LAYOUT_ID, parent, false);
	}

	private final LayoutInflater layoutInflater;

}