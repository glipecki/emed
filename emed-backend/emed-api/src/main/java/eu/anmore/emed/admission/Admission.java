package eu.anmore.emed.admission;

import java.util.Date;

public class Admission {

	public int getId() {
		return id;
	}

	public int getPatientId() {
		return patientId;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public String getBlood() {
		return blood;
	}

	public String getHearthDefect() {
		return hearthDefect;
	}

	public Double getWeight() {
		return weight;
	}

	public Double getBodyArea() {
		return bodyArea;
	}

	public Double getHeight() {
		return height;
	}

	public boolean isDeath() {
		return death;
	}

	public String getDischargePlace() {
		return dischargePlace;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public String getAdmissionReason() {
		return admissionReason;
	}

	@Override
	public String toString() {
		return String.format("Admission [id=%s, patientId=%s, admissionDate=%s, blood=%s, hearthDefect=%s, weight=%s, "
				+ "bodyArea=%s, height=%s, death=%s, dischargePlace=%s, dischargeDate=%s, admissionReason=%s]", id,
				patientId, admissionDate, blood, hearthDefect, weight, bodyArea, height, death, dischargePlace,
				dischargeDate, admissionReason);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime + id;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Admission) {
			return id == ((Admission) obj).id;
		}
		return false;
	}

	Admission(final int id, final int patientId, final Date admissionDate, final String blood,
			final String hearthDefect, final Double weight, final Double bodyArea, final Double height,
			final boolean death, final String dischargePlace, final Date dischargeDate, final String admissionReason) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.admissionDate = admissionDate;
		this.blood = blood;
		this.hearthDefect = hearthDefect;
		this.weight = weight;
		this.bodyArea = bodyArea;
		this.height = height;
		this.death = death;
		this.dischargePlace = dischargePlace;
		this.dischargeDate = dischargeDate;
		this.admissionReason = admissionReason;
	}

	private final int id;

	private final int patientId;

	private final Date admissionDate;

	private final String blood;

	private final String hearthDefect;

	private final Double weight;

	private final Double bodyArea;

	private final Double height;

	private final boolean death;

	private final String dischargePlace;

	private final Date dischargeDate;

	private final String admissionReason;
}
