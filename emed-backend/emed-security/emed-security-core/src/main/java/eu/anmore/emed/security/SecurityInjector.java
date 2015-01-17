package eu.anmore.emed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import eu.anmore.emed.authentication.AuthenticationBusinessInjector;

/**
 * WebApp security injector.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
@ImportResource(value = { "classpath:appCtx-security.xml" })
public class SecurityInjector {

	@Bean(name = "authenticationProvider")
	org.springframework.security.authentication.AuthenticationProvider getAuthenticationProvider() {
		return new DatabaseAuthenticationProvider(authenticationBusinessInjector.getAuthenticationBean());
	}

	@Autowired
	private AuthenticationBusinessInjector authenticationBusinessInjector;


}
