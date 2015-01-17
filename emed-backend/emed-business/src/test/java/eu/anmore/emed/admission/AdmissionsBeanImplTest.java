package eu.anmore.emed.admission;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link AdmissionsBeanImpl}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsBeanImplTest {

	@Before
	public void setUp() {
		admissionRepositoryMock = mock(AdmissionsDao.class);
		admissionsBean = new AdmissionsBeanImpl(admissionRepositoryMock);
	}

	@Test
	public void shouldCallRepository() {
		// given
		AdmissionDiff admissionDiffMock = mock(AdmissionDiff.class);

		// when
		admissionsBean.admitPatient(admissionDiffMock);

		// then
		verify(admissionRepositoryMock).admitPatient(admissionDiffMock);
	}

	@Test
	public void shouldReturnAdmission() {
		// given
		AdmissionDiff admissionDiffMock = mock(AdmissionDiff.class);
		Admission admissionMock = mock(Admission.class);
		when(admissionRepositoryMock.admitPatient(admissionDiffMock)).thenReturn(admissionMock);

		// when
		Admission result = admissionsBean.admitPatient(admissionDiffMock);

		// then
		assertNotNull(result);
	}

	@Test
	public void shouldReturnAdmissionAfterEdit() {
		// given
		int id = 3;
		AdmissionDiff admissionDiffMock = mock(AdmissionDiff.class);
		Admission admissionMock = mock(Admission.class);
		when(admissionRepositoryMock.edit(id, admissionDiffMock)).thenReturn(admissionMock);

		// when
		Admission result = admissionsBean.edit(id, admissionDiffMock);

		// then
		assertEquals(admissionMock, result);
	}

	private AdmissionsDao admissionRepositoryMock;

	private AdmissionsBean admissionsBean;
}
