package eu.anmore.emed.admission;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link AdmissionsControllerImpl}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsControllerImplTest {

	@Before
	public void setUp() {
		admissionsBeanMock = mock(AdmissionsBean.class);
		admissionsController = new AdmissionsControllerImpl(admissionsBeanMock);
	}

	@Test
	public void shouldCallBeanWhileAdmitPatient() {
		// given
		AdmissionDiff admissionDiffMock = mock(AdmissionDiff.class);
		Admission admissionMock = mock(Admission.class);
		when(admissionsBeanMock.admitPatient(admissionDiffMock)).thenReturn(admissionMock);

		// when
		admissionsController.callAdmitPatient(admissionDiffMock);

		// then
		verify(admissionsBeanMock).admitPatient(admissionDiffMock);
	}

	@Test
	public void shouldReturnAdmissionAfterAdmitPatient() {
		// given
		AdmissionDiff admissionDiffMock = mock(AdmissionDiff.class);
		Admission admissionMock = mock(Admission.class);
		when(admissionsBeanMock.admitPatient(admissionDiffMock)).thenReturn(admissionMock);

		// when
		AdmissionDto result = admissionsController.callAdmitPatient(admissionDiffMock);

		// then
		assertNotNull(result);
	}

	@Test
	public void shouldReturnAdmissionDtoAfterEdit() {
		// given
		int id = 2;
		AdmissionDiff admissionDiffMock = mock(AdmissionDiff.class);
		Admission admissionMock = mock(Admission.class);
		when(admissionsBeanMock.edit(id, admissionDiffMock)).thenReturn(admissionMock);

		// when
		AdmissionDto result = admissionsController.callEdit(id, admissionDiffMock);

		// then
		assertNotNull(result);
	}

	private AdmissionsBean admissionsBeanMock;

	private AdmissionsController admissionsController;
}
