package eu.anmore.emed.mobi.observation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.TextView;

public final class ChangeDateListener implements OnTouchListener {

	public static final String DATE_FORMAT = "yyyy/MM/dd";

	public ChangeDateListener(final Activity context) {
		this.context = context;
	}

	@Override
	public boolean onTouch(final View v, final MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			try {
				final Date date = new SimpleDateFormat("yyyy/MM/dd").parse(((TextView) v).getText().toString());

				new DatePickerDialog(context, new OnDateSetListener() {

					@Override
					public void onDateSet(final DatePicker view, final int year, final int monthOfYear,
							final int dayOfMonth) {
						((TextView) v).setText(String.format("%4d/%02d/%02d", year, monthOfYear + 1, dayOfMonth));
					}
				}, 1900 + date.getYear(), date.getMonth(), date.getDate()).show();

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