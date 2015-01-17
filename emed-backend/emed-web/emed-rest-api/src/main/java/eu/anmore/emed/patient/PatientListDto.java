package eu.anmore.emed.patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Transfer object grouping {@link PatientDto} objects.
 * 
 * @author mmiedzinski
 */
public class PatientListDto {

	/** */
	public static PatientListDto valueOf(final List<Patient> patients) {
		final PatientListDto patientListDto = new PatientListDto();
		final List<PatientDto> patientDtoList = new ArrayList<PatientDto>();
		for (final Patient patient : patients) {
			patientDtoList.add(PatientDto.valueOf(patient));
		}
		patientListDto.setPatients(patientDtoList);
		return patientListDto;
	}

	/** */
	public List<PatientDto> getPatients() {
		return patients;
	}

	/** */
	public void setPatients(final List<PatientDto> patients) {
		this.patients = patients;
	}

	/** */
	public List<Patient> asPatientList() {
		final List<Patient> patientsToReturn = new ArrayList<Patient>();
		for (final PatientDto patient : patients) {
			patientsToReturn.add(patient.asPatient());
		}
		return patientsToReturn;
	}

	private List<PatientDto> patients;
}
