package eu.anmore.emed.admission;

/**
 * Admissions repository.
 * 
 * @author mmiedzinski
 */
public interface AdmissionsDao {

	/**
	 * Admit patient to ward.
	 * 
	 * @param admissionDiff
	 *            the admission to insert.
	 * @return
	 */
	Admission admitPatient(AdmissionDiff admissionDiff);

	/**
	 * Edit admission by id.
	 * 
	 * @param admissionId
	 *            the admission id
	 * @param admissionDiff
	 *            the admission changes object
	 * @return the updated object
	 */
	Admission edit(int admissionId, AdmissionDiff admissionDiff);
}
