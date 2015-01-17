package eu.anmore.emed.mobi.observation;

import android.view.View;
import eu.anmore.emed.observation.ObservationType;

public interface ObservationInputField {

	String getRawValue();

	ObservationType getObservationType();

	View asWidget();

	void setValidationErrorMessage(int errorMessage);

}
