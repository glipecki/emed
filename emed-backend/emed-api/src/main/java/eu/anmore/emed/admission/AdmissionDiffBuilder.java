package eu.anmore.emed.admission;

import java.util.Date;

import eu.anmore.emed.diff.ValueDiff;

public final class AdmissionDiffBuilder {

	public static AdmissionDiffBuilder get() {
		return new AdmissionDiffBuilder(null);
	}

	public static AdmissionDiffBuilder get(final Admission admission) {
		return new AdmissionDiffBuilder(admission);
	}

	public AdmissionDiffBuilder admissionDate(final Date newAdmissionDate) {
		if (admission == null) {
			admissionDiff.admissionDate = ValueDiff.changed(newAdmissionDate);
		} else if (!equals(admission.getAdmissionDate(), newAdmissionDate)) {
			admissionDiff.admissionDate = ValueDiff.changed(newAdmissionDate, admission.getAdmissionDate());
		}
		return this;
	}

	public AdmissionDiffBuilder blood(final String newBlood) {
		if (admission == null) {
			admissionDiff.blood = ValueDiff.changed(newBlood);
		} else if (!equals(admission.getBlood(), newBlood)) {
			admissionDiff.blood = ValueDiff.changed(newBlood, admission.getBlood());
		}
		return this;
	}

	public AdmissionDiffBuilder bodyArea(final Double newBodyArea) {
		if (admission == null) {
			admissionDiff.bodyArea = ValueDiff.changed(newBodyArea);
		} else if (!equals(admission.getBodyArea(), newBodyArea)) {
			admissionDiff.bodyArea = ValueDiff.changed(newBodyArea, admission.getBodyArea());
		}
		return this;
	}

	public AdmissionDiffBuilder death(final boolean newDeath) {
		if (admission == null) {
			admissionDiff.death = ValueDiff.changed(newDeath);
		} else if (!equals(admission.isDeath(), newDeath)) {
			admissionDiff.death = ValueDiff.changed(newDeath, admission.isDeath());
		}
		return this;
	}

	public AdmissionDiffBuilder dischargeDate(final Date newDischargeDate) {
		if (admission == null) {
			admissionDiff.dischargeDate = ValueDiff.changed(newDischargeDate);
		} else if (!equals(admission.getDischargeDate(), newDischargeDate)) {
			admissionDiff.dischargeDate = ValueDiff.changed(newDischargeDate, admission.getDischargeDate());
		}
		return this;
	}

	public AdmissionDiffBuilder dischargePlace(final String newDischargePlace) {
		if (admission == null) {
			admissionDiff.dischargePlace = ValueDiff.changed(newDischargePlace);
		} else if (!equals(admission.getDischargePlace(), newDischargePlace)) {
			admissionDiff.dischargePlace = ValueDiff.changed(newDischargePlace, admission.getDischargePlace());
		}
		return this;
	}

	public AdmissionDiffBuilder height(final Double newHeight) {
		if (admission == null) {
			admissionDiff.height = ValueDiff.changed(newHeight);
		} else if (!equals(admission.getHeight(), newHeight)) {
			admissionDiff.height = ValueDiff.changed(newHeight, admission.getHeight());
		}
		return this;
	}

	public AdmissionDiffBuilder hearthDefect(final String newHearthDefect) {
		if (admission == null) {
			admissionDiff.hearthDefect = ValueDiff.changed(newHearthDefect);
		} else if (!equals(admission.getHearthDefect(), newHearthDefect)) {
			admissionDiff.hearthDefect = ValueDiff.changed(newHearthDefect, admission.getHearthDefect());
		}
		return this;
	}

	public AdmissionDiffBuilder admissionReason(final String newAdmissionReason) {
		if (admission == null) {
			admissionDiff.admissionReason = ValueDiff.changed(newAdmissionReason);
		} else if (!equals(admission.getAdmissionReason(), newAdmissionReason)) {
			admissionDiff.admissionReason = ValueDiff.changed(newAdmissionReason, admission.getAdmissionReason());
		}
		return this;
	}

	public AdmissionDiffBuilder patientId(final int newPatientId) {
		if (admission == null) {
			admissionDiff.patientId = ValueDiff.changed(newPatientId);
		} else if (!equals(admission.getPatientId(), newPatientId)) {
			admissionDiff.patientId = ValueDiff.changed(newPatientId, admission.getPatientId());
		}
		return this;
	}

	public AdmissionDiffBuilder weight(final Double newWeight) {
		if (admission == null) {
			admissionDiff.weight = ValueDiff.changed(newWeight);
		} else if (!equals(admission.getWeight(), newWeight)) {
			admissionDiff.weight = ValueDiff.changed(newWeight, admission.getWeight());
		}
		return this;
	}

	public AdmissionDiff build() {
		if (!admissionDiff.hasChanges()) {
			throw new IllegalArgumentException("Diff has to describe change of at least one parameter.");
		}
		return admissionDiff;
	}

	private boolean equals(final Object objectOne, final Object objectTwo) {
		if (objectOne == null && objectTwo == null) {
			return true;
		} else {
			if (objectOne != null) {
				return objectOne.equals(objectTwo);
			} else {
				return objectTwo.equals(objectOne);
			}
		}
	}

	private AdmissionDiffBuilder(final Admission admission) {
		this.admission = admission;
	}

	private final AdmissionDiff admissionDiff = new AdmissionDiff();;

	private Admission admission;
}
