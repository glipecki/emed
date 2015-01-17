package eu.anmore.emed.admission;


/**
 * Default implementation of {@link AdmissionsBean}.
 * 
 * @author mmiedzinski
 */
public class AdmissionsBeanImpl implements AdmissionsBean {

	@Override
	public Admission admitPatient(final AdmissionDiff admissionDiff) {
		return admissionRepository.admitPatient(admissionDiff);
	}

	@Override
	public Admission edit(final int admissionId, final AdmissionDiff admissionDiff) {
		return admissionRepository.edit(admissionId, admissionDiff);
	}

	AdmissionsBeanImpl(final AdmissionsDao admissionRepository) {
		this.admissionRepository = admissionRepository;
	}

	private final AdmissionsDao admissionRepository;
}
