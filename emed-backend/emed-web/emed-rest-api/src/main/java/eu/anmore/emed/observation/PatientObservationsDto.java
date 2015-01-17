package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient observations DTO.
 * 
 * @author glipecki
 * 
 */
public class PatientObservationsDto {

	/**
	 * Patient observations of patient id and patient observations.
	 * 
	 * @param patientId
	 *            patient id
	 * @param observations
	 *            patient observations
	 * @return patient observations DTO object
	 */
	public static PatientObservationsDto of(final Integer patientId, final List<Observation> observations,
			final String observerUsername) {
		checkArgument(patientId != null, "Patient observations should not be null");
		checkArgument(observations != null, "Patient observations should not be null");

		return new PatientObservationsDto(patientId, observations, observerUsername);
	}

	/**
	 * Serialization constructor.
	 * 
	 * @deprecated serialization constructor
	 */
	@Deprecated
	public PatientObservationsDto() {
	}

	public PatientObservationsDto(final Integer patientId, final List<Observation> observations,
			final String observerUsername) {
		this.patientId = patientId;
		this.observations = observations;
		this.observerUsername = observerUsername;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(final Integer patientId) {
		this.patientId = patientId;
	}

	public List<Observation> getObservations() {
		return observations;
	}

	public void setObservations(final List<Observation> observations) {
		this.observations = observations;
	}

	@Override
	public String toString() {
		return String.format("PatientObservationsDto [patientId=%s, observations=%s]", patientId, observations);
	}

	public String getObserverUsername() {
		return observerUsername;
	}

	private static void checkArgument(final boolean condition, final String errorMessage) {
		if (!condition) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	private Integer patientId;

	private String observerUsername;

	private List<Observation> observations = new ArrayList<Observation>();

}
