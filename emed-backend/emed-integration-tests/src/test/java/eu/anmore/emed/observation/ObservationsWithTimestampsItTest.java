package eu.anmore.emed.observation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
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
public class ObservationsWithTimestampsItTest extends BaseItTest {

	@Before
	public void setUp() {
		admissionsBusinessInjector.getAdmissionBean().admitPatient(
				AdmissionDiffBuilder.get().patientId(PATIENT_ID).build());
	}

	@Test
	public void shouldAddTimestampToObservation() throws IOException, Exception {
		// given
		final Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 6, 10);
		final Observation observationToAdd = new Observation(PredefinedObservationType.HR.getKey(), calendar.getTime(),
				"150");

		// when
		getMockMvc().perform(
				post(ObservationsRestDescriptor.getAddObservationPath()).contentType(APPLICATION_JSON_UTF8).body(
						convertObjectToJsonBytes(PatientObservationsDto.of(PATIENT_ID, Arrays.asList(observationToAdd),
								OBSERVER))));

		// then
		final List<Observation> patientObservations = observationsBusinessInjector.getObservationsBean()
				.getObservations(PATIENT_ID);
		final Observation patientObservation = patientObservations.get(0);
		assertThat(patientObservation.getTimestamp()).isNotNull();
		assertThat(patientObservation.getTimestamp()).isNotEqualTo(patientObservation.getDate());
	}

	private static final Integer PATIENT_ID = 1;

	@Autowired
	private ObservationsBusinessInjector observationsBusinessInjector;

	@Autowired
	private AdmissionsBusinessInjector admissionsBusinessInjector;

}
