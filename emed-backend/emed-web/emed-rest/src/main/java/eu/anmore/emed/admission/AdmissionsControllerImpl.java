package eu.anmore.emed.admission;

import javax.naming.OperationNotSupportedException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Implementation of {@link AdmissionsController}.
 * 
 * @author mmiedzinski
 */
@RequestMapping(AdmissionsRestDescriptor.ROOT_URI)
public class AdmissionsControllerImpl implements AdmissionsController {

	@Override
	@RequestMapping(value = AdmissionsRestDescriptor.ADMIT_PATIENT_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public AdmissionDto callAdmitPatient(@RequestBody AdmissionDiff admissionDiff) {
		Admission admission = admissionBean.admitPatient(admissionDiff);
		return AdmissionDto.valueOf(admission);
	}

	@Override
	@RequestMapping(value = AdmissionsRestDescriptor.EDIT_ADMISSION_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public AdmissionDto callEdit(@PathVariable(AdmissionsRestDescriptor.ADMISSION_ID_PARAM) int admissionId,
			@RequestBody AdmissionDiff admissionDiff) {
		Admission admission = admissionBean.edit(admissionId, admissionDiff);
		return AdmissionDto.valueOf(admission);
	}

	@Override
	public Admission admitPatient(AdmissionDiff admissionDiff) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public Admission edit(int admissionId, AdmissionDiff admissionDiff) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	AdmissionsControllerImpl(AdmissionsBean admissionBean) {
		this.admissionBean = admissionBean;
	}

	private final AdmissionsBean admissionBean;
}
