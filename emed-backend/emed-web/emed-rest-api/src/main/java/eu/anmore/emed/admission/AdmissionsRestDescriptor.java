package eu.anmore.emed.admission;

/**
 * Admission REST Service descriptor.
 * <p>
 * Provides information about urls, param names.
 * </p>
 * 
 * @author mmiedzinski
 */
public final class AdmissionsRestDescriptor {

	/**
	 * @return url to service admits patients.
	 */
	public static String getAdmitPatientRestUrl() {
		return ROOT_URI + ADMIT_PATIENT_URL_PATTERN;
	}

	/**
	 * @return url to service edits admissions.
	 */
	public static String getEditAdmissionRestUrl(final int admissionId) {
		return ROOT_URI
				+ EDIT_ADMISSION_URL_PATTERN.replace("{" + ADMISSION_ID_PARAM + "}", String.valueOf(admissionId));
	}

	/** Patient root module url */
	public static final String ROOT_URI = "/admissions";

	/** Url to service admited admissions. */
	public static final String ADMIT_PATIENT_URL_PATTERN = "/admitPatient";

	/** Url to service editing admissions. */
	public static final String EDIT_ADMISSION_URL_PATTERN = "/edit/{id}";

	/** Admission id param name */
	public static final String ADMISSION_ID_PARAM = "id";

	/** Utility class */
	private AdmissionsRestDescriptor() {
		// intentionally left blank
	}
}
