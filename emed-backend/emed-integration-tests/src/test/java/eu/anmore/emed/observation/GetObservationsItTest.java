package eu.anmore.emed.observation;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
public class GetObservationsItTest extends BaseItTest {

	@Test
	public void shouldGetObservataions() {
		List<Observation> observations;

		admissionsBusinessInjector.getAdmissionBean().admitPatient(
				AdmissionDiffBuilder.get().patientId(PATIENT_ID).build());
		observationsBusinessInjector.getObservationsBean().addObservations(PATIENT_ID,
				observations(hr("140", 1), hr("150", 2), hr("160", 3)), OBSERVER);
		observations = observationsBusinessInjector.getObservationsBean().getObservations(PATIENT_ID);
		assertThat(observations).hasSize(3);

		admissionsBusinessInjector.getAdmissionBean().admitPatient(
				AdmissionDiffBuilder.get().patientId(PATIENT_ID).build());
		observationsBusinessInjector.getObservationsBean().addObservations(PATIENT_ID, observations(hr("200", 1)),
				OBSERVER);
		observations = observationsBusinessInjector.getObservationsBean().getObservations(PATIENT_ID);
		assertThat(observations).hasSize(1);
	}

	private static final int PATIENT_ID = 1;

	private List<Observation> observations(final Observation... observations) {
		return Arrays.asList(observations);
	}

	private Observation hr(final String value, final int date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(2001, 10, date, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Observation(PredefinedObservationType.HR.getKey(), calendar.getTime(), value);
	}

	@Autowired
	private ObservationsBusinessInjector observationsBusinessInjector;

	@Autowired
	private AdmissionsBusinessInjector admissionsBusinessInjector;

}
