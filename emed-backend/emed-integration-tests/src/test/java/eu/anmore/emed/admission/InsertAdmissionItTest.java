package eu.anmore.emed.admission;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;
import eu.anmore.emed.patient.DataPatientInjector;
import eu.anmore.emed.patient.Patient;
import eu.anmore.emed.patient.PatientsDao;

/**
 * Integration tests for insert new admissions.
 * 
 * @author mmiedzinski
 */
public class InsertAdmissionItTest extends BaseItTest {

	@Before
	public void setUp() {
		patientRepository = dataPatientInjector.getPatientRepository();
	}

	@Test
	public void shouldInsertAdmission() throws Exception {
		// given
		final int patientId = 1;
		final AdmissionDiff admissionDiff = AdmissionDiffBuilder.get().admissionReason(ADMISSION_REASON).blood(BLOOD)
				.bodyArea(BODY_AREA).height(HEIGHT).weight(WEIGHT).admissionDate(ADMISSION_DATE).patientId(patientId)
				.death(false).hearthDefect(HEARTH_DEFECT).build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(admissionDiff);

		// when
		getMockMvc().perform(requestBuilder);

		// then
		final Patient patient = patientRepository.getPatientWithAdmissionList(patientId);
		final Admission admission = patient.getAdmissionList().get(0);
		assertThat(admission.getAdmissionReason()).isEqualTo(ADMISSION_REASON);
		assertThat(admission.getBlood()).isEqualTo(BLOOD);
		assertThat(admission.getBodyArea()).isEqualTo(BODY_AREA);
		assertThat(admission.getHeight()).isEqualTo(HEIGHT);
		assertThat(admission.getHearthDefect()).isEqualTo(HEARTH_DEFECT);
	}

	private static final String ADMISSION_REASON = "reason";

	private static final String HEARTH_DEFECT = "defect";

	private static final String BLOOD = "blood";

	private static final Double BODY_AREA = 2.6;

	private static final Double HEIGHT = 2.8;

	private static final Double WEIGHT = 2.9;

	private static final Date ADMISSION_DATE = new Date();

	private MockHttpServletRequestBuilder getRequestBuilder(final AdmissionDiff admissionDiff) throws IOException {
		return post(AdmissionsRestDescriptor.getAdmitPatientRestUrl()).contentType(APPLICATION_JSON_UTF8).body(
				convertObjectToJsonBytes(admissionDiff));
	}

	private PatientsDao patientRepository;

	@Autowired
	private DataPatientInjector dataPatientInjector;
}
