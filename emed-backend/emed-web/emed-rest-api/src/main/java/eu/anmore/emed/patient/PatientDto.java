package eu.anmore.emed.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.anmore.emed.admission.Admission;
import eu.anmore.emed.admission.AdmissionDto;
import eu.anmore.emed.helpers.ObjectsCloner;

/**
 * Patient transfer object.
 * 
 * @author mmiedzinski
 */
public class PatientDto {

	public static PatientDto valueOf(final Patient patient) {
		final PatientDto patientDto = new PatientDto();
		patientDto.id = patient.getId();
		patientDto.firstName = patient.getFirstName();
		patientDto.surname = patient.getSurname();
		patientDto.pesel = patient.getPesel();
		patientDto.birthday = patient.getBirthday();
		patientDto.sex = patient.getSex();

		patientDto.admissionList = new ArrayList<AdmissionDto>();
		for (final Admission admission : patient.getAdmissionList()) {
			patientDto.admissionList.add(AdmissionDto.valueOf(admission));
		}

		return patientDto;
	}

	public Patient asPatient() {
		return new Patient(id, firstName, surname, pesel, birthday, sex, convertAdmissionDtosToAdmissionList());
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(final String pesel) {
		this.pesel = pesel;
	}

	public Date getBirthday() {
		return ObjectsCloner.cloneDate(birthday);
	}

	public void setBirthday(final Date birthday) {
		this.birthday = ObjectsCloner.cloneDate(birthday);
	}

	public Patient.Sex getSex() {
		return sex;
	}

	public void setSex(final Patient.Sex sex) {
		this.sex = sex;
	}

	public List<AdmissionDto> getAdmissionList() {
		return admissionList;
	}

	public void setAdmissionList(final List<AdmissionDto> admissionList) {
		this.admissionList = admissionList;
	}

	@Override
	public String toString() {
		return String.format(
				"PatientDto [firstName=%s, surname=%s, pesel=%s, birthday=%s, sex=%s, id=%s, admissionList=%s]",
				firstName, surname, pesel, birthday, sex, id, admissionList);
	}

	private List<Admission> convertAdmissionDtosToAdmissionList() {
		final List<Admission> admissions = new ArrayList<Admission>();
		for (final AdmissionDto admissionDto : this.admissionList) {
			admissions.add(admissionDto.asAdmission());
		}
		return admissions;
	}

	private String firstName;

	private String surname;

	private String pesel;

	private Date birthday;

	private Patient.Sex sex;

	private int id;

	private List<AdmissionDto> admissionList;
}
