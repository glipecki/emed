package eu.anmore.emed.admission;

import java.util.Date;

import eu.anmore.emed.diff.ValueDiff;


public class AdmissionDiff {

	public ValueDiff<Integer> getPatientId() {
		return patientId;
	}

	public ValueDiff<Date> getAdmissionDate() {
		return admissionDate;
	}

	public ValueDiff<String> getBlood() {
		return blood;
	}

	public ValueDiff<String> getHearthDefect() {
		return hearthDefect;
	}

	public ValueDiff<Double> getWeight() {
		return weight;
	}

	public ValueDiff<Double> getBodyArea() {
		return bodyArea;
	}

	public ValueDiff<Double> getHeight() {
		return height;
	}

	public ValueDiff<Boolean> getDeath() {
		return death;
	}

	public ValueDiff<String> getDischargePlace() {
		return dischargePlace;
	}

	public ValueDiff<Date> getDischargeDate() {
		return dischargeDate;
	}

	public ValueDiff<String> getAdmissionReason() {
		return admissionReason;
	}

	public boolean hasChanges() {
		return patientId.isChanged() || admissionDate.isChanged() || blood.isChanged() || hearthDefect.isChanged()
				|| weight.isChanged() || bodyArea.isChanged() || height.isChanged() || death.isChanged()
				|| dischargeDate.isChanged() || dischargePlace.isChanged() || getAdmissionReason().isChanged();
	}

	ValueDiff<Integer> patientId = ValueDiff.notChanged();

	ValueDiff<Date> admissionDate = ValueDiff.notChanged();

	ValueDiff<String> blood = ValueDiff.notChanged();

	ValueDiff<String> hearthDefect = ValueDiff.notChanged();

	ValueDiff<Double> weight = ValueDiff.notChanged();

	ValueDiff<Double> bodyArea = ValueDiff.notChanged();

	ValueDiff<Double> height = ValueDiff.notChanged();

	ValueDiff<Boolean> death = ValueDiff.notChanged();

	ValueDiff<String> dischargePlace = ValueDiff.notChanged();

	ValueDiff<Date> dischargeDate = ValueDiff.notChanged();

	ValueDiff<String> admissionReason = ValueDiff.notChanged();
}
