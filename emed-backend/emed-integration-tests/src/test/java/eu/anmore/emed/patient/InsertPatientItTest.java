package eu.anmore.emed.patient;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;
import eu.anmore.emed.patient.Patient.Sex;

/**
 * Integration tests for insert patients.
 * 
 * @author mmiedzinski
 */
public class InsertPatientItTest extends BaseItTest {

	@Before
	public void setUp() {
		patientRepository = dataPatientInjector.getPatientRepository();
	}

	@Test
	public void shouldInsertPatient() throws Exception {
		// given
		final PatientDiff patientDiff = PatientDiffBuilder.get().name(NAME).surname(SURNAME).pesel(PESEL)
				.birthday(BIRTHDAY).sex(SEX).build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(patientDiff);

		// when
		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// then
		final PatientDto patientDto = getPatientDtoFromRsponse(response);

		final Patient patient = patientRepository.getById(patientDto.getId());
		assertThat(patient.getFirstName()).isEqualTo(NAME);
		assertThat(patient.getSurname()).isEqualTo(SURNAME);
		assertThat(patient.getSex()).isEqualTo(SEX);
		assertThat(patient.getPesel()).isEqualTo(PESEL);
	}

	@Test
	public void shouldReturnNewPatient() throws Exception {
		// given
		final PatientDiff patientDiff = PatientDiffBuilder.get().name(NAME).surname(SURNAME).pesel(PESEL)
				.birthday(BIRTHDAY).sex(SEX).build();

		final MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(patientDiff);

		// when
		final byte[] response = getMockMvc().perform(requestBuilder).andReturn().getResponse().getContentAsByteArray();

		// then
		final PatientDto patientDto = getPatientDtoFromRsponse(response);
		assertThat(patientDto.getBirthday()).isEqualTo(BIRTHDAY);
		assertThat(patientDto.getFirstName()).isEqualTo(NAME);
		assertThat(patientDto.getSurname()).isEqualTo(SURNAME);
		assertThat(patientDto.getSex()).isEqualTo(SEX);
		assertThat(patientDto.getPesel()).isEqualTo(PESEL);
	}

	private static final String NAME = "name";

	private static final String SURNAME = "surname";

	private static final String PESEL = "89031004513";

	private static final Date BIRTHDAY = new Date();

	private static final Sex SEX = Sex.MAN;

	private PatientDto getPatientDtoFromRsponse(final byte[] response) throws IOException, JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.reader(PatientDto.class).readValue(response);
	}

	private MockHttpServletRequestBuilder getRequestBuilder(final PatientDiff patientDiff) throws IOException {
		return post(PatientsRestDescriptor.getInsertPatientRestUrl()).contentType(APPLICATION_JSON_UTF8).body(
				convertObjectToJsonBytes(patientDiff));
	}

	private PatientsDao patientRepository;

	@Autowired
	private DataPatientInjector dataPatientInjector;
}
