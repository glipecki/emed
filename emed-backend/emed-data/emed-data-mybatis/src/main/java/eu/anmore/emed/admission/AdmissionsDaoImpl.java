package eu.anmore.emed.admission;

/**
 * Implementation of {@link AdmissionsDao}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsDaoImpl implements AdmissionsDao {

	public AdmissionsDaoImpl(final AdmissionsMapper admissionsMapper) {
		this.admissionsMapper = admissionsMapper;
	}

	@Override
	public Admission admitPatient(AdmissionDiff admissionDiff) {
		AdmissionEntity admissionEntity = AdmissionEntity.valueOf(admissionDiff);
		admissionsMapper.admitPatient(admissionEntity);
		return admissionEntity.getAdmission();
	}

	@Override
	public Admission edit(int admissionId, AdmissionDiff admissionDiff) {
		admissionsMapper.edit(new EditAdmissionCommand(admissionId, admissionDiff));
		AdmissionEntity updatedAdmission = admissionsMapper.getAdmissionById(admissionId);
		return updatedAdmission.getAdmission();
	}

	/** Just to fulfill CGLIB requirements */
	AdmissionsDaoImpl() {
		admissionsMapper = null;
	}

	private AdmissionsMapper admissionsMapper;
}
