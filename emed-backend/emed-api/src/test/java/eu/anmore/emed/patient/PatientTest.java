package eu.anmore.emed.patient;

import static eu.anmore.emed.patient.Patient.Sex.MAN;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import eu.anmore.emed.admission.Admission;

/**
 * @author Grzegorz Lipecki
 */
public class PatientTest {

	@Test
	public void shouldCreatePatient() {
		// given

		// when
		final Patient patient = new Patient(1, SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY,
				SAMPLE_SEX, ADMISSION_LIST);

		// then
		assertEquals("Incorrect firstName value.", SAMPLE_FIRSTNAME, patient.getFirstName());
		assertEquals("Incorrect surname value.", SAMPLE_SURNAME, patient.getSurname());
		assertEquals("Incorrect pesel value.", SAMPLE_PESEL, patient.getPesel());
		assertEquals(SAMPLE_BIRTHDAY, patient.getBirthday());
		assertEquals(SAMPLE_SEX, patient.getSex());
		assertEquals(ADMISSION_LIST, patient.getAdmissionList());
	}

	@Test
	public void shouldCreatePatientWithId() {
		// given
		final int id = 5;

		// when
		final Patient patient = new Patient(id, SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY,
				SAMPLE_SEX, ADMISSION_LIST);

		// then
		assertThat(patient.getFirstName()).isEqualTo(SAMPLE_FIRSTNAME);
		assertThat(patient.getSurname()).isEqualTo(SAMPLE_SURNAME);
		assertThat(patient.getPesel()).isEqualTo(SAMPLE_PESEL);
		assertThat(patient.getBirthday()).isEqualTo(SAMPLE_BIRTHDAY);
		assertThat(patient.getSex()).isEqualTo(SAMPLE_SEX);
		assertThat(patient.getId()).isEqualTo(id);
		assertThat(patient.getAdmissionList()).isEqualTo(ADMISSION_LIST);
	}

	private static final List<Admission> ADMISSION_LIST = new ArrayList<Admission>();

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Patient.Sex SAMPLE_SEX = MAN;

	private static final Date SAMPLE_BIRTHDAY = new Date();

}
