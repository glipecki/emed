package eu.anmore.emed.mobi.patients;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import eu.anmore.emed.mobi.R;
import eu.anmore.mvpdroid.view.BaseView;

/**
 * Currently admitted patients screen view implementation.
 * 
 * @author Grzegorz Lipecki
 */
@ContentView(R.layout.patients)
public class PatientListViewImpl extends BaseView implements PatientListPresenter.PatientListView {

	@Override
	public void setPatientsGridAdapter(final PatientsGridAdapter patientsGridAdapter) {
		patientsGrid.setAdapter(patientsGridAdapter);
	}

	@Override
	public void setPatientSelectionListener(final OnItemClickListener patientSelectionListener) {
		patientsGrid.setOnItemClickListener(patientSelectionListener);
	}

	@Override
	public void showLoadingMark() {
		patientsGrid.setVisibility(View.GONE);
		loadingMark.setVisibility(View.VISIBLE);
	}

	@Override
	public void showPatientsGrid() {
		patientsGrid.setVisibility(View.VISIBLE);
		loadingMark.setVisibility(View.GONE);
	}

	@InjectView(R.id.patients_patients_grid)
	private GridView patientsGrid;

	@InjectView(R.id.patients_loading_mark)
	private ProgressBar loadingMark;

}
