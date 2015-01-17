package eu.anmore.emed.admission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rest patient module injector.
 * 
 * @author mmiedzinski
 */
@Configuration
public class AdmissionsRestInjector {

	@Bean
	public AdmissionsController getAdmissionsController() {
		return new AdmissionsControllerImpl(admissionsBusinessInjector.getAdmissionBean());
	}

	@Autowired
	private AdmissionsBusinessInjector admissionsBusinessInjector;
}
