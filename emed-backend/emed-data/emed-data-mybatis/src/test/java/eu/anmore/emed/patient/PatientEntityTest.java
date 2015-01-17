package eu.anmore.emed.patient;

import static eu.anmore.emed.patient.Patient.Sex.MAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import eu.anmore.emed.admission.AdmissionEntity;

/**
 * Test class for {@link PatientEntity}.
 * 
 * @author mmiedzinski
 */
public class PatientEntityTest {

	@Test
	public void shouldCreatePatientObject() {
		// given
		final PatientEntity patientEntity = new PatientEntity(SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL,
				SAMPLE_BIRTHDAY, SAMPLE_SEX, ADMISSION_LIST);

		// when
		final Patient patient = patientEntity.getPatient();

		// then
		assertNotNull(patient);
		Assertions.assertThat(patient.getId()).isEqualTo(patientEntity.getId());
		assertEquals(patientEntity.getFirstName(), patient.getFirstName());
		assertEquals(patientEntity.getSurname(), patient.getSurname());
		assertEquals(patientEntity.getPesel(), patient.getPesel());
		assertEquals(patientEntity.getBirthday(), patient.getBirthday());
		assertEquals(patientEntity.getSex(), patient.getSex());
	}

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Date SAMPLE_BIRTHDAY = new Date();

	private static final Patient.Sex SAMPLE_SEX = MAN;

	private static final List<AdmissionEntity> ADMISSION_LIST = new ArrayList<AdmissionEntity>();
}
