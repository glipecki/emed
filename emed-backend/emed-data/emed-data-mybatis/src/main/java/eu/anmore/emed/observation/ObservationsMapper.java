package eu.anmore.emed.observation;

import java.util.List;

/**
 * @author glipecki
 */
public interface ObservationsMapper {

	void insert(ObservationEntity observationEntity);

	List<ObservationEntity> getObservations(ObservationsQuery observationQuery);

	Integer getObservationNextBatchId();

	void insertNorm(ObservationNormEntity normEntity);

	List<ObservationNormEntity> getObservationNorms();

	List<CustomObservationTypeEntity> getCustomObservationTypes();

	void insertCustomObservationType(CustomObservationTypeEntity entity);

	List<AdmissionObservationType> getAdmissionObservation(int patientId);

	Integer getCurrentAdmissionId(int patientId);

	void clearAdmissionTypes(int admissionId);

	void addAdmissionType(AdmissionObservationType admissionObservationType);

}
