package eu.anmore.emed.patient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller interface for Patients.
 * 
 * @author mmiedzinski
 */
@RequestMapping(PatientsRestDescriptor.ROOT_URI)
public interface PatientsController extends Patients {

	/**
	 * Get all patient list.
	 * 
	 * @return
	 */
	@RequestMapping(value = PatientsRestDescriptor.ALL_PATIENT_LIST_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	PatientListDto callGetAll();

	/**
	 * Edit patient object.
	 * 
	 * @param id
	 *            the id of patient
	 * @param changes
	 *            the changes object
	 * @return the edited object
	 */
	@RequestMapping(value = PatientsRestDescriptor.EDIT_PATIENT_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	PatientDto callEdit(@PathVariable(PatientsRestDescriptor.PARIENT_ID_PARAM) final int id,
			@RequestBody final PatientDiff changes);

	/**
	 * Insert patient into system.
	 * 
	 * @param patient
	 *            the patient to insert
	 * @return the inserted object with id
	 */
	@RequestMapping(value = PatientsRestDescriptor.INSERT_PATIENT_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	PatientDto callInsert(@RequestBody final PatientDiff patientDiff);

	/**
	 * Get Patient object with list of admissions.
	 * 
	 * @param id
	 *            the id of patient
	 * @return
	 */
	@RequestMapping(value = PatientsRestDescriptor.GET_PATIENT_WITH_ADMISSION_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	PatientDto callGetPatientWithAdmission(@PathVariable(PatientsRestDescriptor.PARIENT_ID_PARAM) final int id);

	/**
	 * Get admitted patient list.
	 * 
	 * @return
	 */
	@RequestMapping(value = PatientsRestDescriptor.ADMITTED_PATIENT_LIST_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	PatientListDto callGetAdmitted();

	/**
	 * Get not admitted patient list.
	 * 
	 * @return
	 */
	@RequestMapping(value = PatientsRestDescriptor.NOT_ADMITTED_PATIENT_LIST_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	PatientListDto callGetNotAdmitted();

	/**
	 * Get admitted patient list with admissions.
	 * 
	 * @return
	 */
	@RequestMapping(value = PatientsRestDescriptor.ADMITTED_PATIENT_LIST_WITH_ADMISSIONS_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	PatientListDto callGetAdmittedPatientsWithAdmissions();
}
