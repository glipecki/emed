package eu.anmore.emed.patient;

import static eu.anmore.emed.patient.Patient.Sex.MAN;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.anmore.emed.admission.Admission;

/**
 * Test class for {@link PatientsControllerImpl}.
 * 
 * @author mmiedzinski
 */
public class PatientsControllerImplTest {

	@Before
	public void setUp() {
		patientBeanMock = mock(PatientsBean.class);
		patientController = new PatientsControllerImpl(patientBeanMock);
		samplePatient = new Patient(1, SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY, SAMPLE_SEX,
				new ArrayList<Admission>());
	}

	@Test
	public void shouldReturnPatientList() {
		// given
		final List<Patient> patients = Arrays.asList(samplePatient, samplePatient);
		when(patientBeanMock.getAll()).thenReturn(patients);

		// when
		final PatientListDto result = patientController.callGetAll();

		// then
		assertNotNull("Patient list shouldn't be null.", result.getPatients());
		assertSame("Incorrect elements size on patient list.", patients.size(), result.getPatients().size());
	}

	@Test
	public void shouldReturnUpdatedPatient() {
		// given
		final int id = 1;
		final PatientDiff patientDiff = mock(PatientDiff.class);

		when(patientBeanMock.edit(id, patientDiff)).thenReturn(samplePatient);

		// when
		final PatientDto result = patientController.callEdit(id, patientDiff);

		// then
		assertEquals(samplePatient.getId(), result.getId());
		assertEquals(samplePatient.getFirstName(), result.getFirstName());
		assertEquals(samplePatient.getSurname(), result.getSurname());
		assertEquals(samplePatient.getPesel(), result.getPesel());
		assertEquals(samplePatient.getBirthday(), result.getBirthday());
		assertEquals(samplePatient.getSex(), result.getSex());
	}

	@Test
	public void shouldReturnInsertedPatient() {
		// given
		final PatientDiff patientDiff = mock(PatientDiff.class);
		when(patientBeanMock.insert(patientDiff)).thenReturn(samplePatient);

		// when
		final PatientDto result = patientController.callInsert(patientDiff);

		// then
		assertEquals(samplePatient.getId(), result.getId());
		assertEquals(samplePatient.getFirstName(), result.getFirstName());
		assertEquals(samplePatient.getSurname(), result.getSurname());
		assertEquals(samplePatient.getPesel(), result.getPesel());
		assertEquals(samplePatient.getBirthday(), result.getBirthday());
		assertEquals(samplePatient.getSex(), result.getSex());
	}

	@Test
	public void shouldCallRepositoryToGetPatientWithAdmission() {
		// given
		final int patientId = 2;
		when(patientBeanMock.getPatientWithAdmissionList(patientId)).thenReturn(samplePatient);

		// when
		patientController.callGetPatientWithAdmission(patientId);

		// then
		verify(patientBeanMock).getPatientWithAdmissionList(patientId);
	}

	@Test
	public void shouldReturnPatientDtoWhileGettingPatientWithAdmission() {
		// given
		final int patientId = 2;
		when(patientBeanMock.getPatientWithAdmissionList(patientId)).thenReturn(samplePatient);

		// when
		final PatientDto result = patientController.callGetPatientWithAdmission(patientId);

		// then
		assertEquals(samplePatient.getId(), result.getId());
		assertEquals(samplePatient.getFirstName(), result.getFirstName());
		assertEquals(samplePatient.getSurname(), result.getSurname());
		assertEquals(samplePatient.getPesel(), result.getPesel());
		assertEquals(samplePatient.getBirthday(), result.getBirthday());
		assertEquals(samplePatient.getSex(), result.getSex());
	}

	@Test
	public void shouldReturnAdmittedPatientList() {
		// given
		final List<Patient> patients = Arrays.asList(samplePatient, samplePatient);
		when(patientBeanMock.getAdmitted()).thenReturn(patients);

		// when
		final PatientListDto result = patientController.callGetAdmitted();

		// then
		assertNotNull(result.getPatients());
		assertSame(patients.size(), result.getPatients().size());
	}

	@Test
	public void shouldReturnNotAdmittedPatientList() {
		// given
		final List<Patient> patients = Arrays.asList(samplePatient, samplePatient);
		when(patientBeanMock.getNotAdmitted()).thenReturn(patients);

		// when
		final PatientListDto result = patientController.callGetNotAdmitted();

		// then
		assertNotNull(result.getPatients());
		assertSame(patients.size(), result.getPatients().size());
	}

	@Test
	public void shouldReturnAdmittedPatientListWithAdmissions() {
		// given
		final List<Patient> patients = Arrays.asList(samplePatient, samplePatient);
		when(patientBeanMock.getAdmittedPatientsWithAdmissions()).thenReturn(patients);

		// when
		final PatientListDto result = patientController.callGetAdmittedPatientsWithAdmissions();

		// then
		assertNotNull(result.getPatients());
		assertSame(patients.size(), result.getPatients().size());
	}

	private Patient samplePatient;

	private PatientsBean patientBeanMock;

	private PatientsController patientController;

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Date SAMPLE_BIRTHDAY = new Date();

	private static final Patient.Sex SAMPLE_SEX = MAN;
}
