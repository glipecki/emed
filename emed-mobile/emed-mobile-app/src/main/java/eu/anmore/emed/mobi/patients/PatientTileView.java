package eu.anmore.emed.mobi.patients;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import eu.anmore.emed.admission.Admission;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.patient.Patient;
import eu.anmore.mvpdroid.view.ListItemView;

public class PatientTileView implements ListItemView {

	public static ListItemView of(final View rowView, final Patient patient) {
		return new PatientTileView(rowView, patient);
	}

	@Override
	public View getView() {
		return rowView;
	}

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private PatientTileView(final View rowView, final Patient patient) {
		this.rowView = rowView;
		setPesel(patient.getPesel());
		setDisplayName(getDisplayName(patient));
		setBirthday(patient.getBirthday());
		setSex(patient.getSex());

		final Admission admission = patient.getAdmissionList().get(0);
		if (admission != null) {
			setDayOfAdmission(getDayOfAdmission(admission));
		} else {
			setDayOfAdmission(0);
		}
	}

	private void setPesel(final String pesel) {
		final TextView peselTextView = (TextView) rowView.findViewById(R.id.patients_patient_tile_pesel);
		peselTextView.setText(valueOrEmpty(pesel));
	}

	private String valueOrEmpty(final String string) {
		return string != null ? string : "";
	}

	private void setDisplayName(final String displayName) {
		final TextView displaynameTextView = (TextView) rowView.findViewById(R.id.patients_patient_tile_displayname);
		displaynameTextView.setText(valueOrEmpty(displayName));
	}

	private void setBirthday(final Date birthday) {
		final TextView birthdayTextView = (TextView) rowView.findViewById(R.id.patients_patient_tile_birthday);
		if (birthday != null) {
			birthdayTextView.setText(DateFormat.format(DATE_FORMAT, birthday));
		} else {
			birthdayTextView.setText("");
		}
	}

	private void setSex(final Patient.Sex sex) {
		final TextView sexTextView = (TextView) rowView.findViewById(R.id.patients_patient_tile_sex);
		sexTextView.setText(getSexDisplayText(sex));
	}

	private void setDayOfAdmission(final Integer dayOfAdmission) {
		final TextView dayOfAdmissionTextView = (TextView) rowView
				.findViewById(R.id.patients_patient_tile_day_of_admission);
		dayOfAdmissionTextView.setText(String.valueOf(dayOfAdmission));
	}

	private String getSexDisplayText(final Patient.Sex sex) {
		return sex == Patient.Sex.WOMAN ? "kobieta" : "mężczyzna";
	}

	private String getDisplayName(final Patient patient) {
		return String.format("%s %s", valueOrEmpty(patient.getFirstName()), valueOrEmpty(patient.getSurname()));
	}

	private int getDayOfAdmission(final Admission admission) {
		if (admission.getAdmissionDate() != null) {
			final DateTime admissionDate = new DateTime(admission.getAdmissionDate());
			final DateTime now = new DateTime();
			return Days.daysBetween(admissionDate, now).getDays();
		} else {
			return 0;
		}
	}

	private final View rowView;
}