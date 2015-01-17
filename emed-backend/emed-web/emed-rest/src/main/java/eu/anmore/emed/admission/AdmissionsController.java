package eu.anmore.emed.admission;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller interface for Admissions.
 * 
 * @author mmiedzinski
 */
@RequestMapping(AdmissionsRestDescriptor.ROOT_URI)
public interface AdmissionsController extends Admissions {

	/**
	 * Admit patient service.
	 * 
	 * @param admissionDiff
	 *            the diff object.
	 * @return
	 */
	@RequestMapping(value = AdmissionsRestDescriptor.ADMIT_PATIENT_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	AdmissionDto callAdmitPatient(@RequestBody AdmissionDiff admissionDiff);

	/**
	 * Edit admission by id.
	 * 
	 * @param admissionId
	 *            the admission id
	 * @param admissionDiff
	 *            the admission changes object
	 * @return the updated object
	 */
	@RequestMapping(value = AdmissionsRestDescriptor.EDIT_ADMISSION_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	AdmissionDto callEdit(@PathVariable(AdmissionsRestDescriptor.ADMISSION_ID_PARAM) int admissionId,
			@RequestBody AdmissionDiff admissionDiff);
}
