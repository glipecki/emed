package eu.anmore.emed.observation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.anmore.emed.ServiceResult;

/**
 * Observations REST controller.
 * 
 * @author glipecki
 * 
 */
@RequestMapping(ObservationsRestDescriptor.ROOT_URI)
public interface ObservationsController extends Observations {

	@RequestMapping(value = ObservationsRestDescriptor.ADD, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	ServiceResult callAddObservations(@RequestBody final PatientObservationsDto patientObservationsDto);

	@RequestMapping(value = ObservationsRestDescriptor.GET_FOR_GROUP, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	List<Observation> callGetObservations(
			@PathVariable(ObservationsRestDescriptor.PARAM_PATIENT_ID) final Integer patientId,
			@PathVariable(ObservationsRestDescriptor.PARAM_GROUP_KEY) final String groupKeys);

	@RequestMapping(value = ObservationsRestDescriptor.GET_FOR_GROUP_AND_DATE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	List<Observation> callGetObservations(
			@PathVariable(ObservationsRestDescriptor.PARAM_PATIENT_ID) final Integer patientId,
			@PathVariable(ObservationsRestDescriptor.PARAM_GROUP_KEY) final String groupKeys,
			@PathVariable(ObservationsRestDescriptor.PARAM_DATE) final Long timestamp);

	void callAddObservationNorm(final ObservationNorm observationNorm);

	void callEditObservationNorm(final ObservationNorm observationNorm);

	@RequestMapping(value = ObservationsRestDescriptor.GET_OBSERVATION_NORMS, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	List<ObservationNorm> callGetObservationNorms();

	@RequestMapping(value = ObservationsRestDescriptor.GET_CUSTOM_TYPES, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	List<CustomObservationTypeDto> callGetCustomObservationTypes();

	@RequestMapping(value = ObservationsRestDescriptor.ADD_CUSTOM_TYPE, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	ServiceResult callAddCustomObservationType(@RequestBody final CustomObservationType customObservationType);

	@RequestMapping(value = "/setAdmissionObservation/{admissionId}", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	ServiceResult callSetAdmissionObservations(@PathVariable("admissionId") final int admissionId,
			@RequestBody final AdmissionObservationTypeDto dto);

	@Override
	@RequestMapping(value = "/admissionObservation/{patientId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	List<AdmissionObservationType> getAdmissionObservation(@PathVariable("patientId") final int patientId);
}
