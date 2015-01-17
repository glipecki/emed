package eu.anmore.emed.patient;

import java.util.Date;

import eu.anmore.emed.diff.ObjectDiff;
import eu.anmore.emed.diff.ValueDiff;

/**
 * Difference definition between two {@link Patient}.
 * 
 * @author Grzegorz Lipecki
 */
public class PatientDiff implements ObjectDiff<Patient> {

	public ValueDiff<String> getName() {
		return name;
	}

	public ValueDiff<String> getSurname() {
		return surname;
	}

	public ValueDiff<String> getPesel() {
		return pesel;
	}

	public ValueDiff<Date> getBirthday() {
		return birthday;
	}

	public ValueDiff<Patient.Sex> getSex() {
		return sex;
	}

	@Override
	public boolean hasChanges() {
		return name.isChanged() || surname.isChanged() || pesel.isChanged() || birthday.isChanged() || sex.isChanged();
	}

	ValueDiff<String> name = ValueDiff.notChanged();

	ValueDiff<String> surname = ValueDiff.notChanged();

	ValueDiff<String> pesel = ValueDiff.notChanged();

	ValueDiff<Date> birthday = ValueDiff.notChanged();

	ValueDiff<Patient.Sex> sex = ValueDiff.notChanged();
}
