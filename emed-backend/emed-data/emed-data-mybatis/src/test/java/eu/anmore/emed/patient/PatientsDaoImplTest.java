package eu.anmore.emed.patient;

import static eu.anmore.emed.patient.Patient.Sex.MAN;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.anmore.emed.admission.AdmissionEntity;

/**
 * Test class for {@link PatientsDaoImpl}.
 * 
 * @author mmiedzinski
 */
public class PatientsDaoImplTest {

	@Before
	public void setUp() {
		patientMapperMock = mock(PatientsMapper.class);
		patientDao = new PatientsDaoImpl(patientMapperMock);
		patientEntity = new PatientEntity(SAMPLE_FIRSTNAME, SAMPLE_SURNAME, SAMPLE_PESEL, SAMPLE_BIRTHDAY, SAMPLE_SEX,
				ADMISSION_LIST);
	}

	@Test
	public void shouldGetPatientList() {
		// given
		final List<PatientEntity> mockedData = Arrays.asList(patientEntity, patientEntity);
		when(patientMapperMock.queryPatients(any(PatientsQuery.class))).thenReturn(mockedData);

		// when
		final List<Patient> result = patientDao.getAll();

		// then
		assertNotNull(result);
		assertSame(mockedData.size(), result.size());
	}

	@Test
	public void shouldEditPatient() {
		// given
		final int id = 1;
		final PatientDiff patientDiff = mock(PatientDiff.class);

		// when
		patientDao.edit(id, patientDiff);

		// then
		verify(patientMapperMock).edit((EditPatientCommand) anyObject());
	}

	@Test
	public void shouldGetPatentById() {
		// given
		when(patientMapperMock.queryPatients(any(PatientsQuery.class))).thenReturn(Arrays.asList(patientEntity));

		// when
		final Patient result = patientDao.getById(patientEntity.getId());

		// then
		assertEquals(patientEntity.getId(), result.getId());
		assertEquals(patientEntity.getFirstName(), result.getFirstName());
		assertEquals(patientEntity.getSurname(), result.getSurname());
		assertEquals(patientEntity.getPesel(), result.getPesel());
		assertEquals(patientEntity.getBirthday(), result.getBirthday());
		assertEquals(patientEntity.getSex(), result.getSex());
	}

	@Test
	public void shouldCallInsertMapperMethod() {
		// given
		final PatientDiff patientDif = generateSamplePatientDiff();

		final PatientEntity expectedEntity = new PatientEntity("name", "surname", "89031004513",
				patientDif.birthday.getNewValue(), Patient.Sex.WOMAN, ADMISSION_LIST);

		// when
		patientDao.insert(patientDif);

		// then
		verify(patientMapperMock).insert(eq(expectedEntity));
	}

	@Test
	public void shouldReturnPatientAfterInsert() {
		// when
		final PatientDiff patientDif = generateSamplePatientDiff();

		// when
		final Patient result = patientDao.insert(patientDif);

		// then
		assertNotNull(result);
		assertEquals(patientDif.name.getNewValue(), result.getFirstName());
		assertEquals(patientDif.surname.getNewValue(), result.getSurname());
		assertEquals(patientDif.pesel.getNewValue(), result.getPesel());
		assertEquals(patientDif.birthday.getNewValue(), result.getBirthday());
		assertEquals(patientDif.sex.getNewValue(), result.getSex());
	}

	@Test
	public void shouldGetPatientWithAdmissionList() {
		// given
		final int id = 2;
		when(patientMapperMock.queryPatientsWithAdmissions((PatientsQuery) any())).thenReturn(
				Arrays.asList(patientEntity));

		// when
		final Patient result = patientDao.getPatientWithAdmissionList(id);

		// then
		notNull(result);
	}

	@Test
	public void shouldGetAdmittedPatientList() {
		// given
		final List<PatientEntity> mockedData = Arrays.asList(patientEntity, patientEntity);
		when(patientMapperMock.queryAdmittedPatients(any(PatientsQuery.class))).thenReturn(mockedData);

		// when
		final List<Patient> result = patientDao.getAdmitted();

		// then
		assertNotNull(result);
		assertSame(mockedData.size(), result.size());
	}

	@Test
	public void shouldGetNotAdmittedPatientList() {
		// given
		final List<PatientEntity> mockedData = Arrays.asList(patientEntity, patientEntity);
		when(patientMapperMock.queryNotAdmittedPatients(any(PatientsQuery.class))).thenReturn(mockedData);

		// when
		final List<Patient> result = patientDao.getNotAdmitted();

		// then
		assertNotNull(result);
		assertSame(mockedData.size(), result.size());
	}

	@Test
	public void shouldGetAdmittedPatientWithAdmissions() {
		// given
		final List<PatientEntity> mockedData = Arrays.asList(patientEntity, patientEntity);
		when(patientMapperMock.queryAdmittedPatientWithAdmissions(any(PatientsQuery.class))).thenReturn(mockedData);

		// when
		final List<Patient> result = patientDao.getAdmittedPatientsWithAdmissions();

		// then
		assertNotNull(result);
		assertSame(mockedData.size(), result.size());
	}

	private PatientDiff generateSamplePatientDiff() {
		final PatientDiff patientDif = PatientDiffBuilder.get().name("name").surname("surname").pesel("89031004513")
				.birthday(new Date()).sex(Patient.Sex.WOMAN).build();
		return patientDif;
	}

	private PatientEntity patientEntity;

	private PatientsDao patientDao;

	private PatientsMapper patientMapperMock;

	private static final List<AdmissionEntity> ADMISSION_LIST = new ArrayList<AdmissionEntity>();

	private static final String SAMPLE_FIRSTNAME = "firstname";

	private static final String SAMPLE_SURNAME = "surname";

	private static final String SAMPLE_PESEL = "12345678909";

	private static final Date SAMPLE_BIRTHDAY = new Date();

	private static final Patient.Sex SAMPLE_SEX = MAN;
}
