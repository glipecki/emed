package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import eu.anmore.emed.BaseServiceRestConnector;
import eu.anmore.emed.ServiceResult;
import eu.anmore.emed.ServiceStatus;

/**
 * Observations REST service connector.
 * 
 * @author glipecki
 * 
 */
public class ObservationsRestConnector extends BaseServiceRestConnector implements ObservationsConnector {

	static public String join(final List<String> list, final String conjunction) {
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final String item : list) {
			if (first) {
				first = false;
			} else {
				sb.append(conjunction);
			}
			sb.append(item);
		}
		return sb.toString();
	}

	/** */
	public ObservationsRestConnector(final String serverUri) {
		super();
		this.serverUri = serverUri;
	}

	@Override
	public List<Observation> getObservations(final Integer patientId) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey) {
		final String url = ObservationsRestDescriptor.getGetObservationsUrl(serverUri, patientId, groupKey);

		final Observation[] serviceResult = restTemplate.getForObject(url, Observation[].class);

		return new ArrayList<Observation>(Arrays.asList(serviceResult));
	}

	@Override
	public void addObservations(final Integer patientId, final List<Observation> observations,
			final String observerUsername) {
		final String url = ObservationsRestDescriptor.getAddObservationUrl(serverUri);
		final PatientObservationsDto patientObservationsDto = PatientObservationsDto.of(patientId, observations,
				observerUsername);

		final ServiceResult serviceResult = restTemplate
				.postForObject(url, patientObservationsDto, ServiceResult.class);

		if (serviceResult == null || serviceResult.getStatus() != ServiceStatus.OK) {
			throw new RuntimeException(String.format("Service returned bad response: [%s]", serviceResult.getStatus()));
		}
	}

	public List<Observation> getObservations(final Integer patientId, final List<String> groupKeys) {
		return getObservations(patientId, join(groupKeys, ObservationsRestDescriptor.VALUES_SEPARATOR));
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey, final Date date) {
		final String url = ObservationsRestDescriptor
				.getGetObservationsUrlForDate(serverUri, patientId, groupKey, date);
		final Observation[] serviceResult = restTemplate.getForObject(url, Observation[].class);
		return new ArrayList<Observation>(Arrays.asList(serviceResult));
	}

	@Override
	public List<ObservationNorm> getObservationNorms() {
		final String url = ObservationsRestDescriptor.getGetObservationNormsUrl(serverUri);
		final ObservationNorm[] serviceResult = restTemplate.getForObject(url, ObservationNorm[].class);
		return new ArrayList<ObservationNorm>(Arrays.asList(serviceResult));
	}

	@Override
	public List<CustomObservationType> getCustomObservationTypes() {
		final String url = ObservationsRestDescriptor.getGetCustomObservationsUrl(serverUri);
		final CustomObservationType[] serviceResult = restTemplate.getForObject(url, CustomObservationType[].class);
		return new ArrayList<CustomObservationType>(Arrays.asList(serviceResult));
	}

	@Override
	public void addCustomObservationType(final CustomObservationType customObservationType) {
		final CustomObservationTypeDto dto = new CustomObservationTypeDto(customObservationType);
		final String url = serverUri + ObservationsRestDescriptor.ROOT_URI + ObservationsRestDescriptor.ADD_CUSTOM_TYPE;
		final ServiceResult serviceResult = restTemplate.postForObject(url, dto, ServiceResult.class);
		if (serviceResult == null || serviceResult.getStatus() != ServiceStatus.OK) {
			throw new RuntimeException(String.format("Service returned bad response: [%s]", serviceResult.getStatus()));
		}
	}

	@Override
	public void setAdmissionObservations(final int admissionId, final List<AdmissionObservationType> types) {
		final String url = String.format("%s%s%s", serverUri, ObservationsRestDescriptor.ROOT_URI,
				"/setAdmissionObservation/{admissionId}");
		final AdmissionObservationTypeDto dto = new AdmissionObservationTypeDto(types);

		final ServiceResult serviceResult = restTemplate.postForObject(url, dto, ServiceResult.class, admissionId);

		if (serviceResult == null || serviceResult.getStatus() != ServiceStatus.OK) {
			throw new RuntimeException(String.format("Service returned bad response: [%s]", serviceResult.getStatus()));
		}
	}

	@Override
	public List<AdmissionObservationType> getAdmissionObservation(final int patientId) {
		final String url = String.format("%s%s%s", serverUri, ObservationsRestDescriptor.ROOT_URI,
				"/admissionObservation/{patientId}");
		final AdmissionObservationType[] serviceResult = restTemplate.getForObject(url,
				AdmissionObservationType[].class, patientId);
		return new ArrayList<AdmissionObservationType>(Arrays.asList(serviceResult));
	}

	@Override
	public void addObservationNorm(final ObservationNorm observationNorm) {
		// TODO Auto-generated method stub
	}

	@Override
	public void editObservationNorm(final ObservationNorm observationNorm) {
		// TODO Auto-generated method stub
	}

	private final String serverUri;

}
