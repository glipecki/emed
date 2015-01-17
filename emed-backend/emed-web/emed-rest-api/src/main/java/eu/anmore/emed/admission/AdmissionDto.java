package eu.anmore.emed.admission;

import java.util.Date;

import eu.anmore.emed.helpers.ObjectsCloner;

/**
 * Admission transfer object.
 * 
 * @author mmiedzinski
 */
public class AdmissionDto {

	public static AdmissionDto valueOf(final Admission admission) {
		final AdmissionDto admissionDto = new AdmissionDto();
		admissionDto.setId(admission.getId());
		admissionDto.setAdmissionDate(admission.getAdmissionDate());
		admissionDto.setBlood(admission.getBlood());
		admissionDto.setBodyArea(admission.getBodyArea());
		admissionDto.setDeath(admission.isDeath());
		admissionDto.setDischargeDate(admission.getDischargeDate());
		admissionDto.setDischargePlace(admission.getDischargePlace());
		admissionDto.setHeight(admission.getHeight());
		admissionDto.setHearthDefect(admission.getHearthDefect());
		admissionDto.setPatientId(admission.getPatientId());
		admissionDto.setWeight(admission.getWeight());
		admissionDto.setAdmissionReason(admission.getAdmissionReason());
		return admissionDto;
	}

	public Admission asAdmission() {
		return new Admission(id, patientId, admissionDate, blood, hearthDefect, weight, bodyArea, height, death,
				dischargePlace, dischargeDate, admissionReason);
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(final int patientId) {
		this.patientId = patientId;
	}

	public Date getAdmissionDate() {
		return ObjectsCloner.cloneDate(admissionDate);
	}

	public void setAdmissionDate(final Date admissionDate) {
		this.admissionDate = ObjectsCloner.cloneDate(admissionDate);
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(final String blood) {
		this.blood = blood;
	}

	public String getHearthDefect() {
		return hearthDefect;
	}

	public void setHearthDefect(final String hearthDefect) {
		this.hearthDefect = hearthDefect;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(final Double weight) {
		this.weight = weight;
	}

	public Double getBodyArea() {
		return bodyArea;
	}

	public void setBodyArea(final Double bodyArea) {
		this.bodyArea = bodyArea;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(final Double height) {
		this.height = height;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(final boolean death) {
		this.death = death;
	}

	public String getDischargePlace() {
		return dischargePlace;
	}

	public void setDischargePlace(final String dischargePlace) {
		this.dischargePlace = dischargePlace;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(final Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getAdmissionReason() {
		return admissionReason;
	}

	public void setAdmissionReason(final String admissionReason) {
		this.admissionReason = admissionReason;
	}

	@Override
	public String toString() {
		return String.format(
				"AdmissionDto [id=%s, patientId=%s, admissionDate=%s, blood=%s, hearthDefect=%s, weight=%s, "
						+ "bodyArea=%s, height=%s, death=%s, dischargePlace=%s, dischargeDate=%s, admissionReason=%s]",
				id, patientId, admissionDate, blood, hearthDefect, weight, bodyArea, height, death, dischargePlace,
				dischargeDate, admissionReason);
	}

	private int id;

	private int patientId;

	private Date admissionDate;

	private String blood;

	private String hearthDefect;

	private Double weight;

	private Double bodyArea;

	private Double height;

	private boolean death;

	private String dischargePlace;

	private Date dischargeDate;

	private String admissionReason;
}
