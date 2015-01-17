package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author glipecki
 */
@Transactional
public class ObservationsDaoImpl implements ObservationsDao {

	public ObservationsDaoImpl(final ObservationsMapper observationMapper) {
		this();
		this.observationMapper = observationMapper;
	}

	public ObservationsDaoImpl() {
		// just to trick CGLIB.
	}

	@Override
	public void addObservation(final Integer patientId, final Observation observation, final String observerUsername,
			final Integer observationBatchId) {
		final ObservationEntity observationEntity = new ObservationEntity(observation.getTypeKey(), patientId,
				observation.getDate(), observation.getValue(), observerUsername, new Date(), observationBatchId);
		observationMapper.insert(observationEntity);
		if (observationEntity.getId() == null || observationEntity.getId() < 0) {
			// TODO (glipecki): return id and handle this other way
			throw new RuntimeException("Observation was not added to DB!");
		}
	}

	@Override
	public List<Observation> getObservations(final Integer patientId) {
		final List<ObservationEntity> observationEntities = observationMapper.getObservations(ObservationsQuery
				.of(patientId));

		return convertObservationsToModel(observationEntities);
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey) {
		final List<ObservationEntity> observationEntities = getPredefinedWithCustomObservations(patientId, groupKey,
				null);

		return convertObservationsToModel(observationEntities);
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey, final Date date) {
		final List<ObservationEntity> observationEntities = getPredefinedWithCustomObservations(patientId, groupKey,
				date);

		return convertObservationsToModel(observationEntities);
	}

	@Override
	public Integer getNextObservationBatchId() {
		return observationMapper.getObservationNextBatchId();
	}

	@Override
	public void addObservationNorm(final ObservationNorm observationNorm) {
		observationMapper.insertNorm(ObservationNormEntity.from(observationNorm));
	}

	@Override
	public List<ObservationNorm> getObservationNorms() {
		final List<ObservationNormEntity> allNorms = observationMapper.getObservationNorms();
		final List<ObservationNorm> norms = new ArrayList<>();
		for (final ObservationNormEntity observationNormEntity : allNorms) {
			norms.add(observationNormEntity.asModel());
		}
		return norms;
	}

	@Override
	public List<CustomObservationType> getCustomObservationTypes() {
		final List<CustomObservationTypeEntity> customObservationTypes = observationMapper.getCustomObservationTypes();
		return convertCustomObservationTypesToModel(customObservationTypes);
	}

	@Override
	public void editObservationNorm(final ObservationNorm observationNorm) {
		throw new NotImplementedException();
	}

	@Override
	public void addCustomObservationType(final CustomObservationType customObservationType) {
		observationMapper.insertCustomObservationType(CustomObservationTypeEntity.of(customObservationType));
	}

	@Override
	public List<AdmissionObservationType> getAdmissionObservation(final int patientId) {
		final List<AdmissionObservationType> list = observationMapper.getAdmissionObservation(patientId);
		for (final AdmissionObservationType admissionObservationType : list) {
			admissionObservationType.setDirect(true);
		}
		final Integer admissionId = observationMapper.getCurrentAdmissionId(patientId);
		if (admissionId != null) {
			final List<Observation> obervations = getObservations(patientId);
			for (final Observation observation : obervations) {
				final AdmissionObservationType newType = new AdmissionObservationType(admissionId,
						observation.getTypeKey(), false);
				if (!list.contains(newType)) {
					list.add(newType);
				}
			}
			return list;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public void setAdmissionObservations(final int admissionId, final List<AdmissionObservationType> observations) {
		observationMapper.clearAdmissionTypes(admissionId);
		for (final AdmissionObservationType admissionObservationType : observations) {
			observationMapper.addAdmissionType(new AdmissionObservationType(admissionId, admissionObservationType
					.getTypeKey(), true));
		}
	}

	private List<ObservationEntity> getPredefinedWithCustomObservations(final Integer patientId, final String groupKey,
			final Date date) {
		final List<ObservationEntity> observations = new ArrayList<>();
		observations.addAll(observationMapper.getObservations(ObservationsQuery.of(patientId, date)
				.withObservationGroups(ObservationGroup.valueOf(groupKey))));
		observations.addAll(observationMapper.getObservations(ObservationsQuery.of(patientId, groupKey, date)));
		return observations;
	}

	private List<Observation> convertObservationsToModel(final List<ObservationEntity> observationEntities) {
		final List<Observation> observations = new ArrayList<Observation>();
		for (final ObservationEntity entity : observationEntities) {
			observations.add(entity.asModel());
		}
		return observations;
	}

	private List<CustomObservationType> convertCustomObservationTypesToModel(
			final List<CustomObservationTypeEntity> customObservationTypes) {
		final List<CustomObservationType> customTypes = new ArrayList<CustomObservationType>();
		for (final CustomObservationTypeEntity entity : customObservationTypes) {
			customTypes.add(entity.asModel());
		}
		return customTypes;
	}

	private ObservationsMapper observationMapper;

}
