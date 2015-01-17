package eu.anmore.emed.observation;

import java.util.Date;

/**
 * Observations REST service descriptor.
 * 
 * @author glipecki
 * 
 */
public final class ObservationsRestDescriptor {

	/**
	 * Gets add observation service path.
	 * 
	 * @return service path
	 */
	public static String getAddObservationPath() {
		return ROOT_URI + ADD;
	}

	/**
	 * Gets get observations service path.
	 * 
	 * @param patientId
	 *            patient id to get observations
	 * @param groupKey
	 *            requested observations group key
	 * @return service path
	 */
	public static String getGetObservationsPath(final String patientId, final String groupKey) {
		return ROOT_URI
				+ GET_FOR_GROUP.replace(getParamHolder(PARAM_PATIENT_ID), patientId).replace(
						getParamHolder(PARAM_GROUP_KEY), groupKey);
	}

	public static String getGetObservationsPathForDate(final String patientId, final String groupKey, final Date date) {
		return ROOT_URI
				+ GET_FOR_GROUP_AND_DATE.replace(getParamHolder(PARAM_PATIENT_ID), patientId)
						.replace(getParamHolder(PARAM_GROUP_KEY), groupKey)
						.replace(getParamHolder(PARAM_DATE), String.valueOf(date.getTime()));
	}

	/**
	 * Gets add observation service URL.
	 * 
	 * @param serverUrl
	 *            server URL (as service URL base)
	 * @return service URL
	 */
	public static String getAddObservationUrl(final String serverUrl) {
		return serverUrl + getAddObservationPath();
	}

	/**
	 * Gets get observations service URL.
	 * 
	 * @param serverUrl
	 *            server URL (as service URL base)
	 * @param patientId
	 *            patient id to get observations
	 * @param groupKey
	 *            requested observations group key
	 * @return service URL
	 */
	public static String getGetObservationsUrl(final String serverUrl, final Integer patientId, final String groupKey) {
		return serverUrl + getGetObservationsPath(String.valueOf(patientId), groupKey);
	}

	public static String getGetObservationsUrlForDate(final String serverUrl, final Integer patientId,
			final String groupKey, final Date date) {
		return serverUrl + getGetObservationsPathForDate(String.valueOf(patientId), groupKey, date);
	}

	public static String getGetObservationNormsUrl(final String serverUrl) {
		return serverUrl + getGetObservationNormsPath();
	}

	public static String getGetCustomObservationsUrl(final String serverUrl) {
		return serverUrl + getCustomObservationsPath();
	}

	/** Observations root module url */
	public static final String ROOT_URI = "/observations";

	/** Adds observation */
	public static final String ADD = "/add";

	/** Request param: patient id */
	public static final String PARAM_PATIENT_ID = "patientId";

	/** Request param: group key */
	public static final String PARAM_GROUP_KEY = "groupKey";

	/** Request param: date */
	public static final String PARAM_DATE = "date";

	/** Gets patient observations for group */
	public static final String GET_FOR_GROUP = "/get/{" + PARAM_PATIENT_ID + "}/{" + PARAM_GROUP_KEY + "}";

	/** Gets patient observations for group and date */
	public static final String GET_FOR_GROUP_AND_DATE = "/getWithDate/{" + PARAM_PATIENT_ID + "}/{" + PARAM_GROUP_KEY
			+ "}/{" + PARAM_DATE + "}";

	/** Gets observation norms */
	public static final String GET_OBSERVATION_NORMS = "/getNorms";

	/** Gets custom observation types */
	public static final String GET_CUSTOM_TYPES = "/customTypes";

	public static final String ADD_CUSTOM_TYPE = "/addCustomType";

	public static final String VALUES_SEPARATOR = ",";

	private static String getGetObservationNormsPath() {
		return ROOT_URI + GET_OBSERVATION_NORMS;
	}

	private static String getCustomObservationsPath() {
		return ROOT_URI + GET_CUSTOM_TYPES;
	}

	private static String getParamHolder(final String name) {
		return "{" + name + "}";
	}

	private ObservationsRestDescriptor() {
		// constants class.
	}

}
