package eu.anmore.emed.patient;

/**
 * Patient REST Service descriptor.
 * <p>
 * Provides information about urls, param names.
 * </p>
 * 
 * @author mmiedzinski
 */
public final class PatientsRestDescriptor {

	/**
	 * @return url to service listing patients.
	 */
	public static String getPatientListRestUrl() {
		return ROOT_URI + ALL_PATIENT_LIST_URL_PATTERN;
	}

	/**
	 * @return url to service inserting patients.
	 */
	public static String getInsertPatientRestUrl() {
		return ROOT_URI + INSERT_PATIENT_URL_PATTERN;
	}

	/**
	 * @return url to service edits patients.
	 */
	public static String getEditPatientRestUrl(final int patientId) {
		return ROOT_URI + EDIT_PATIENT_URL_PATTERN.replace("{" + PARIENT_ID_PARAM + "}", String.valueOf(patientId));
	}

	/** Patient root module url */
	public static final String ROOT_URI = "/patients";

	/** Url to service returning list of all patients. */
	public static final String ALL_PATIENT_LIST_URL_PATTERN = "/list";

	/** Url to service returning list of admitted patients. */
	public static final String ADMITTED_PATIENT_LIST_URL_PATTERN = "/admittedList";

	/** Url to service returning list of admitted patients with admissions. */
	public static final String ADMITTED_PATIENT_LIST_WITH_ADMISSIONS_URL_PATTERN = "/admittedListWithAdmissions";

	/** Url to service returning list of not admitted patients. */
	public static final String NOT_ADMITTED_PATIENT_LIST_URL_PATTERN = "/notAdmittedList";

	/** Patient id param name */
	public static final String PARIENT_ID_PARAM = "id";

	/** Url to service editing patients. */
	public static final String EDIT_PATIENT_URL_PATTERN = "/edit/{id}";

	/** Url to service inserting patients. */
	public static final String INSERT_PATIENT_URL_PATTERN = "/insert";

	/** Url to service getting patient with admission list by id */
	public static final String GET_PATIENT_WITH_ADMISSION_URL_PATTERN = "/getWithAdmission/{id}";

	/** Utility class */
	private PatientsRestDescriptor() {
		// intentionally left blank
	}
}
