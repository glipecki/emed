package eu.anmore.emed.admission;

/**
 * Admission SQL mapper.
 * 
 * @author mmiedzinski
 */
public interface AdmissionsMapper {

	/**
	 * Admit patient to ward.
	 * 
	 * @param admissionEntity
	 */
	int admitPatient(AdmissionEntity admissionEntity);

	/**
	 * Edit admission.
	 * 
	 * @param editAdmissionCommand
	 *            the admission edit command.
	 */
	void edit(EditAdmissionCommand editAdmissionCommand);

	/**
	 * Select patient by id.
	 * 
	 * @param id
	 *            the id of patient
	 * @return
	 */
	AdmissionEntity getAdmissionById(int id);
}
