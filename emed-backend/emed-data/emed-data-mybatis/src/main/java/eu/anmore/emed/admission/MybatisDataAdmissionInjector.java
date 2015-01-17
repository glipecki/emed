package eu.anmore.emed.admission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis version of {@link DataAdmissionInjector}.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
public class MybatisDataAdmissionInjector implements DataAdmissionInjector {

	@Override
	public AdmissionsDao getAdmissionRepository() {
		return new AdmissionsDaoImpl(admissionsMapper);
	}

	@Autowired
	private AdmissionsMapper admissionsMapper;
}
