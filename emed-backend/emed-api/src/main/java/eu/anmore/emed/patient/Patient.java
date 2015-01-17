package eu.anmore.emed.patient;

import java.util.Date;
import java.util.List;

import eu.anmore.emed.admission.Admission;

/**
 * @author Grzegorz Lipecki
 */
public class Patient {

	/**
	 * Patients sex.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static enum Sex {
		/** man */
		MAN,

		/** woman */
		WOMAN

	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public String getPesel() {
		return pesel;
	}

	public Date getBirthday() {
		return birthday;
	}

	public Sex getSex() {
		return sex;
	}

	public int getId() {
		return id;
	}

	public List<Admission> getAdmissionList() {
		return admissionList;
	}

	@Override
	public String toString() {
		return String.format("Patient [firstName=%s, surname=%s, pesel=%s, birthday=%s, sex=%s]", firstName, surname,
				pesel, birthday, sex);
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
		if (obj instanceof Patient) {
			return id == ((Patient) obj).id;
		}
		return false;
	}

	Patient(final int id, final String firstName, final String surname, final String pesel, final Date birthday,
			final Sex sex, final List<Admission> admissionList) {
		this.firstName = firstName;
		this.surname = surname;
		this.pesel = pesel;
		this.birthday = birthday;
		this.sex = sex;
		this.id = id;
		this.admissionList = admissionList;
	}

	private final String firstName;

	private final String surname;

	private final String pesel;

	private final Date birthday;

	private final Sex sex;

	private final int id;

	private final List<Admission> admissionList;
}
