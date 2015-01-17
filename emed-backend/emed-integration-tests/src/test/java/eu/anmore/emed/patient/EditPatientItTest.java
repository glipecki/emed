package eu.anmore.emed.patient;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;
import eu.anmore.emed.patient.Patient.Sex;

/**
 * Integration tests for edit patient.
 * 
 * @author mmiedzinski
 */
public class EditPatientItTest extends BaseItTest {

	@Before
	public void setUp() {
		patientRepository = dataPatientInjector.getPatientRepository();
	}

	@Test
	public void shouldEditPatient() throws Exception {
		// given
		final Patient patient = insertPatient();

		final PatientDiff patientDiff = PatientDiffBuilder.get(patient).name(NEW_NAME).surname(NEW_SURNAME)
				.pesel(NEW_PESEL).sex(NEW_SEX).build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(patientDiff, patient.getId());

		// when
		getMockMvc().perform(requestBuilder);

		// then

		final Patient editedPatient = patientRepository.getById(patient.getId());
		assertThat(editedPatient.getFirstName()).isEqualTo(NEW_NAME);
		assertThat(editedPatient.getSurname()).isEqualTo(NEW_SURNAME);
		assertThat(editedPatient.getSex()).isEqualTo(NEW_SEX);
		assertThat(editedPatient.getPesel()).isEqualTo(NEW_PESEL);
	}

	@Test
	public void shouldReturnEditedPatient() throws Exception {
		// given
		final Patient patient = insertPatient();

		final PatientDiff patientDiff = PatientDiffBuilder.get(patient).name(NEW_NAME).surname(NEW_SURNAME)
				.pesel(NEW_PESEL).sex(NEW_SEX).build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(patientDiff, patient.getId());

		// when
		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// then
		final PatientDto patientDto = getPatientDtoFromRsponse(response);
		assertThat(patientDto.getFirstName()).isEqualTo(NEW_NAME);
		assertThat(patientDto.getSurname()).isEqualTo(NEW_SURNAME);
		assertThat(patientDto.getSex()).isEqualTo(NEW_SEX);
		assertThat(patientDto.getPesel()).isEqualTo(NEW_PESEL);
	}

	private static final String NAME = "name";

	private static final String SURNAME = "surname";

	private static final String PESEL = "89031004513";

	private static final Sex SEX = Sex.MAN;

	private static final String NEW_NAME = "new_name";

	private static final String NEW_SURNAME = "new_surname";

	private static final String NEW_PESEL = "89041004513";

	private static final Sex NEW_SEX = Sex.WOMAN;

	private Patient insertPatient() throws Exception {
		final PatientDiff patientDiff = PatientDiffBuilder.get().name(NAME).surname(SURNAME).pesel(PESEL).sex(SEX)
				.build();

		final MockHttpServletRequestBuilder requestBuilder = post(PatientsRestDescriptor.getInsertPatientRestUrl())
				.contentType(APPLICATION_JSON_UTF8).body(convertObjectToJsonBytes(patientDiff));

		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		return getPatientDtoFromRsponse(response).asPatient();
	}

	private PatientDto getPatientDtoFromRsponse(final byte[] response) throws IOException, JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.reader(PatientDto.class).readValue(response);
	}

	private MockHttpServletRequestBuilder getRequestBuilder(final PatientDiff patientDiff, final int id)
			throws IOException {
		return post(PatientsRestDescriptor.getEditPatientRestUrl(id)).contentType(APPLICATION_JSON_UTF8).body(
				convertObjectToJsonBytes(patientDiff));
	}

	private PatientsDao patientRepository;

	@Autowired
	private DataPatientInjector dataPatientInjector;
}