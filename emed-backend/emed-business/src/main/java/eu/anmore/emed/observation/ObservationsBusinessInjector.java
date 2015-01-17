package eu.anmore.emed.observation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author glipecki
 */
@Configuration
public class ObservationsBusinessInjector {

	@Bean
	public ObservationsBean getObservationsBean() {
		return new ObservationsBeanImpl(dataObservationInjector.getObservationsRepository());
	}

	@Autowired
	private DataObservationInjector dataObservationInjector;

}
