package eu.anmore.emed.mobi.observation;

import static android.text.format.DateFormat.is24HourFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.TimePicker;

public final class ChangeTimeListener implements OnTouchListener {

	public static final String TIME_FORMAT = "HH:mm";

	public ChangeTimeListener(final Activity context) {
		this.context = context;
	}

	@Override
	public boolean onTouch(final View v, final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			try {
				final Date date = new SimpleDateFormat("HH:mm").parse(((TextView) v).getText().toString());
				new TimePickerDialog(context, new OnTimeSetListener() {

					@Override
					public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
						((TextView) v).setText(String.format("%02d:%02d", hourOfDay, minute));
					}

				}, date.getHours(), date.getMinutes(), is24HourFormat(context)).show();
				return true;
			} catch (final ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			return false;
		}
	}

	private final Activity context;
}