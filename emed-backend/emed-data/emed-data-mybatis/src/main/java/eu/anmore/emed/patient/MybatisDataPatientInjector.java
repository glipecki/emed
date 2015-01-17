package eu.anmore.emed.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.anmore.emed.PersistenceInjector;

/**
 * Mybatis version of {@link DataPatientInjector}.
 * 
 * @author mmiedzinski
 */
@Configuration
public class MybatisDataPatientInjector implements DataPatientInjector {

	@Override
	@Bean
	public PatientsDao getPatientRepository() {
		return new PatientsDaoImpl(patientsMapper);
	}

	@Autowired
	PersistenceInjector persistenceInjector;

	@Autowired
	PatientsMapper patientsMapper;
}
