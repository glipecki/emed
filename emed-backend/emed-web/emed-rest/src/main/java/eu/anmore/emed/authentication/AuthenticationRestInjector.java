package eu.anmore.emed.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.anmore.emed.configuration.ConfigurationInjector;

/**
 * Rest authentication module injector.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
public class AuthenticationRestInjector {

	@Bean
	public AuthenticationController getAuthenticationController() {
		return new AuthenticationControllerImpl(configurationInjector.getApplicationConfiguration(),
				authenticationBusinessInjector.getAuthenticationBean());
	}

	@Autowired
	private AuthenticationBusinessInjector authenticationBusinessInjector;

	@Autowired
	private ConfigurationInjector configurationInjector;
}
