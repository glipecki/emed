package eu.anmore.emed.observation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.anmore.emed.PersistenceInjector;

/**
 * @author glipecki
 */
@Configuration
public class MybatisDataObservationInjector implements DataObservationInjector {

	@Override
	@Bean
	public ObservationsDao getObservationsRepository() {
		return new ObservationsDaoImpl(observationMapper);
	}

	@Autowired
	PersistenceInjector persistenceInjector;

	@Autowired
	ObservationsMapper observationMapper;

}
