package eu.anmore.emed.patient;

import java.util.List;

import org.springframework.web.client.RestClientException;

import eu.anmore.emed.BaseServiceRestConnector;

/**
 * Implementation of {@link PatientsConnector}
 * 
 * @author mmiedzinski
 */
public class PatientsServiceRestConnector extends BaseServiceRestConnector implements PatientsConnector {

	/** */
	public PatientsServiceRestConnector(final String serverUri) {
		super();
		this.patientServiceUri = serverUri + "/" + PatientsRestDescriptor.ROOT_URI + "/";
	}

	@Override
	public List<Patient> getAll() {
		PatientListDto patientDtoList = null;
		try {
			patientDtoList = patientListRestCall();
		} catch (final RestClientException restClientException) {
			handleConnectionError(restClientException);
		}
		return patientDtoList.asPatientList();
	}

	@Override
	public Patient edit(final int id, final PatientDiff changes) {
		final String uri = generatePatientEditServiceAddress();
		final PatientDto patientDto = restTemplate.postForObject(uri, changes, PatientDto.class, id);
		return patientDto.asPatient();
	}

	@Override
	public Patient insert(final PatientDiff patient) {
		final String uri = generateInsertServiceUri();
		final PatientDto patientDto = restTemplate.postForObject(uri, patient, PatientDto.class);
		return patientDto.asPatient();
	}

	@Override
	public Patient getPatientWithAdmissionList(final int patientId) {
		final String uri = generateGetPatientWithAdmissionsServiceAddress();
		final PatientDto patientDto = restTemplate.getForObject(uri, PatientDto.class, patientId);
		return patientDto.asPatient();
	}

	@Override
	public List<Patient> getAdmitted() {
		final String uri = patientServiceUri + PatientsRestDescriptor.ADMITTED_PATIENT_LIST_URL_PATTERN;
		final PatientListDto patientDtoList = restTemplate.getForObject(uri, PatientListDto.class);
		return patientDtoList.asPatientList();
	}

	@Override
	public List<Patient> getNotAdmitted() {
		final String uri = patientServiceUri + PatientsRestDescriptor.NOT_ADMITTED_PATIENT_LIST_URL_PATTERN;
		final PatientListDto patientDtoList = restTemplate.getForObject(uri, PatientListDto.class);
		return patientDtoList.asPatientList();
	}

	@Override
	public List<Patient> getAdmittedPatientsWithAdmissions() {
		final String uri = patientServiceUri + PatientsRestDescriptor.ADMITTED_PATIENT_LIST_WITH_ADMISSIONS_URL_PATTERN;
		final PatientListDto patientDtoList = restTemplate.getForObject(uri, PatientListDto.class);
		return patientDtoList.asPatientList();
	}

	private String generateInsertServiceUri() {
		return String.format("%s%s", patientServiceUri, PatientsRestDescriptor.INSERT_PATIENT_URL_PATTERN);
	}

	private String generatePatientEditServiceAddress() {
		return String.format("%s%s", patientServiceUri, PatientsRestDescriptor.EDIT_PATIENT_URL_PATTERN);
	}

	private String generateGetPatientWithAdmissionsServiceAddress() {
		return String.format("%s%s", patientServiceUri, PatientsRestDescriptor.GET_PATIENT_WITH_ADMISSION_URL_PATTERN);
	}

	private PatientListDto patientListRestCall() {
		final String uri = patientServiceUri + PatientsRestDescriptor.ALL_PATIENT_LIST_URL_PATTERN;
		return restTemplate.getForObject(uri, PatientListDto.class);
	}

	private final String patientServiceUri;
}
