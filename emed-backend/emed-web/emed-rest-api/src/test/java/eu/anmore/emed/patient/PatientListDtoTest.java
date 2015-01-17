package eu.anmore.emed.patient;

import static eu.anmore.emed.patient.Patient.Sex.MAN;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.anmore.emed.admission.Admission;

/**
 * Test class for {@link PatientListDto}.
 * 
 * @author mmiedzinski
 */
public class PatientListDtoTest {

	@Test
	public void shouldCreatePatientListDto() {
		// given
		final List<Patient> patients = preparePatientList();

		// when
		final PatientListDto patientListDto = PatientListDto.valueOf(patients);

		// then
		Assert.assertNotNull("Patient list shouldn't be null.", patientListDto.getPatients());
		Assert.assertSame("Incorrect elements size on patient list.", patients.size(), patientListDto.getPatients()
				.size());
	}

	@Test
	public void shouldReturnAsPatientList() {
		// given
		final List<Patient> patients = preparePatientList();
		final PatientListDto patientListDto = PatientListDto.valueOf(patients);

		// when
		final List<Patient> result = patientListDto.asPatientList();

		// then
		Assert.assertNotNull("Patient list shouldn't be null.", result);
		Assert.assertSame("Incorrect elements size on patient list.", patientListDto.getPatients().size(),
				result.size());
	}

	private List<Patient> preparePatientList() {
		final Patient patient = new Patient(1, SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY,
				SAMPLE_SEX, new ArrayList<Admission>());

		final List<Patient> patients = new ArrayList<Patient>();
		patients.add(patient);
		patients.add(patient);

		return patients;
	}

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Patient.Sex SAMPLE_SEX = MAN;

	private static final Date SAMPLE_BIRTHDAY = new Date();

}
