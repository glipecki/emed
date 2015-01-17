package eu.anmore.emed.admission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import eu.anmore.emed.patient.PatientTestUtils;
import eu.anmore.emed.tests.BaseMybatisTest;

/**
 * Test class for {@link AdmissionsMapper}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsMapperTest extends BaseMybatisTest {

	@Before
	public void setup() {
		admissionsMapper = getSqlSession().getMapper(AdmissionsMapper.class);
	}

	@Test
	public void shouldInsertAdmissionToDB() {
		// given
		final int patientId = 51;
		final AdmissionEntity admissionEntity = new AdmissionEntity(0, patientId, new LocalDate(2012, 2, 3).toDate(),
				"0", "sampleDefect", 22.0, 33.0, 22.0, true, "place", new LocalDate(2012, 2, 4).toDate(), "reason1");

		// when
		final int result = admissionsMapper.admitPatient(admissionEntity);

		// then
		assertSame(1, result);
	}

	@Test
	public void shouldInsertAllAdmissionDataToDB() {
		// given
		final int patientId = 51;
		final AdmissionEntity admissionEntity = new AdmissionEntity(0, patientId, new LocalDate(2012, 2, 3).toDate(),
				"0", "sampleDefect", 22.0, 33.0, 22.0, true, "place", new LocalDate(2012, 2, 4).toDate(), "reason2");

		// when
		admissionsMapper.admitPatient(admissionEntity);

		// then
		final AdmissionEntity newAdmission = admissionsMapper.getAdmissionById(admissionEntity.getId());

		assertEquals(admissionEntity.getAdmissionDate(), newAdmission.getAdmissionDate());
		assertEquals(admissionEntity.getBloodType(), newAdmission.getBloodType());
		assertEquals(admissionEntity.getBodyArea(), newAdmission.getBodyArea());
		assertEquals(admissionEntity.getDischargeDate(), newAdmission.getDischargeDate());
		assertEquals(admissionEntity.getDischargePlace(), newAdmission.getDischargePlace());
		assertEquals(admissionEntity.getHeight(), newAdmission.getHeight());
		assertEquals(admissionEntity.getHearthDefect(), newAdmission.getHearthDefect());
		assertEquals(admissionEntity.getId(), newAdmission.getId());
		assertEquals(admissionEntity.getPatientId(), newAdmission.getPatientId());
		assertEquals(admissionEntity.getWeight(), newAdmission.getWeight());
		assertEquals(admissionEntity.getAdmissionReason(), newAdmission.getAdmissionReason());
	}

	@Test
	public void shouldUpdatePatient() {
		// given
		final Date newDate = new LocalDate(2012, 2, 4).toDate();
		final String newString = "newString";
		final Integer newInteger = 32;
		final Double newDoble = 3.3;

		final Admission admission = AdmissionsTestUtils.getKnownAdmission(PatientTestUtils.getKnownPatient());
		final AdmissionDiff admissionDiff = AdmissionDiffBuilder.get(admission).admissionDate(newDate).blood(newString)
				.bodyArea(newDoble).death(true).dischargeDate(newDate).dischargePlace(newString).height(newDoble)
				.hearthDefect(newString).weight(newDoble).admissionReason(newString).build();

		final EditAdmissionCommand editAdmissionCommand = new EditAdmissionCommand(admission.getId(), admissionDiff);

		// when
		admissionsMapper.edit(editAdmissionCommand);

		// then
		final AdmissionEntity newAdmission = admissionsMapper.getAdmissionById(admission.getId());

		assertEquals(admission.getPatientId(), newAdmission.getPatientId());
		assertEquals(admission.getId(), newAdmission.getId());
		assertEquals(newDate, newAdmission.getAdmissionDate());
		assertEquals(newString, newAdmission.getBloodType());
		assertEquals(newDoble, newAdmission.getBodyArea());
		assertEquals(newDate, newAdmission.getDischargeDate());
		assertEquals(newString, newAdmission.getDischargePlace());
		assertEquals(newDoble, newAdmission.getHeight());
		assertEquals(newString, newAdmission.getHearthDefect());
		assertEquals(newDoble, newAdmission.getWeight());
		assertEquals(newString, newAdmission.getAdmissionReason());
	}

	@Test
	public void shouldGetAdmissionById() {
		// given
		final Admission admission = AdmissionsTestUtils.getKnownAdmission(PatientTestUtils.getKnownPatient());

		// when
		final AdmissionEntity resut = admissionsMapper.getAdmissionById(admission.getId());

		// then
		assertEquals(admission.getAdmissionDate(), resut.getAdmissionDate());
		assertEquals(admission.getBlood(), resut.getBloodType());
		assertEquals(admission.getBodyArea(), resut.getBodyArea());
		assertEquals(admission.getDischargeDate(), resut.getDischargeDate());
		assertEquals(admission.getDischargePlace(), resut.getDischargePlace());
		assertEquals(admission.getHeight(), resut.getHeight());
		assertEquals(admission.getHearthDefect(), resut.getHearthDefect());
		assertEquals(admission.getId(), resut.getId());
		assertEquals(admission.getPatientId(), resut.getPatientId());
		assertEquals(admission.getWeight(), resut.getWeight());
		assertEquals(admission.getAdmissionReason(), resut.getAdmissionReason());
	}

	@Override
	protected List<String> getBootrapScript() {
		return Arrays.asList("classpath:eu/anmore/emed/patient/patientsMapperTestData.sql",
				"classpath:eu/anmore/emed/admission/admissionsMapperTestData.sql");
	}

	@Override
	protected Resource[] getMappers() {
		return new Resource[] { new ClassPathResource("eu/anmore/emed/admission/AdmissionsMapper.xml") };
	}

	private AdmissionsMapper admissionsMapper;
}
