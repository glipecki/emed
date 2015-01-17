package eu.anmore.emed.patient;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * Tests for {@link PatientDiff}.
 * 
 * @author Grzegorz Lipecki
 */
public class PatientDiffBuilderTest {

	@Test
	public void shouldDescribeNameChange() {
		// given
		final String newName = "new name";

		// when
		final PatientDiff diff = PatientDiffBuilder.get().name(newName).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newName, diff.getName().getNewValue());
	}

	@Test
	public void shouldDescribeNameChangeWithOldValue() {
		// given
		final String newName = "new name";
		final String oldName = "old name";
		final Patient patient = new Patient(1, oldName, null, null, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).name(newName).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newName, diff.getName().getNewValue());
		assertEquals(oldName, diff.getName().getOldValue());
	}

	@Test
	public void shouldDescribeSurnameChange() {
		// given
		final String newSurname = "new surname";

		// when
		final PatientDiff diff = PatientDiffBuilder.get().surname(newSurname).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newSurname, diff.getSurname().getNewValue());
	}

	@Test
	public void shouldDescribeSurnameChangeWithOldValue() {
		// given
		final String newSurname = "new surname";
		final String oldSurname = "old surname";
		final Patient patient = new Patient(1, null, oldSurname, null, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).surname(newSurname).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newSurname, diff.getSurname().getNewValue());
		assertEquals(oldSurname, diff.getSurname().getOldValue());
	}

	@Test
	public void shouldDescribeBirthdayChange() {
		// given
		final LocalDate oldBirthday = new LocalDate();
		final LocalDate newBirthday = oldBirthday.minusYears(1);
		final Patient patient = new Patient(1, null, null, null, oldBirthday.toDate(), null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).birthday(newBirthday.toDate()).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newBirthday.toDate(), diff.getBirthday().getNewValue());
		assertEquals(oldBirthday.toDate(), diff.getBirthday().getOldValue());
	}

	@Test
	public void shouldDescribeSexChange() {
		// given
		final Patient.Sex oldSex = Patient.Sex.WOMAN;
		final Patient.Sex newSex = Patient.Sex.MAN;
		final Patient patient = new Patient(1, null, null, null, null, oldSex, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).sex(newSex).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newSex, diff.getSex().getNewValue());
		assertEquals(oldSex, diff.getSex().getOldValue());
	}

	@Test
	public void shouldDescribePeselChange() {
		// given
		final String newPesel = "89031004513";

		// when
		final PatientDiff diff = PatientDiffBuilder.get().pesel(newPesel).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newPesel, diff.getPesel().getNewValue());
	}

	@Test
	public void shouldDescribePeselChangeWithOldValue() {
		// given
		final String newPesel = "89031004513";
		final String oldPesel = "89041004513";
		final Patient patient = new Patient(1, null, null, oldPesel, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).pesel(newPesel).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newPesel, diff.getPesel().getNewValue());
		assertEquals(oldPesel, diff.getPesel().getOldValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenNotChanged() {
		// given
		final Patient patient = new Patient(1, null, null, null, null, null, null);

		// when
		PatientDiffBuilder.get(patient).build();

		// then
	}

	@Test
	public void shouldntStoreUnchangedName() {
		// given
		final String oldName = "name";
		final String newName = oldName;
		final Patient patient = new Patient(1, oldName, null, null, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).name(newName).pesel("").build();

		// then
		assertThat(diff.getName().isChanged()).isFalse();
	}

	@Test
	public void shouldStoreNameWhenPatientWithoutName() {
		// given
		final String name = "name";
		final Patient patient = new Patient(1, null, null, null, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).name(name).pesel("").build();

		// then
		assertThat(diff.hasChanges()).isTrue();
		assertThat(diff.getName().isChanged()).isTrue();
	}

	@Test
	public void shouldStorePeselWhenPatientWithoutPesel() {
		// given
		final String pesel = "name";
		final Patient patient = new Patient(1, null, null, null, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).pesel(pesel).name("").build();

		// then
		assertThat(diff.hasChanges()).isTrue();
		assertThat(diff.getPesel().isChanged()).isTrue();
	}

	@Test
	public void shouldntStoreUnchangedSurname() {
		// given
		final String oldSurname = "surname";
		final String newSurname = oldSurname;
		final Patient patient = new Patient(1, null, oldSurname, null, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).surname(newSurname).pesel("").build();

		// then
		assertThat(diff.getSurname().isChanged()).isFalse();
	}

	@Test
	public void shouldntStoreUnchangedPesel() {
		// given
		final String oldPesel = "pesel";
		final String newPesel = oldPesel;
		final Patient patient = new Patient(1, null, null, oldPesel, null, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).pesel(newPesel).surname("").build();

		// then
		assertThat(diff.getPesel().isChanged()).isFalse();
	}

	@Test
	public void shouldntStoreUnchangedBirthday() {
		// given
		final Date oldBirthday = new Date();
		final Date newBirthday = oldBirthday;
		final Patient patient = new Patient(1, null, null, null, oldBirthday, null, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).birthday(newBirthday).pesel("").build();

		// then
		assertThat(diff.getBirthday().isChanged()).isFalse();
	}

	@Test
	public void shouldntStoreUnchangedSex() {
		// given
		final Patient.Sex oldSex = Patient.Sex.WOMAN;
		final Patient.Sex newSex = oldSex;
		final Patient patient = new Patient(1, null, null, null, null, oldSex, null);

		// when
		final PatientDiff diff = PatientDiffBuilder.get(patient).sex(newSex).pesel("").build();

		// then
		assertThat(diff.getSex().isChanged()).isFalse();
	}
}
