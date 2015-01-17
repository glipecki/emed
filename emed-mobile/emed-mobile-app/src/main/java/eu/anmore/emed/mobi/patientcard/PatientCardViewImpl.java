package eu.anmore.emed.mobi.patientcard;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import eu.anmore.emed.admission.Admission;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.patientcard.PatientCardPresenter.PatientCardView;
import eu.anmore.emed.patient.Patient;
import eu.anmore.emed.patient.Patient.Sex;
import eu.anmore.mvpdroid.view.BaseView;

@ContentView(R.layout.patient_card)
public class PatientCardViewImpl extends BaseView implements PatientCardView {

	@Override
	public void onCreate() {
		setDate(new Date());
	}

	@Override
	public void setPatient(final Patient patient) {
		setDisplayName(getDisplayName(patient));
		setPesel(patient.getPesel());
		setBirhtday(patient.getBirthday());
		setSex(patient.getSex());

		if (patient.getAdmissionList() != null && !patient.getAdmissionList().isEmpty()) {
			final Admission admission = patient.getAdmissionList().get(0);
			setAdmissionData(admission);
		} else {
			clearAdmissionData();
		}
	}

	@Override
	public void hideLoadingMark() {
		infoPanelView.setVisibility(View.VISIBLE);
		infoPanelRowTwoView.setVisibility(View.VISIBLE);
		groupsPanel.setVisibility(View.VISIBLE);
		infoPanelLoadingMark.setVisibility(View.GONE);
	}

	@Override
	public void showLoadingMark() {
		infoPanelView.setVisibility(View.GONE);
		infoPanelRowTwoView.setVisibility(View.GONE);
		groupsPanel.setVisibility(View.GONE);
		infoPanelLoadingMark.setVisibility(View.VISIBLE);
	}

	@Override
	public void setObservationTab(final View view) {
		observationsPanel.removeAllViews();
		observationsPanel.addView(view);
	}

	@Override
	public void setObservationGroupSelectionListener(final OnClickListener onClickListener) {
		hemodynamicsButton.setOnClickListener(onClickListener);
		fluidBalanceButton.setOnClickListener(onClickListener);
		gasometryButton.setOnClickListener(onClickListener);
		cbcButton.setOnClickListener(onClickListener);
		bloodChemistryButton.setOnClickListener(onClickListener);
		coagulationpanelButton.setOnClickListener(onClickListener);
	}

	@Override
	public void setAddObservationsClickHandler(final OnClickListener addObservationsClickHandler) {
		addObservationButton.setOnClickListener(addObservationsClickHandler);
	}

	private static final CharSequence DATE_FORMAT = "yyyy-MM-dd";

	private void setAdmissionData(final Admission admission) {
		setBloodType(admission.getBlood());
		setWeight(admission.getWeight());
		setBsa(admission.getBodyArea());
		setAdmissionDate(admission.getAdmissionDate());
		setDayOfAdmission(getDayOfAdmission(admission));
	}

	private void clearAdmissionData() {
		setBloodType("");
		setWeight(0.0d);
		setBsa(0.0d);
		setAdmissionDate(new Date(0));
		setDayOfAdmission(0);
	}

	private void setAdmissionDate(final Date admissionDate) {
		if (admissionDate != null) {
			admissionDateTextView.setText(DateFormat.format(DATE_FORMAT, admissionDate));
		} else {
			admissionDateTextView.setText("");
		}
	}

	private void setDayOfAdmission(final int dayOfAdmission) {
		dayOfAdmissionTextView.setText(dayOfAdmission != 0 ? String.valueOf(dayOfAdmission) : "");
	}

	private void setDate(final Date date) {
		if (date != null) {
			dateTextView.setText(DateFormat.format(DATE_FORMAT, date));
		} else {
			dateTextView.setText("");
		}
	}

	private void setSex(final Sex sex) {
		sexTextView.setText(sex == Sex.WOMAN ? "Kobieta" : "Mężczyzna");
	}

	private void setBsa(final Double bodyArea) {
		// TODO (glipecki): is BSA equal to BodyArea?
		bsaTextView.setText(bodyArea != null && bodyArea != 0.0d ? String.valueOf(bodyArea) : "");
	}

	private void setWeight(final Double weight) {
		weightTextView.setText(weight != null && weight != 0.0d ? String.valueOf(weight) : "");
	}

	private void setBloodType(final String bloodType) {
		bloodTypeTextView.setText(valueOrEmpty(bloodType));
	}

	private void setBirhtday(final Date birthday) {
		if (birthday != null) {
			birthdayTextView.setText(DateFormat.format(DATE_FORMAT, birthday));
		} else {
			birthdayTextView.setText("");
		}
	}

	private void setPesel(final String patient) {
		peselTextView.setText(valueOrEmpty(patient));
	}

	private void setDisplayName(final String displayName) {
		displayNameTextView.setText(valueOrEmpty(displayName));
	}

	private String getDisplayName(final Patient patient) {
		return String.format("%s %s", valueOrEmpty(patient.getFirstName()), valueOrEmpty(patient.getSurname()));
	}

	private int getDayOfAdmission(final Admission admission) {
		final DateTime admissionDate = new DateTime(admission.getAdmissionDate());
		if (admissionDate != null) {
			final DateTime now = new DateTime();
			return Days.daysBetween(admissionDate, now).getDays();
		} else {
			return 0;
		}
	}

	private String valueOrEmpty(final String string) {
		return string != null ? string : "";
	}

	@InjectView(R.id.patient_card_displayname)
	private TextView displayNameTextView;

	@InjectView(R.id.patient_card_pesel)
	private TextView peselTextView;

	@InjectView(R.id.patient_card_birthdate)
	private TextView birthdayTextView;

	@InjectView(R.id.patient_card_blood_type)
	private TextView bloodTypeTextView;

	@InjectView(R.id.patient_card_weight)
	private TextView weightTextView;

	@InjectView(R.id.patient_card_bsa)
	private TextView bsaTextView;

	@InjectView(R.id.patient_card_sex)
	private TextView sexTextView;

	@InjectView(R.id.patient_card_date)
	private TextView dateTextView;

	@InjectView(R.id.patient_card_day_of_admission)
	private TextView dayOfAdmissionTextView;

	@InjectView(R.id.patient_card_admission_date)
	private TextView admissionDateTextView;

	@InjectView(R.id.patient_card_info_panel)
	private View infoPanelView;

	@InjectView(R.id.patient_card_info_panel_row_two)
	private View infoPanelRowTwoView;

	@InjectView(R.id.patient_card_groups_panel)
	private View groupsPanel;

	@InjectView(R.id.patient_card_observations_panel)
	private LinearLayout observationsPanel;

	@InjectView(R.id.patient_card_info_loading_mark)
	private ProgressBar infoPanelLoadingMark;

	@InjectView(R.id.patient_card_add_observation)
	private Button addObservationButton;

	@InjectView(R.id.patient_card_fluidbalance)
	private Button fluidBalanceButton;

	@InjectView(R.id.patient_card_hemodynamics)
	private Button hemodynamicsButton;

	@InjectView(R.id.patient_card_gasometry)
	private Button gasometryButton;

	@InjectView(R.id.patient_card_cbc)
	private Button cbcButton;

	@InjectView(R.id.patient_card_bloodchemistry)
	private Button bloodChemistryButton;

	@InjectView(R.id.patient_card_coagulationpanel)
	private Button coagulationpanelButton;

}
