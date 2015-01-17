package eu.anmore.emed.patient;

import static eu.anmore.emed.patient.Patient.Sex.MAN;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.anmore.emed.admission.Admission;

/**
 * Test class for {@link PatientDto}.
 * 
 * @author mmiedzinski
 */
public class PatientDtoTest {

	@Before
	public void setUp() {
		final Admission admissionMock = mock(Admission.class);
		ADMISSION_LIST.add(admissionMock);
		ADMISSION_LIST.add(admissionMock);
	}

	@Test
	public void shouldCreatePatientDtoFromPatient() {
		// given
		final Patient patient = new Patient(1, SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY,
				SAMPLE_SEX, ADMISSION_LIST);

		// when
		final PatientDto result = PatientDto.valueOf(patient);

		// then
		assertNotNull("Patient shouldn't be null.", result);
		assertThat(result.getId()).isEqualTo(patient.getId());
		assertEquals("Incorrect firstName value.", patient.getFirstName(), result.getFirstName());
		assertEquals("Incorrect surname value.", patient.getSurname(), result.getSurname());
		assertEquals("Incorrect pesel value.", patient.getPesel(), result.getPesel());
		assertEquals(patient.getBirthday(), result.getBirthday());
		assertEquals(patient.getSex(), result.getSex());
		assertEquals(patient.getAdmissionList().size(), result.getAdmissionList().size());
	}

	@Test
	public void shouldCreatePatientFromDto() {
		// given
		final Patient patient = new Patient(1, SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY,
				SAMPLE_SEX, ADMISSION_LIST);
		final PatientDto patientDto = PatientDto.valueOf(patient);

		// when
		final Patient result = patientDto.asPatient();

		// then
		assertNotNull("Patient shouldn't be null.", result);
		assertThat(result.getId()).isEqualTo(patient.getId());
		assertEquals("Incorrect firstName value.", patientDto.getFirstName(), result.getFirstName());
		assertEquals("Incorrect surname value.", patientDto.getSurname(), result.getSurname());
		assertEquals("Incorrect pesel value.", patientDto.getPesel(), result.getPesel());
		assertEquals(patientDto.getBirthday(), result.getBirthday());
		assertEquals(patientDto.getSex(), result.getSex());
		assertEquals(patientDto.getAdmissionList().size(), result.getAdmissionList().size());
	}

	private static final List<Admission> ADMISSION_LIST = new ArrayList<Admission>();

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Date SAMPLE_BIRTHDAY = new Date();

	private static final Patient.Sex SAMPLE_SEX = MAN;

}
