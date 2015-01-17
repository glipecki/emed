package eu.anmore.emed.admission;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;
import eu.anmore.emed.patient.DataPatientInjector;
import eu.anmore.emed.patient.Patient;
import eu.anmore.emed.patient.PatientsDao;

/**
 * Integration tests for edit admissions.
 * 
 * @author mmiedzinski
 */
public class EditAdmissionItTest extends BaseItTest {

	@Before
	public void setUp() {
		patientRepository = dataPatientInjector.getPatientRepository();
	}

	@Test
	public void shouldEditAdmission() throws Exception {
		// given
		final int patientId = 2;
		final Admission admission = insertAdmission(patientId);

		final AdmissionDiff admissionDiff = AdmissionDiffBuilder.get(admission).admissionReason(NEW_ADMISSION_REASON)
				.blood(NEW_BLOOD).bodyArea(NEW_BODY_AREA).height(NEW_HEIGHT).weight(NEW_WEIGHT).patientId(patientId)
				.death(true).hearthDefect(NEW_HEARTH_DEFECT).build();

		final MockHttpServletRequestBuilder requestBuilder = post(
				AdmissionsRestDescriptor.getEditAdmissionRestUrl(admission.getId())).contentType(APPLICATION_JSON_UTF8)
				.body(convertObjectToJsonBytes(admissionDiff));

		// when
		getMockMvc().perform(requestBuilder);

		// then
		final Patient patient = patientRepository.getPatientWithAdmissionList(patientId);
		final Admission editedAdmission = patient.getAdmissionList().get(0);
		assertThat(editedAdmission.getBlood()).isEqualTo(NEW_BLOOD);
		assertThat(editedAdmission.getBodyArea()).isEqualTo(NEW_BODY_AREA);
		assertThat(editedAdmission.getHeight()).isEqualTo(NEW_HEIGHT);
		assertThat(editedAdmission.getHearthDefect()).isEqualTo(NEW_HEARTH_DEFECT);
	}

	private static final String ADMISSION_REASON = "reason";

	private static final String HEARTH_DEFECT = "defect";

	private static final String BLOOD = "blood";

	private static final Double BODY_AREA = 2.6;

	private static final Double HEIGHT = 2.8;

	private static final Double WEIGHT = 2.9;

	private static final String NEW_ADMISSION_REASON = "new_reason";

	private static final String NEW_HEARTH_DEFECT = "new_defect";

	private static final String NEW_BLOOD = "new_blood";

	private static final Double NEW_BODY_AREA = 4.6;

	private static final Double NEW_HEIGHT = 4.8;

	private static final Double NEW_WEIGHT = 4.9;

	private Admission insertAdmission(final int patientId) throws IOException, Exception {
		final AdmissionDiff admissionDiff = AdmissionDiffBuilder.get().admissionReason(ADMISSION_REASON).blood(BLOOD)
				.bodyArea(BODY_AREA).height(HEIGHT).weight(WEIGHT).patientId(patientId).death(false)
				.hearthDefect(HEARTH_DEFECT).build();

		final MockHttpServletRequestBuilder requestBuilder = post(AdmissionsRestDescriptor.getAdmitPatientRestUrl())
				.contentType(APPLICATION_JSON_UTF8).body(convertObjectToJsonBytes(admissionDiff));
		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		final ObjectMapper mapper = new ObjectMapper();
		return ((AdmissionDto) mapper.reader(AdmissionDto.class).readValue(response)).asAdmission();
	}

	private PatientsDao patientRepository;

	@Autowired
	private DataPatientInjector dataPatientInjector;
}
