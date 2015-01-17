package eu.anmore.emed.admission;

import java.util.Date;

import eu.anmore.emed.helpers.ObjectsCloner;

/**
 * Admission entity class.
 * <p>
 * Admission object representation in DB.
 * </p>
 * 
 * @author mmiedzinski
 */
public class AdmissionEntity {

	public static AdmissionEntity valueOf(final Admission admission) {
		return new AdmissionEntity(admission.getId(), admission.getPatientId(), admission.getAdmissionDate(),
				admission.getBlood(), admission.getHearthDefect(), admission.getWeight(), admission.getBodyArea(),
				admission.getHeight(), admission.isDeath(), admission.getDischargePlace(),
				admission.getDischargeDate(), admission.getAdmissionReason());
	}

	public static AdmissionEntity valueOf(final AdmissionDiff admissionDiff) {
		return new AdmissionEntity(0, admissionDiff.getPatientId().getNewValue(), admissionDiff.getAdmissionDate()
				.getNewValue(), admissionDiff.getBlood().getNewValue(), admissionDiff.getHearthDefect().getNewValue(),
				admissionDiff.getWeight().getNewValue(), admissionDiff.getBodyArea().getNewValue(), admissionDiff
						.getHeight().getNewValue(), admissionDiff.getDeath().getNewValue(), admissionDiff
						.getDischargePlace().getNewValue(), admissionDiff.getDischargeDate().getNewValue(),
				admissionDiff.getAdmissionReason().getNewValue());
	}

	@Override
	public String toString() {
		return String
				.format("AdmissionEntity [id=%s, patientId=%s, admissionDate=%s, bloodType=%s, hearthDefect=%s, weight=%s,"
						+ " bodyArea=%s, height=%s, death=%s, dischargePlace=%s, dischargeDate=%s, admissionReason=%s]",
						id, patientId, admissionDate, bloodType, hearthDefect, weight, bodyArea, height, death,
						dischargePlace, dischargeDate, admissionReason);
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
		if (obj instanceof AdmissionEntity) {
			return id == ((AdmissionEntity) obj).id;
		}
		return false;
	}

	public Admission getAdmission() {
		return new Admission(id, patientId, admissionDate, bloodType, hearthDefect, weight, bodyArea, height, death,
				dischargePlace, dischargeDate, admissionReason);
	}

	AdmissionEntity() {
		super();
	}

	AdmissionEntity(final int id, final int patientId, final Date admissionDate, final String bloodType,
			final String hearthDefect, final Double weight, final Double bodyArea, final Double height,
			final Boolean death, final String dischargePlace, final Date dischargeDate, final String admissionReason) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.admissionDate = admissionDate;
		this.bloodType = bloodType;
		this.hearthDefect = hearthDefect;
		this.weight = weight;
		this.bodyArea = bodyArea;
		this.height = height;
		this.death = death != null ? death : false;
		this.dischargePlace = dischargePlace;
		this.dischargeDate = dischargeDate;
		this.admissionReason = admissionReason;
	}

	int getId() {
		return id;
	}

	void setId(final int id) {
		this.id = id;
	}

	int getPatientId() {
		return patientId;
	}

	void setPatientId(final int patientId) {
		this.patientId = patientId;
	}

	Date getAdmissionDate() {
		return ObjectsCloner.cloneDate(admissionDate);
	}

	void setAdmissionDate(final Date admissionDate) {
		this.admissionDate = ObjectsCloner.cloneDate(admissionDate);
	}

	String getBloodType() {
		return bloodType;
	}

	void setBloodType(final String blood) {
		this.bloodType = blood;
	}

	String getHearthDefect() {
		return hearthDefect;
	}

	void setHearthDefect(final String hearthDefect) {
		this.hearthDefect = hearthDefect;
	}

	Double getWeight() {
		return weight;
	}

	void setWeight(final Double weight) {
		this.weight = weight;
	}

	Double getBodyArea() {
		return bodyArea;
	}

	void setBodyArea(final Double bodyArea) {
		this.bodyArea = bodyArea;
	}

	Double getHeight() {
		return height;
	}

	void setHeight(final Double height) {
		this.height = height;
	}

	boolean isDeath() {
		return death;
	}

	void setDeath(final boolean death) {
		this.death = death;
	}

	String getDischargePlace() {
		return dischargePlace;
	}

	void setDischargePlace(final String dischargePlace) {
		this.dischargePlace = dischargePlace;
	}

	Date getDischargeDate() {
		return ObjectsCloner.cloneDate(dischargeDate);
	}

	void setDischargeDate(final Date dischargeDate) {
		this.dischargeDate = ObjectsCloner.cloneDate(dischargeDate);
	}

	String getAdmissionReason() {
		return admissionReason;
	}

	void setAdmissionReason(final String admissionReason) {
		this.admissionReason = admissionReason;
	}

	private int id;

	private int patientId;

	private Date admissionDate;

	private String bloodType;

	private String hearthDefect;

	private Double weight;

	private Double bodyArea;

	private Double height;

	private boolean death;

	private String dischargePlace;

	private Date dischargeDate;

	private String admissionReason;
}
