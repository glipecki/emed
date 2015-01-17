package eu.anmore.emed.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Business patient module injector.
 * 
 * @author mmiedzinski
 */
@Configuration
public class PatientsBusinessInjector {

	/** */
	public PatientsBean getPatientBean() {
		return new PatientsBeanImpl(dataPatientInjector.getPatientRepository());
	}

	@Autowired
	private DataPatientInjector dataPatientInjector;
}
