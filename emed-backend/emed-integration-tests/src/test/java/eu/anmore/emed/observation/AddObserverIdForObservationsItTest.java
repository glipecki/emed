package eu.anmore.emed.observation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eu.anmore.emed.BaseItTest;
import eu.anmore.emed.admission.AdmissionDiffBuilder;
import eu.anmore.emed.admission.AdmissionsBusinessInjector;

/**
 * Podczas dodawania obserwacji należy przechowa informację o osobie dodającej
 * wpis.
 * 
 * @author glipecki
 * 
 */
public class AddObserverIdForObservationsItTest extends BaseItTest {

	private static final String USERNAME = "glipecki";

	@Before
	public void setUp() {
		admissionsBusinessInjector.getAdmissionBean().admitPatient(
				AdmissionDiffBuilder.get().patientId(PATIENT_ID).build());
	}

	@Test
	public void shouldSaveUserIdForObservation() throws Exception {
		// given
		final PatientObservationsDto observationDto = getObservationDto();

		// when
		getMockMvc().perform(
				post(ObservationsRestDescriptor.getAddObservationPath()).contentType(APPLICATION_JSON_UTF8).body(
						convertObjectToJsonBytes(observationDto)));

		// then
		final List<Observation> patientObservations = getPatientObservations();
		assertThat(patientObservations).isNotEmpty();
		assertThat(patientObservations.get(0).getObserverUsername()).isEqualTo(USERNAME);
	}

	private List<Observation> getPatientObservations() {
		final List<Observation> patientObservations = observationsBusinessInjector.getObservationsBean()
				.getObservations(PATIENT_ID, ObservationGroup.HEMODYNAMICS.getKey());
		return patientObservations;
	}

	private PatientObservationsDto getObservationDto() {
		final Observation observation = new Observation(PredefinedObservationType.HR.getKey(), new Date(), "100");
		final PatientObservationsDto observationDto = PatientObservationsDto.of(PATIENT_ID, Arrays.asList(observation),
				USERNAME);
		return observationDto;
	}

	private static final int PATIENT_ID = 1;

	@Autowired
	private ObservationsBusinessInjector observationsBusinessInjector;

	@Autowired
	private AdmissionsBusinessInjector admissionsBusinessInjector;

}
