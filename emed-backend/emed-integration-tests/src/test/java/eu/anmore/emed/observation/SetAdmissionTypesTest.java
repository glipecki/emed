package eu.anmore.emed.observation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.anmore.emed.BaseItTest;
import eu.anmore.emed.admission.AdmissionDiffBuilder;
import eu.anmore.emed.admission.AdmissionsBusinessInjector;

/**
 * Dla dodawanych obserwacji wpis powinien zawierac zarówno datę dodania
 * ustawioną przez aplikację kliencką, jak i datę faktycznego wpisania danych do
 * systemu
 * 
 * @author glipecki
 * 
 */
public class SetAdmissionTypesTest extends BaseItTest {

	public static final Integer PATIENT_ID = 1;

	@Before
	public void setUp() {
		admissionId = admissionsBusinessInjector.getAdmissionBean()
				.admitPatient(AdmissionDiffBuilder.get().patientId(PATIENT_ID).build()).getId();
	}

	@Test
	public void shouldSetAdmissionObservations() throws IOException, Exception {
		// given
		final AdmissionObservationType hrAT = new AdmissionObservationType(admissionId, "HR", true);
		final AdmissionObservationType cvpAT = new AdmissionObservationType(admissionId, "CVP", true);
		final List<AdmissionObservationType> list = Arrays.asList(hrAT, cvpAT);
		final AdmissionObservationTypeDto dto = new AdmissionObservationTypeDto(list);

		// when
		getMockMvc().perform(
				post(ObservationsRestDescriptor.ROOT_URI + "/setAdmissionObservation/" + admissionId).contentType(
						APPLICATION_JSON_UTF8).body(convertObjectToJsonBytes(dto)));

		// then
		final List<AdmissionObservationType> result = observationsBusinessInjector.getObservationsBean()
				.getAdmissionObservation(PATIENT_ID);
		assertThat(result).contains(hrAT).contains(cvpAT);
	}

	private int admissionId;

	@Autowired
	private ObservationsBusinessInjector observationsBusinessInjector;

	@Autowired
	private AdmissionsBusinessInjector admissionsBusinessInjector;

}
