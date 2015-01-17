package eu.anmore.emed.patient;

/**
 * Data Patient Injector.
 * 
 * @author mmiedzinski
 */
public interface DataPatientInjector {

	/**
	 * Get patient repository.
	 * 
	 * @return
	 */
	PatientsDao getPatientRepository();
}
