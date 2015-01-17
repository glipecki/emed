package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.List;

public class AdmissionObservationTypeDto {

	public AdmissionObservationTypeDto() {
		super();
	}

	public AdmissionObservationTypeDto(final List<AdmissionObservationType> admissionTypes) {
		super();
		this.admissionTypes = admissionTypes;
	}

	public void setAdmissionTypes(final List<AdmissionObservationType> admissionTypes) {
		this.admissionTypes = admissionTypes;
	}

	public List<AdmissionObservationType> getAdmissionTypes() {
		return admissionTypes;
	}

	@Override
	public String toString() {
		return String.format("AdmissionObservationTypeDto [admissionTypes=%s]", admissionTypes);
	}

	private List<AdmissionObservationType> admissionTypes = new ArrayList<AdmissionObservationType>();

}
