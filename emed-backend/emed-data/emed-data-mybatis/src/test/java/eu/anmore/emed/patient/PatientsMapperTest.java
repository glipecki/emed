package eu.anmore.emed.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import eu.anmore.emed.admission.Admission;
import eu.anmore.emed.admission.AdmissionsTestUtils;
import eu.anmore.emed.tests.BaseMybatisTest;

/**
 * @author Grzegorz Lipecki
 */
public class PatientsMapperTest extends BaseMybatisTest {

	@Before
	public void setup() {
		patientsMapper = getSqlSession().getMapper(PatientsMapper.class);
		knownPatient = PatientTestUtils.getKnownPatient();
	}

	@Test
	public void shouldGetEntityWithAllData() {
		// given
		final int knownEntityId = 51;

		// when
		final PatientEntity result = patientsMapper.queryPatients(
				PatientsQuery.getBuilder().idEquals(knownEntityId).build()).get(0);

		// then
		assertEquals(knownPatient.getId(), result.getId());
		assertEquals(knownPatient.getFirstName(), result.getFirstName());
		assertEquals(knownPatient.getSurname(), result.getSurname());
		assertEquals(knownPatient.getPesel(), result.getPesel());
		assertEquals(knownPatient.getBirthday(), result.getBirthday());
		assertEquals(knownPatient.getSex(), result.getSex());
	}

	@Test
	public void shouldQueryAllPatients() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder().build());

		// then
		assertNotNull(result);
		assertEquals(6, result.size());
	}

	@Test
	public void shouldGetEntitiesOrderByFirstname() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME).build());

		// then
		assertTrue("Invalid records order.", result.get(0).getFirstName().compareTo(result.get(1).getFirstName()) <= 0);
	}

	@Test
	public void shouldGetEntitiesOrderBySurname() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.orderBy(PatientEntity.SURNAME_COLUMN_NAME).build());

		// then
		assertTrue("Invalid records order.", result.get(0).getSurname().compareTo(result.get(1).getSurname()) < 0);
	}

	@Test
	public void shouldGetPatientsOrderbyFirstnameAndSurname() {
		// given
		final int expectedFirstRecordId = 54;

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME).orderBy(PatientEntity.SURNAME_COLUMN_NAME).build());

		// then
		assertSame("Invalid records order.", expectedFirstRecordId, result.get(0).getId());
	}

	@Test
	public void shouldQueryByFirstName() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.firstNameEquals(knownPatient.getFirstName()).build());

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(knownPatient.getId(), result.get(0).getId());
		assertEquals(knownPatient.getFirstName(), result.get(0).getFirstName());
		assertEquals(knownPatient.getSurname(), result.get(0).getSurname());
		assertEquals(knownPatient.getPesel(), result.get(0).getPesel());
		assertEquals(knownPatient.getBirthday(), result.get(0).getBirthday());
		assertEquals(knownPatient.getSex(), result.get(0).getSex());
	}

	@Test
	public void shouldQueryBySurname() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.surnameEquals(knownPatient.getSurname()).build());

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(knownPatient.getId(), result.get(0).getId());
		assertEquals(knownPatient.getFirstName(), result.get(0).getFirstName());
		assertEquals(knownPatient.getSurname(), result.get(0).getSurname());
		assertEquals(knownPatient.getPesel(), result.get(0).getPesel());
		assertEquals(knownPatient.getBirthday(), result.get(0).getBirthday());
		assertEquals(knownPatient.getSex(), result.get(0).getSex());
	}

	@Test
	public void shouldQueryByPesel() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.peselEquals(knownPatient.getPesel()).build());

		// then
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(knownPatient.getId(), result.get(0).getId());
		assertEquals(knownPatient.getFirstName(), result.get(0).getFirstName());
		assertEquals(knownPatient.getSurname(), result.get(0).getSurname());
		assertEquals(knownPatient.getPesel(), result.get(0).getPesel());
		assertEquals(knownPatient.getBirthday(), result.get(0).getBirthday());
		assertEquals(knownPatient.getSex(), result.get(0).getSex());
	}

	@Test
	public void shouldQueryById() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.idEquals(knownPatient.getId()).build());

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(knownPatient.getId(), result.get(0).getId());
		assertEquals(knownPatient.getFirstName(), result.get(0).getFirstName());
		assertEquals(knownPatient.getSurname(), result.get(0).getSurname());
		assertEquals(knownPatient.getPesel(), result.get(0).getPesel());
		assertEquals(knownPatient.getBirthday(), result.get(0).getBirthday());
		assertEquals(knownPatient.getSex(), result.get(0).getSex());
	}

	@Test
	public void shouldQueryByBirthday() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.birthdayEquals(knownPatient.getBirthday()).build());

		// then
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(knownPatient.getId(), result.get(0).getId());
		assertEquals(knownPatient.getFirstName(), result.get(0).getFirstName());
		assertEquals(knownPatient.getSurname(), result.get(0).getSurname());
		assertEquals(knownPatient.getPesel(), result.get(0).getPesel());
		assertEquals(knownPatient.getBirthday(), result.get(0).getBirthday());
		assertEquals(knownPatient.getSex(), result.get(0).getSex());
	}

	@Test
	public void shouldQueryBySex() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryPatients(PatientsQuery.getBuilder()
				.sexEquals(Patient.Sex.WOMAN).build());

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void shouldEditPatientName() {
		// given
		final int testPatientId = 51;
		final String newPatientName = "Arnold";

		final Patient originalPatient = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();

		// when
		patientsMapper.edit(EditPatientCommand.get(testPatientId,
				PatientDiffBuilder.get(originalPatient).name(newPatientName).build()));

		// then
		final Patient patientAfterChanges = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();
		assertEquals(newPatientName, patientAfterChanges.getFirstName());
		assertFalse(patientAfterChanges.getFirstName().equals(originalPatient.getFirstName()));
	}

	@Test
	public void shouldEditPatientSurname() {
		// given
		final int testPatientId = 51;
		final String newPatientSurname = "Bazinga";

		final Patient originalPatient = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();

		// when
		patientsMapper.edit(EditPatientCommand.get(testPatientId,
				PatientDiffBuilder.get(originalPatient).surname(newPatientSurname).build()));

		// then
		final Patient patientAfterChanges = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();
		assertEquals(newPatientSurname, patientAfterChanges.getSurname());
		assertFalse(patientAfterChanges.getSurname().equals(originalPatient.getSurname()));
	}

	@Test
	public void shouldEditPatientPesel() {
		// given
		final int testPatientId = 51;
		final String newPatientPesel = "89041004513";

		final Patient originalPatient = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();

		// when
		patientsMapper.edit(EditPatientCommand.get(testPatientId,
				PatientDiffBuilder.get(originalPatient).pesel(newPatientPesel).build()));

		// then
		final Patient patientAfterChanges = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();
		assertEquals(newPatientPesel, patientAfterChanges.getPesel());
		assertFalse(patientAfterChanges.getPesel().equals(originalPatient.getPesel()));
	}

	@Test
	public void shouldEditPatientBirthday() {
		// given
		final int testPatientId = 51;
		final Date newPatientBirthday = new LocalDate(2000, 3, 24).toDate();

		final Patient originalPatient = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();

		// when
		patientsMapper.edit(EditPatientCommand.get(testPatientId,
				PatientDiffBuilder.get(originalPatient).birthday(newPatientBirthday).build()));

		// then
		final Patient patientAfterChanges = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();
		assertEquals(newPatientBirthday, patientAfterChanges.getBirthday());
		assertFalse(patientAfterChanges.getBirthday().equals(originalPatient.getBirthday()));
	}

	@Test
	public void shouldEditPatientSex() {
		// given
		final int testPatientId = 51;
		final Patient.Sex newPatientSex = Patient.Sex.WOMAN;

		final Patient originalPatient = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();

		// when
		patientsMapper.edit(EditPatientCommand.get(testPatientId,
				PatientDiffBuilder.get(originalPatient).sex(newPatientSex).build()));

		// then
		final Patient patientAfterChanges = patientsMapper
				.queryPatients(PatientsQuery.getBuilder().idEquals(testPatientId).build()).get(0).getPatient();
		assertEquals(newPatientSex, patientAfterChanges.getSex());
		assertFalse(patientAfterChanges.getSex().equals(originalPatient.getSex()));
	}

	@Test
	public void shouldInsertNewPatient() {
		// given
		final PatientEntity patientEntity = new PatientEntity("firstName", "surname", "89031004513", new Date(),
				Patient.Sex.MAN, null);

		// when
		patientsMapper.insert(patientEntity);

		// then
		assertNotNull(patientEntity.getId() > 0);
	}

	@Test
	public void shouldGetEntityWithAdmissions() {
		// given
		final Admission admission = AdmissionsTestUtils.getKnownAdmission(knownPatient);

		// when
		final PatientEntity result = patientsMapper.queryPatientsWithAdmissions(
				PatientsQuery.getBuilder().idEquals(admission.getPatientId()).build()).get(0);

		// then
		assertNotNull(result.getAdmissions());
		assertEquals(1, result.getAdmissions().size());

		final Admission resultAdmission = result.getAdmissions().get(0).getAdmission();
		assertEquals(admission.getAdmissionDate(), resultAdmission.getAdmissionDate());
		assertEquals(admission.getBlood(), resultAdmission.getBlood());
		assertEquals(admission.getBodyArea(), resultAdmission.getBodyArea());
		assertEquals(admission.getDischargeDate(), resultAdmission.getDischargeDate());
		assertEquals(admission.getDischargePlace(), resultAdmission.getDischargePlace());
		assertEquals(admission.getHeight(), resultAdmission.getHeight());
		assertEquals(admission.getHearthDefect(), resultAdmission.getHearthDefect());
		assertEquals(admission.getId(), resultAdmission.getId());
		assertEquals(admission.getPatientId(), resultAdmission.getPatientId());
		assertEquals(admission.getWeight(), resultAdmission.getWeight());
		assertEquals(admission.getAdmissionReason(), resultAdmission.getAdmissionReason());
	}

	@Test
	public void shouldPatientWithAdmissionContainsPatientId() {
		// given
		final Admission admission = AdmissionsTestUtils.getKnownAdmission(knownPatient);

		// when
		final PatientEntity result = patientsMapper.queryPatientsWithAdmissions(
				PatientsQuery.getBuilder().idEquals(admission.getPatientId()).build()).get(0);

		// then
		assertEquals(knownPatient.getId(), result.getId());
		assertEquals(knownPatient.getFirstName(), result.getFirstName());
		assertEquals(knownPatient.getSurname(), result.getSurname());
		assertEquals(knownPatient.getPesel(), result.getPesel());
		assertEquals(knownPatient.getBirthday(), result.getBirthday());
		assertEquals(knownPatient.getSex(), result.getSex());
	}

	@Test
	public void shouldReturnAdmittedPatients() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryAdmittedPatients(PatientsQuery.getBuilder().build());

		// then
		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	public void shouldReturnNotAdmittedPatients() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryNotAdmittedPatients(PatientsQuery.getBuilder().build());

		// then
		assertNotNull(result);
		assertEquals(3, result.size());
	}

	@Test
	public void shouldReturnAdmittedPatientsWithAdmissions() {
		// given

		// when
		final List<PatientEntity> result = patientsMapper.queryAdmittedPatientWithAdmissions(PatientsQuery.getBuilder()
				.build());

		// then
		assertNotNull(result);
		assertEquals(3, result.size());
		for (final PatientEntity patientEntity : result) {
			assertNotNull(patientEntity.getAdmissions());
			assertFalse(patientEntity.getAdmissions().isEmpty());
		}
	}

	@Override
	protected List<String> getBootrapScript() {
		return Arrays.asList("classpath:eu/anmore/emed/patient/patientsMapperTestData.sql",
				"classpath:eu/anmore/emed/admission/admissionsMapperTestData.sql");
	}

	@Override
	protected Resource[] getMappers() {
		return new Resource[] { new ClassPathResource("eu/anmore/emed/patient/PatientsMapper.xml") };
	}

	private Patient knownPatient;

	private PatientsMapper patientsMapper;

}
