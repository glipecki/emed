package eu.anmore.emed.observation;

import java.util.Date;
import java.util.List;

/**
 * Observations service.
 * 
 * @author glipecki
 * 
 */
public interface Observations {

	/**
	 * Gets observations for current admission of patient.
	 * 
	 * @param patientId
	 *            patient id to get observations
	 * @return list of observation for current admission of patient
	 */
	List<Observation> getObservations(final Integer patientId);

	/**
	 * Gets observations of specified group for current admission of patient.
	 * 
	 * @param patientId
	 *            patient id to get observations
	 * @param groupKey
	 *            group key to get observations
	 * @return list of observation within group for current admission of patient
	 */
	List<Observation> getObservations(final Integer patientId, final String groupKey);

	List<Observation> getObservations(final Integer patientId, final String groupKey, Date date);

	/**
	 * Adds observations for current patient admission.
	 * 
	 * @param patientId
	 *            patient id to add observations
	 * @param observations
	 *            observations to add
	 * @param observerUsername
	 *            username of user which adds observation
	 */
	void addObservations(final Integer patientId, List<Observation> observations, final String observerUsername);

	/**
	 * Adds observation norm for specified age range.
	 * 
	 * @param observationNorm
	 *            norm min and max values
	 */
	void addObservationNorm(final ObservationNorm observationNorm);

	/**
	 * Edits observation norm.
	 * <p>
	 * Overrides old min and max norm values.
	 * </p>
	 * 
	 * @param observationNorm
	 */
	void editObservationNorm(final ObservationNorm observationNorm);

	/**
	 * Gets observation norms.
	 * 
	 * @return
	 */
	List<ObservationNorm> getObservationNorms();

	/**
	 * Gets user defined observation types.
	 * 
	 * @return
	 */
	List<CustomObservationType> getCustomObservationTypes();

	/**
	 * Adds new custom observation type.
	 * 
	 * @param customObservationType
	 */
	void addCustomObservationType(CustomObservationType customObservationType);

	List<AdmissionObservationType> getAdmissionObservation(final int patientId);

	void setAdmissionObservations(final int admissionId, final List<AdmissionObservationType> observations);

}
