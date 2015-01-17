package eu.anmore.emed.admission;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.anmore.emed.patient.PatientTestUtils;

/**
 * Test class for {@link AdmissionsDaoImpl}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsDaoImplTest {

	@Before
	public void setUp() {
		admissionsMapperMock = mock(AdmissionsMapper.class);
		admissionDao = new AdmissionsDaoImpl(admissionsMapperMock);
	}

	@Test
	public void shouldInsertAdmission() {
		// given
		AdmissionDiff admissionToInsert = AdmissionsTestUtils.getSampleAdmissioinDiff(PatientTestUtils
				.getKnownPatient());
		AdmissionEntity expectedEntity = AdmissionEntity.valueOf(admissionToInsert);

		// when
		admissionDao.admitPatient(admissionToInsert);

		// then
		verify(admissionsMapperMock).admitPatient(eq(expectedEntity));
	}

	@Test
	public void shouldReturnAdmissionAfterInsert() {
		// given
		AdmissionDiff admissionToInsert = AdmissionsTestUtils.getSampleAdmissioinDiff(PatientTestUtils
				.getKnownPatient());

		// when
		Admission result = admissionDao.admitPatient(admissionToInsert);

		// then
		assertEquals(AdmissionEntity.valueOf(admissionToInsert).getAdmission(), result);
	}

	@Test
	public void shouldCallMapperWhileEditAdmission() {
		// given
		int admissionId = 1;
		AdmissionDiff admissionToUpdate = AdmissionsTestUtils.getSampleAdmissioinDiff(PatientTestUtils
				.getKnownPatient());

		AdmissionEntity admissionMock = mock(AdmissionEntity.class);
		when(admissionsMapperMock.getAdmissionById(admissionId)).thenReturn(admissionMock);

		// when
		admissionDao.edit(admissionId, admissionToUpdate);

		// then
		verify(admissionsMapperMock).edit((EditAdmissionCommand) anyObject());
	}

	@Test
	public void shouldReturnUpdatedAdmission() {
		// given
		int admissionId = 1;
		AdmissionDiff admissionToUpdate = AdmissionsTestUtils.getSampleAdmissioinDiff(PatientTestUtils
				.getKnownPatient());

		Admission admissionMock = mock(Admission.class);
		AdmissionEntity admissionEntityMock = mock(AdmissionEntity.class);
		when(admissionEntityMock.getAdmission()).thenReturn(admissionMock);
		when(admissionsMapperMock.getAdmissionById(admissionId)).thenReturn(admissionEntityMock);

		// when
		Admission result = admissionDao.edit(admissionId, admissionToUpdate);

		// then
		assertEquals(admissionMock, result);
	}

	private AdmissionsMapper admissionsMapperMock;

	private AdmissionsDao admissionDao;
}
