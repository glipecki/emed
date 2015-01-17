package eu.anmore.emed.mobi.patientcard;

import java.util.List;

import android.app.Activity;
import android.view.View;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.patient.Patient;

public interface ObservationTab {

	View getView(Activity activityProxy, Patient activePatient);

	void refreshView(Patient activePatient);

	List<ObservationGroup> getObservationGroups();

	boolean isReady();

}
