package eu.anmore.emed.patient;

import java.util.List;

/**
 * Implementation of {@link PatientsBean}.
 * 
 * @author mmiedzinski
 */
public class PatientsBeanImpl implements PatientsBean {

	@Override
	public List<Patient> getAll() {
		return patientRepository.getAll();
	}

	@Override
	public Patient edit(final int id, final PatientDiff changes) {
		patientRepository.edit(id, changes);
		return patientRepository.getById(id);
	}

	@Override
	public Patient insert(final PatientDiff patient) {
		return patientRepository.insert(patient);
	}

	@Override
	public Patient getPatientWithAdmissionList(final int patientId) {
		return patientRepository.getPatientWithAdmissionList(patientId);
	}

	@Override
	public List<Patient> getAdmitted() {
		return patientRepository.getAdmitted();
	}

	@Override
	public List<Patient> getNotAdmitted() {
		return patientRepository.getNotAdmitted();
	}

	@Override
	public List<Patient> getAdmittedPatientsWithAdmissions() {
		return patientRepository.getAdmittedPatientsWithAdmissions();
	}

	PatientsBeanImpl(final PatientsDao patientRepository) {
		this.patientRepository = patientRepository;
	}

	private final PatientsDao patientRepository;
}
