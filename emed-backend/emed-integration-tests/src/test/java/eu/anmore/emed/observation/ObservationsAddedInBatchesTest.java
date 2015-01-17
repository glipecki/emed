package eu.anmore.emed.observation;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
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
public class ObservationsAddedInBatchesTest extends BaseItTest {

	public static final Integer PATIENT_ID = 1;

	@Before
	public void setUp() {
		admissionsBusinessInjector.getAdmissionBean().admitPatient(
				AdmissionDiffBuilder.get().patientId(PATIENT_ID).build());
	}

	@Test
	public void shouldAddTimestampToObservation() throws IOException, Exception {
		// when
		addObservation(PATIENT_ID, 2000, 6, 10, PredefinedObservationType.HR, "150");
		addObservation(PATIENT_ID, 2000, 6, 11, PredefinedObservationType.HR, "160");

		// then
		final List<Observation> patientObservations = observationsBusinessInjector.getObservationsBean()
				.getObservations(PATIENT_ID);

		final Integer batchIdObservation1 = patientObservations.get(0).getBatchId();
		assertThat(batchIdObservation1).isNotNull();

		final Integer batchIdObservation2 = patientObservations.get(1).getBatchId();
		assertThat(batchIdObservation1).isNotNull();

		assertThat(batchIdObservation1).isNotEqualTo(batchIdObservation2);
	}

	@Autowired
	private ObservationsBusinessInjector observationsBusinessInjector;

	@Autowired
	private AdmissionsBusinessInjector admissionsBusinessInjector;

}
