package eu.anmore.emed.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Business authentication module injector.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
public class AuthenticationBusinessInjector {

	@Bean
	public AuthenticationBean getAuthenticationBean() {
		return new AuthenticationBeanImpl(dataAuthenticationInjector.getUserRepository());
	}

	@Autowired
	private DataAuthenticationInjector dataAuthenticationInjector;
}
