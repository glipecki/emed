package eu.anmore.emed.observation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Injector for Observations REST service.
 * 
 * @author glipecki
 * 
 */
@Configuration
public class ObservationsRestInjector {

	@Bean
	public ObservationsController getObservationsController() {
		return new ObservationsControllerImpl(observationsBusinessInjector.getObservationsBean());
	}

	@Autowired
	private ObservationsBusinessInjector observationsBusinessInjector;

}
