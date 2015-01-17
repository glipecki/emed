package eu.anmore.emed.observation;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author glipecki
 */
@Transactional
public class ObservationsBeanImpl implements ObservationsBean {

	public ObservationsBeanImpl(final ObservationsDao observationsRepository) {
		this();
		this.observationsRepository = observationsRepository;
	}

	public ObservationsBeanImpl() {
		// just to trick CGLIB.
	}

	@Override
	public List<Observation> getObservations(final Integer patientId) {
		return observationsRepository.getObservations(patientId);
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey) {
		return observationsRepository.getObservations(patientId, groupKey);
	}

	@Override
	public void addObservations(final Integer patientId, final List<Observation> observations,
			final String observerUsername) {
		final Integer observationBatchId = observationsRepository.getNextObservationBatchId();
		for (final Observation observation : observations) {
			observationsRepository.addObservation(patientId, observation, observerUsername, observationBatchId);
		}
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey, final Date date) {
		return observationsRepository.getObservations(patientId, groupKey, date);
	}

	@Override
	public void addObservationNorm(final ObservationNorm observationNorm) {
		observationsRepository.addObservationNorm(observationNorm);
	}

	@Override
	public void editObservationNorm(final ObservationNorm observationNorm) {
		observationsRepository.editObservationNorm(observationNorm);
	}

	@Override
	public List<ObservationNorm> getObservationNorms() {
		return observationsRepository.getObservationNorms();
	}

	@Override
	public List<CustomObservationType> getCustomObservationTypes() {
		return observationsRepository.getCustomObservationTypes();
	}

	@Override
	public void addCustomObservationType(final CustomObservationType customObservationType) {
		observationsRepository.addCustomObservationType(customObservationType);
	}

	@Override
	public List<AdmissionObservationType> getAdmissionObservation(final int patientId) {
		return observationsRepository.getAdmissionObservation(patientId);
	}

	@Override
	public void setAdmissionObservations(final int admissionId, final List<AdmissionObservationType> observations) {
		observationsRepository.setAdmissionObservations(admissionId, observations);
	}

	private ObservationsDao observationsRepository;

}
