package eu.anmore.emed.patient;

import java.util.List;

/**
 * Patient SQL mapper.
 * 
 * @author mmiedzinski
 * @author Grzegorz Lipecki
 */
public interface PatientsMapper {

	/**
	 * Query all patients by {@link PatientsQuery}.
	 * 
	 * @param query
	 * @return
	 */
	List<PatientEntity> queryPatients(PatientsQuery query);

	/**
	 * Query admitted patients by {@link PatientsQuery}.
	 * 
	 * @param query
	 * @return
	 */
	List<PatientEntity> queryAdmittedPatients(PatientsQuery query);

	/**
	 * Query not admitted patients by {@link PatientsQuery}.
	 * 
	 * @param query
	 * @return
	 */
	List<PatientEntity> queryNotAdmittedPatients(PatientsQuery query);

	/**
	 * Query all patients with data about admissions by {@link PatientsQuery}.
	 * 
	 * @param query
	 * @return
	 */
	List<PatientEntity> queryPatientsWithAdmissions(PatientsQuery query);

	/**
	 * Query admitted patients with data about admissions by
	 * {@link PatientsQuery}.
	 * 
	 * @param query
	 * @return
	 */
	List<PatientEntity> queryAdmittedPatientWithAdmissions(PatientsQuery query);

	/**
	 * Edit patient by {@link EditPatientCommand}.
	 * 
	 * @param query
	 * @return
	 */
	void edit(EditPatientCommand editPatientCommand);

	/**
	 * Insert new Patient into system.
	 * 
	 * @param patientEntity
	 */
	void insert(PatientEntity patientEntity);
}
