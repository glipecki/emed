package eu.anmore.emed.patient;

import java.util.Date;

import eu.anmore.emed.diff.DiffBuilder;

/**
 * @author Grzegorz Lipecki
 */
public final class PatientDiffBuilder extends DiffBuilder<Patient, PatientDiff> {

	public static PatientDiffBuilder get() {
		return new PatientDiffBuilder();
	}

	public static PatientDiffBuilder get(final Patient patient) {
		return new PatientDiffBuilder(patient);
	}

	public PatientDiffBuilder name(final String newName) {
		diff.name = getAttributeDiff(newName, object != null ? object.getFirstName() : null);
		return this;
	}

	public PatientDiffBuilder surname(final String newSurname) {
		diff.surname = getAttributeDiff(newSurname, object != null ? object.getSurname() : null);
		return this;
	}

	public PatientDiffBuilder pesel(final String newPesel) {
		diff.pesel = getAttributeDiff(newPesel, object != null ? object.getPesel() : null);
		return this;
	}

	public PatientDiffBuilder birthday(final Date newBirthday) {
		diff.birthday = getAttributeDiff(newBirthday, object != null ? object.getBirthday() : null);
		return this;
	}

	public PatientDiffBuilder sex(final Patient.Sex newSex) {
		diff.sex = getAttributeDiff(newSex, object != null ? object.getSex() : null);
		return this;
	}

	private PatientDiffBuilder() {
		super(new PatientDiff());
	}

	private PatientDiffBuilder(final Patient patient) {
		super(new PatientDiff(), patient);
	}

}
