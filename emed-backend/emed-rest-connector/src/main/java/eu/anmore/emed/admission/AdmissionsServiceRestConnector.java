package eu.anmore.emed.admission;

import eu.anmore.emed.BaseServiceRestConnector;

/**
 * Implementation of {@link AdmissionsConnector}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsServiceRestConnector extends BaseServiceRestConnector implements AdmissionsConnector {

	public AdmissionsServiceRestConnector(final String serverUri) {
		super();
		this.admissionsServiceUri = String.format("%s/%s", serverUri, AdmissionsRestDescriptor.ROOT_URI);
	}

	@Override
	public Admission admitPatient(AdmissionDiff admissionDiff) {
		String uri = generateAdmitPatientServiceAddress();
		AdmissionDto admissionDto = restTemplate.postForObject(uri, admissionDiff, AdmissionDto.class);
		return admissionDto.asAdmission();
	}

	@Override
	public Admission edit(int admissionId, AdmissionDiff admissionDiff) {
		final String uri = generateAdmissionEditServiceAddress();
		AdmissionDto admissionDto = restTemplate.postForObject(uri, admissionDiff, AdmissionDto.class, admissionId);
		return admissionDto.asAdmission();
	}

	private String generateAdmissionEditServiceAddress() {
		return String.format("%s%s", admissionsServiceUri, AdmissionsRestDescriptor.EDIT_ADMISSION_URL_PATTERN);
	}

	private String generateAdmitPatientServiceAddress() {
		return String.format("%s%s", admissionsServiceUri, AdmissionsRestDescriptor.ADMIT_PATIENT_URL_PATTERN);
	}

	private final String admissionsServiceUri;
}
