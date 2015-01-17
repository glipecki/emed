package eu.anmore.emed.mobi.patients;

import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.patient.Patient;
import eu.anmore.emed.patient.PatientDiff;
import eu.anmore.emed.patient.PatientDto;
import eu.anmore.emed.patient.PatientListDto;
import eu.anmore.emed.patient.PatientsConnector;
import eu.anmore.emed.patient.PatientsRestDescriptor;

public class PatientsRestConnector implements PatientsConnector {

	@Inject
	public PatientsRestConnector(final ApplicationConfigurationFacade applicationState) {
		initRestTemplate();
		this.applicationState = applicationState;
	}

	public List<Patient> getAll() {
		throw new RuntimeException("Operation not supported");
	}

	public Patient edit(final int id, final PatientDiff changes) {
		throw new RuntimeException("Operation not supported");
	}

	public Patient insert(final PatientDiff patient) {
		throw new RuntimeException("Operation not supported");
	}

	public Patient getPatientWithAdmissionList(final int patientId) {
		final String getAdmittedPatientsUrl = String.format("%s%s%s", applicationState.getServerAddress(),
				PatientsRestDescriptor.ROOT_URI, PatientsRestDescriptor.GET_PATIENT_WITH_ADMISSION_URL_PATTERN);
		final PatientDto patientWithAdmissionDto = restTemplate.getForObject(getAdmittedPatientsUrl, PatientDto.class,
				patientId);
		return patientWithAdmissionDto.asPatient();
	}

	public List<Patient> getAdmitted() {
		final String getAdmittedPatientsUrl = String.format("%s%s%s", applicationState.getServerAddress(),
				PatientsRestDescriptor.ROOT_URI, PatientsRestDescriptor.ADMITTED_PATIENT_LIST_URL_PATTERN);
		final PatientListDto patientDtoList = restTemplate.getForObject(getAdmittedPatientsUrl, PatientListDto.class);
		return patientDtoList.asPatientList();
	}

	public List<Patient> getNotAdmitted() {
		throw new RuntimeException("Operation not supported");
	}

	public List<Patient> getAdmittedPatientsWithAdmissions() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initRestTemplate() {
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().clear();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		if (restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
			((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(1000);
			((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(1000);
		} else if (restTemplate.getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
			((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(1000);
			((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(1000);
		}
	}

	private final ApplicationConfigurationFacade applicationState;

	private RestTemplate restTemplate;

}
