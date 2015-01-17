package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.anmore.emed.ServiceResult;
import eu.anmore.emed.ServiceStatus;

/**
 * 
 * @author glipecki
 * 
 */
@RequestMapping(ObservationsRestDescriptor.ROOT_URI)
public class ObservationsControllerImpl implements ObservationsController {

	public ObservationsControllerImpl(final ObservationsBean observationsBean) {
		this.observationsBean = observationsBean;
	}

	@RequestMapping(value = ObservationsRestDescriptor.ADD, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	@Override
	public ServiceResult callAddObservations(@RequestBody final PatientObservationsDto patientObservationsDto) {
		LOG.info("> Adding observations for patient [patientId={}, observations={}]",
				patientObservationsDto.getPatientId(), patientObservationsDto.getObservations());

		observationsBean.addObservations(patientObservationsDto.getPatientId(),
				patientObservationsDto.getObservations(), patientObservationsDto.getObserverUsername());

		LOG.info("< observations added.");
		return ServiceResult.ok();
	}

	@RequestMapping(value = ObservationsRestDescriptor.GET_FOR_GROUP, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Override
	public List<Observation> callGetObservations(
			@PathVariable(ObservationsRestDescriptor.PARAM_PATIENT_ID) final Integer patientId,
			@PathVariable(ObservationsRestDescriptor.PARAM_GROUP_KEY) final String groupKeys) {
		LOG.info("> Getting patient observations [patientId={} observationsGroupKeys={}]", patientId, groupKeys);

		final List<Observation> observations = new ArrayList<>();
		for (final String groupKey : groupKeys.split(ObservationsRestDescriptor.VALUES_SEPARATOR)) {
			observations.addAll(observationsBean.getObservations(patientId, groupKey));
		}

		LOG.info("< returning observations [{}]", observations);
		return observations;
	}

	@RequestMapping(value = ObservationsRestDescriptor.GET_FOR_GROUP_AND_DATE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Override
	public List<Observation> callGetObservations(
			@PathVariable(ObservationsRestDescriptor.PARAM_PATIENT_ID) final Integer patientId,
			@PathVariable(ObservationsRestDescriptor.PARAM_GROUP_KEY) final String groupKeys,
			@PathVariable(ObservationsRestDescriptor.PARAM_DATE) final Long timestamp) {
		final Date date = new Date(timestamp);
		LOG.info("> Getting patient observations [patientId={} groupKeys={} date={}]", patientId, groupKeys, date);

		final List<Observation> observations = new ArrayList<>();
		for (final String groupKey : groupKeys.split(ObservationsRestDescriptor.VALUES_SEPARATOR)) {
			observations.addAll(observationsBean.getObservations(patientId, groupKey, date));
		}

		LOG.info("< returning observations [{}]", observations);
		return observations;
	}

	@RequestMapping(value = ObservationsRestDescriptor.GET_OBSERVATION_NORMS, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Override
	public List<ObservationNorm> callGetObservationNorms() {
		return observationsBean.getObservationNorms();
	}

	@RequestMapping(value = ObservationsRestDescriptor.GET_CUSTOM_TYPES, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Override
	public List<CustomObservationTypeDto> callGetCustomObservationTypes() {
		return asCustomObservationTypeDtos(observationsBean.getCustomObservationTypes());
	}

	@Override
	public void addCustomObservationType(final CustomObservationType customObservationType) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@RequestMapping(value = ObservationsRestDescriptor.ADD_CUSTOM_TYPE, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@Override
	public ServiceResult callAddCustomObservationType(@RequestBody final CustomObservationType customObservationType) {
		if (customObservationType.getKey() != null && customObservationType.getKey().length() >= 20) {
			throw new RuntimeException("To long observation type key");
		}
		observationsBean.addCustomObservationType(customObservationType);
		return new ServiceResult(ServiceStatus.OK);
	}

	@RequestMapping(value = "/admissionObservation/{patientId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@Override
	public List<AdmissionObservationType> getAdmissionObservation(@PathVariable("patientId") final int patientId) {
		return observationsBean.getAdmissionObservation(patientId);
	}

	@RequestMapping(value = "/setAdmissionObservation/{admissionId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	@Override
	public ServiceResult callSetAdmissionObservations(@PathVariable("admissionId") final int admissionId,
			@RequestBody final AdmissionObservationTypeDto dto) {
		observationsBean.setAdmissionObservations(admissionId, dto.getAdmissionTypes());
		return new ServiceResult(ServiceStatus.OK);
	}

	@Override
	public void callAddObservationNorm(final ObservationNorm observationNorm) {
		// TODO Auto-generated method stub
	}

	@Override
	public void callEditObservationNorm(final ObservationNorm observationNorm) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Observation> getObservations(final Integer patientId) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public void addObservations(final Integer patientId, final List<Observation> observations,
			final String observerUsername) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<Observation> getObservations(final Integer patientId, final String groupKey, final Date date) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public void addObservationNorm(final ObservationNorm observationNorm) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public void editObservationNorm(final ObservationNorm observationNorm) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<ObservationNorm> getObservationNorms() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<CustomObservationType> getCustomObservationTypes() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public void setAdmissionObservations(final int admissionId, final List<AdmissionObservationType> observations) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	private static final Logger LOG = LoggerFactory.getLogger(ObservationsControllerImpl.class);

	private List<CustomObservationTypeDto> asCustomObservationTypeDtos(
			final List<CustomObservationType> customObservationTypes) {
		final List<CustomObservationTypeDto> dtos = new ArrayList<>();
		for (final CustomObservationType customObservationType : customObservationTypes) {
			dtos.add(new CustomObservationTypeDto(customObservationType));
		}
		return dtos;
	}

	private final ObservationsBean observationsBean;

}
