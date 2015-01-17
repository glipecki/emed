package eu.anmore.emed.mobi.observation;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import eu.anmore.emed.observation.ObservationType;

public class ObservationEditText extends EditText implements ObservationInputField {

	public ObservationEditText(final Context context, final ObservationType observationType) {
		super(context);
		this.observationType = observationType;
	}

	@Override
	public ObservationType getObservationType() {
		return observationType;
	}

	@Override
	public String getRawValue() {
		return getText().toString();
	}

	@Override
	public View asWidget() {
		return this;
	}

	@Override
	public String toString() {
		return String.format("ObservationEditText [observationType=%s]", observationType);
	}

	@Override
	public void setValidationErrorMessage(final int errorMessage) {
		// TODO Auto-generated method stub

	}

	private final ObservationType observationType;

}
