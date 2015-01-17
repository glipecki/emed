package eu.anmore.emed.patient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link PatientsDao}.
 * 
 * @author mmiedzinski
 */
public class PatientsDaoImpl implements PatientsDao {

	public PatientsDaoImpl(final PatientsMapper patientMapper) {
		this.patientMapper = patientMapper;
	}

	@Override
	@Transactional
	public List<Patient> getAll() {
		final List<PatientEntity> entities = patientMapper.queryPatients(PatientsQuery.getBuilder()
				.orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME).orderBy(PatientEntity.SURNAME_COLUMN_NAME).build());
		return convertToPatientList(entities);
	}

	@Override
	@Transactional
	public void edit(final int id, final PatientDiff patientDiff) {
		patientMapper.edit(EditPatientCommand.get(id, patientDiff));
	}

	@Override
	@Transactional
	public Patient getById(final int id) {
		final PatientEntity entity = patientMapper.queryPatients(PatientsQuery.getBuilder().idEquals(id).build())
				.get(0);
		return entity.getPatient();
	}

	@Override
	@Transactional
	public Patient insert(final PatientDiff patient) {
		PatientEntity entity = getPatientEntityFromPatientDiff(patient);
		patientMapper.insert(entity);
		return entity.getPatient();
	}

	@Override
	@Transactional
	public Patient getPatientWithAdmissionList(int id) {
		PatientsQuery patientsQuery = PatientsQuery.getBuilder().idEquals(id).build();
		PatientEntity patientWithAdmissionList = patientMapper.queryPatientsWithAdmissions(patientsQuery).get(0);
		return patientWithAdmissionList.getPatient();
	}

	@Override
	@Transactional
	public List<Patient> getAdmitted() {
		final List<PatientEntity> entities = patientMapper.queryAdmittedPatients(PatientsQuery.getBuilder()
				.orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME).orderBy(PatientEntity.SURNAME_COLUMN_NAME).build());
		return convertToPatientList(entities);
	}

	@Override
	@Transactional
	public List<Patient> getNotAdmitted() {
		final List<PatientEntity> entities = patientMapper.queryNotAdmittedPatients(PatientsQuery.getBuilder()
				.orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME).orderBy(PatientEntity.SURNAME_COLUMN_NAME).build());
		return convertToPatientList(entities);
	}

	@Override
	public List<Patient> getAdmittedPatientsWithAdmissions() {
		final List<PatientEntity> entities = patientMapper.queryAdmittedPatientWithAdmissions(PatientsQuery
				.getBuilder().orderBy(PatientEntity.FIRSTNAME_COLUMN_NAME).orderBy(PatientEntity.SURNAME_COLUMN_NAME)
				.build());
		return convertToPatientList(entities);
	}

	private PatientEntity getPatientEntityFromPatientDiff(PatientDiff patientDiff) {
		return new PatientEntity(patientDiff.name.getNewValue(), patientDiff.surname.getNewValue(),
				patientDiff.pesel.getNewValue(), patientDiff.birthday.getNewValue(), patientDiff.sex.getNewValue(),
				null);
	}

	private List<Patient> convertToPatientList(final List<PatientEntity> entities) {
		final List<Patient> result = new ArrayList<Patient>();
		for (final PatientEntity patientEntity : entities) {
			result.add(patientEntity.getPatient());
		}
		return result;
	}

	/** Just to fulfill CGLIB requirements */
	PatientsDaoImpl() {
		patientMapper = null;
	}

	private final PatientsMapper patientMapper;
}
