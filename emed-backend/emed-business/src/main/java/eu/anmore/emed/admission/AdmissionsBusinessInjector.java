package eu.anmore.emed.admission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Business patient module injector.
 * 
 * @author mmiedzinski
 */
@Configuration
public class AdmissionsBusinessInjector {

	public AdmissionsBean getAdmissionBean() {
		return new AdmissionsBeanImpl(dataAdmissionInjector.getAdmissionRepository());
	}

	@Autowired
	private DataAdmissionInjector dataAdmissionInjector;
}
