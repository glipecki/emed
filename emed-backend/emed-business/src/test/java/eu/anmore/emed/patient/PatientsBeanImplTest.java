package eu.anmore.emed.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PatientsBeanImpl}
 * 
 * @author mmiedzinski
 */
public class PatientsBeanImplTest {

	@Before
	public void setUp() {
		patientRepositoryMock = mock(PatientsDao.class);
		patientBean = new PatientsBeanImpl(patientRepositoryMock);
	}

	@Test
	public void shouldReturnPatientList() {
		// given
		final List<Patient> patientListMock = prepareSampleData();
		when(patientRepositoryMock.getAll()).thenReturn(patientListMock);

		// when
		final List<Patient> result = patientBean.getAll();

		// then
		assertNotNull("Result shouldn't be null.", result);
		assertEquals("Incorrect result data.", patientListMock, result);
	}

	@Test
	public void shouldEditAndReturnEditedPatient() {
		// given
		final int id = 1;
		final PatientDiff patientDiff = mock(PatientDiff.class);

		final Patient patientMock = mock(Patient.class);
		when(patientRepositoryMock.getById(id)).thenReturn(patientMock);

		// when
		final Patient result = patientBean.edit(id, patientDiff);

		// then
		verify(patientRepositoryMock).edit(id, patientDiff);
		assertEquals(patientMock, result);
	}

	@Test
	public void shouldInsertAndReturnPatient() {
		// given
		final PatientDiff patientDiff = mock(PatientDiff.class);
		final Patient patient = mock(Patient.class);

		when(patientRepositoryMock.insert(patientDiff)).thenReturn(patient);

		// when
		final Patient result = patientBean.insert(patientDiff);

		// then
		verify(patientRepositoryMock).insert(patientDiff);
		assertEquals(patient, result);
	}

	@Test
	public void shouldCallRepositoryWhileGetPatientWithAdmission() {
		// given
		int patientId = 1;

		// when
		patientBean.getPatientWithAdmissionList(patientId);

		// then
		verify(patientRepositoryMock).getPatientWithAdmissionList(patientId);
	}

	@Test
	public void shouldReturnPatientWithAdmission() {
		// given
		int patientId = 1;
		Patient patientMock = mock(Patient.class);
		when(patientRepositoryMock.getPatientWithAdmissionList(patientId)).thenReturn(patientMock);

		// when
		final Patient result = patientBean.getPatientWithAdmissionList(patientId);

		// then
		assertEquals(patientMock, result);
	}

	@Test
	public void shouldReturnAdmittedPatientList() {
		// given
		final List<Patient> patientListMock = prepareSampleData();
		when(patientRepositoryMock.getAdmitted()).thenReturn(patientListMock);

		// when
		final List<Patient> result = patientBean.getAdmitted();

		// then
		assertNotNull(result);
		assertEquals(patientListMock, result);
	}

	@Test
	public void shouldReturnNotAdmittedPatientList() {
		// given
		final List<Patient> patientListMock = prepareSampleData();
		when(patientRepositoryMock.getNotAdmitted()).thenReturn(patientListMock);

		// when
		final List<Patient> result = patientBean.getNotAdmitted();

		// then
		assertNotNull(result);
		assertEquals(patientListMock, result);
	}

	@Test
	public void shouldReturnAdmittedPatientListWithAdmissions() {
		// given
		final List<Patient> patientListMock = prepareSampleData();
		when(patientRepositoryMock.getAdmittedPatientsWithAdmissions()).thenReturn(patientListMock);

		// when
		final List<Patient> result = patientBean.getAdmittedPatientsWithAdmissions();

		// then
		assertNotNull(result);
		assertEquals(patientListMock, result);
	}

	private List<Patient> prepareSampleData() {
		final List<Patient> patientListMock = new ArrayList<Patient>();
		patientListMock.add(new Patient(1, null, null, null, null, null, null));
		patientListMock.add(new Patient(2, null, null, null, null, null, null));
		return patientListMock;
	}

	private PatientsBean patientBean;

	private PatientsDao patientRepositoryMock;
}
