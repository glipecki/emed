package eu.anmore.emed.observation;

import java.util.Date;
import java.util.List;

/**
 * @author glipecki
 */
public interface ObservationsDao {

	void addObservation(Integer patientId, Observation observation, final String observerUsername,
			Integer observationBatchId);

	List<Observation> getObservations(Integer patientId, String groupKey);

	List<Observation> getObservations(Integer patientId, String groupKey, Date date);

	List<Observation> getObservations(Integer patientId);

	Integer getNextObservationBatchId();

	void addObservationNorm(ObservationNorm observationNorm);

	void editObservationNorm(ObservationNorm observationNorm);

	List<ObservationNorm> getObservationNorms();

	List<CustomObservationType> getCustomObservationTypes();

	void addCustomObservationType(CustomObservationType customObservationType);

	List<AdmissionObservationType> getAdmissionObservation(int patientId);

	void setAdmissionObservations(int admissionId, List<AdmissionObservationType> observations);

}
