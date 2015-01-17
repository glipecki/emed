package eu.anmore.emed.admission;

public interface Admissions {

	/**
	 * Admit patient to ward.
	 * 
	 * @param admission
	 *            the admission to insert.
	 * @return
	 */
	Admission admitPatient(AdmissionDiff admission);

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
