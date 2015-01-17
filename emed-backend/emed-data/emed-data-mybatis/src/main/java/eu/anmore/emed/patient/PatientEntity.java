package eu.anmore.emed.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.anmore.emed.admission.Admission;
import eu.anmore.emed.admission.AdmissionEntity;
import eu.anmore.emed.helpers.ObjectsCloner;

/**
 * Patient entity.
 * <p>
 * Patient object representation in DB.
 * </p>
 * 
 * @author mmiedzinski
 */
public class PatientEntity {

	@Override
	public String toString() {
		return String.format(
				"PatientEntity [id=%s, firstName=%s, surname=%s, pesel=%s, birthday=%s, sex=%s, admissions=%s]", id,
				firstName, surname, pesel, birthday, sex, admissions);
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
		if (obj instanceof PatientEntity) {
			return id == ((PatientEntity) obj).id;
		}
		return false;
	}

	static final String ID_COLUMN_NAME = "id";

	static final String FIRSTNAME_COLUMN_NAME = "first_name";

	static final String SURNAME_COLUMN_NAME = "surname";

	static final String PESEL_COLUMN_NAME = "pesel";

	static final String BIRTHDAY_COLUMN_NAME = "birthday";

	static final String SEX_COLUMN_NAME = "sex";

	PatientEntity() {
		super();
	}

	PatientEntity(final String firstName, final String surname, final String pesel, final Date birthday,
			final Patient.Sex sex, final List<AdmissionEntity> admissions) {
		super();
		this.firstName = firstName;
		this.surname = surname;
		this.pesel = pesel;
		this.birthday = birthday;
		this.sex = sex;
		this.admissions = admissions;
	}

	int getId() {
		return id;
	}

	void setId(final int id) {
		this.id = id;
	}

	String getFirstName() {
		return firstName;
	}

	void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	String getSurname() {
		return surname;
	}

	void setSurname(final String surname) {
		this.surname = surname;
	}

	String getPesel() {
		return pesel;
	}

	void setPesel(final String pesel) {
		this.pesel = pesel;
	}

	Date getBirthday() {
		return ObjectsCloner.cloneDate(birthday);
	}

	void setBirthday(final Date birthday) {
		this.birthday = ObjectsCloner.cloneDate(birthday);
	}

	Patient.Sex getSex() {
		return sex;
	}

	void setSex(final Patient.Sex sex) {
		this.sex = sex;
	}

	List<AdmissionEntity> getAdmissions() {
		return admissions;
	}

	void setAdmissions(final List<AdmissionEntity> admissions) {
		this.admissions = admissions;
	}

	Patient getPatient() {
		return new Patient(id, firstName, surname, pesel, birthday, sex, getAdmissionList());
	}

	private List<Admission> getAdmissionList() {
		final List<Admission> admissions = new ArrayList<Admission>();

		if (this.admissions != null) {
			for (final AdmissionEntity entity : this.admissions) {
				admissions.add(entity.getAdmission());
			}
		}

		return admissions;
	}

	private int id;

	private String firstName;

	private String surname;

	private String pesel;

	private Date birthday;

	private Patient.Sex sex;

	private List<AdmissionEntity> admissions;
}
