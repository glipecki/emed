package eu.anmore.emed.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rest patient module injector.
 * 
 * @author mmiedzinski
 */
@Configuration
public class PatientsRestInjector {

	@Bean
	public PatientsController getPatientController() {
		return new PatientsControllerImpl(patientBusinessInjector.getPatientBean());
	}

	@Autowired
	private PatientsBusinessInjector patientBusinessInjector;
}
