package eu.anmore.emed.patient;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Implementation of {@link PatientsController}.
 * 
 * @author mmiedzinski
 */
@RequestMapping(PatientsRestDescriptor.ROOT_URI)
public class PatientsControllerImpl implements PatientsController {

	@Override
	@RequestMapping(value = PatientsRestDescriptor.ALL_PATIENT_LIST_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public PatientListDto callGetAll() {
		final List<Patient> patients = patientBean.getAll();
		final PatientListDto listDto = PatientListDto.valueOf(patients);
		log.debug("[{}]", listDto);
		return listDto;
	}

	@Override
	@RequestMapping(value = PatientsRestDescriptor.EDIT_PATIENT_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public PatientDto callEdit(@PathVariable(PatientsRestDescriptor.PARIENT_ID_PARAM) final int id,
			@RequestBody final PatientDiff changes) {
		final Patient patient = patientBean.edit(id, changes);
		final PatientDto dto = PatientDto.valueOf(patient);
		log.debug("[{}]", dto);
		return dto;
	}

	@Override
	@RequestMapping(value = PatientsRestDescriptor.INSERT_PATIENT_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public PatientDto callInsert(@RequestBody final PatientDiff patientDiff) {
		final Patient patient = patientBean.insert(patientDiff);
		final PatientDto dto = PatientDto.valueOf(patient);
		log.debug("[{}]", dto);
		return dto;
	}

	@Override
	@RequestMapping(value = PatientsRestDescriptor.GET_PATIENT_WITH_ADMISSION_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public PatientDto callGetPatientWithAdmission(@PathVariable(PatientsRestDescriptor.PARIENT_ID_PARAM) final int id) {
		final Patient patient = patientBean.getPatientWithAdmissionList(id);
		final PatientDto dto = PatientDto.valueOf(patient);
		log.debug("[{}]", dto);
		return dto;
	}

	@Override
	@RequestMapping(value = PatientsRestDescriptor.ADMITTED_PATIENT_LIST_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public PatientListDto callGetAdmitted() {
		final List<Patient> patients = patientBean.getAdmitted();
		final PatientListDto dto = PatientListDto.valueOf(patients);
		log.debug("[{}]", dto);
		return dto;
	}

	@Override
	@RequestMapping(value = PatientsRestDescriptor.NOT_ADMITTED_PATIENT_LIST_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public PatientListDto callGetNotAdmitted() {
		final List<Patient> patients = patientBean.getNotAdmitted();
		final PatientListDto dto = PatientListDto.valueOf(patients);
		log.debug("[{}]", dto);
		return dto;
	}

	@Override
	@RequestMapping(value = PatientsRestDescriptor.ADMITTED_PATIENT_LIST_WITH_ADMISSIONS_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public PatientListDto callGetAdmittedPatientsWithAdmissions() {
		final List<Patient> patients = patientBean.getAdmittedPatientsWithAdmissions();
		final PatientListDto dto = PatientListDto.valueOf(patients);
		log.debug("[{}]", dto);
		return dto;
	}

	@Override
	public List<Patient> getAll() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public Patient edit(final int id, final PatientDiff changes) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public Patient insert(final PatientDiff patient) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public Patient getPatientWithAdmissionList(final int patientId) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<Patient> getAdmitted() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<Patient> getNotAdmitted() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<Patient> getAdmittedPatientsWithAdmissions() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	PatientsControllerImpl(final PatientsBean patientBean) {
		this.patientBean = patientBean;
	}

	/** Just to fulfill CGLIB requirements */
	PatientsControllerImpl() {
		patientBean = null;
	}

	private static final Logger log = LoggerFactory.getLogger(PatientsControllerImpl.class);

	private final PatientsBean patientBean;
}
