package eu.anmore.emed.patient;

import java.util.List;

/**
 * Patients API.
 * <p>
 * Unified interface for Beans, Controllers, Connectors.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public interface Patients {

	/**
	 * Patient list.
	 * 
	 * @return
	 */
	List<Patient> getAll();

	/**
	 * Edit patient.
	 * 
	 * @param id
	 *            the id of patient
	 * @param changes
	 *            the changes object
	 * @return the patient edited object
	 */
	Patient edit(int id, PatientDiff changes);

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
	 * @param patientId
	 *            the id of patient
	 * @return
	 */
	Patient getPatientWithAdmissionList(int patientId);

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
