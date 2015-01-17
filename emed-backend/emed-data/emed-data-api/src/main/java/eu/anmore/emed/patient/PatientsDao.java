package eu.anmore.emed.patient;

import java.util.List;

/**
 * Patients repository.
 * 
 * @author mmiedzinski
 */
public interface PatientsDao {

	/**
	 * Get list of all patients.
	 * 
	 * @return list of all patients
	 */
	List<Patient> getAll();

	/**
	 * Get Patient by id.
	 * 
	 * @param id
	 *            the id of patient
	 * @return the Patient object.
	 */
	Patient getById(int id);

	/**
	 * Edit patient.
	 * 
	 * @param id
	 *            the id of patient
	 * @param patient
	 *            the diff object
	 */
	void edit(int id, PatientDiff patient);

	/**
	 * Insert patient into system.
	 * 
	 * @param patient
	 *            the patient to insert
	 * @return the inserted object with id
	 */
	Patient insert(PatientDiff patient);

	/**
	 * Get Patient with full information about admissions.
	 * 
	 * @param id
	 *            the id of patient
	 * @return
	 */
	Patient getPatientWithAdmissionList(int id);

	/**
	 * Get list of admitted patients.
	 * 
	 * @return list of admitted patients
	 */
	List<Patient> getAdmitted();

	/**
	 * Get list of not admitted patients.
	 * 
	 * @return list of not admitted patients
	 */
	List<Patient> getNotAdmitted();

	/**
	 * Get patient list with admissions.
	 * 
	 * @return list of admitted patients with admissions
	 */
	List<Patient> getAdmittedPatientsWithAdmissions();
}
