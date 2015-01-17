package eu.anmore.emed.configuration;

import org.apache.commons.configuration.CombinedConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.anmore.commons.configuration.ConfigurationFactory;

/**
 * Configuration module injector.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
public class ConfigurationInjector {

	@Bean
	public CombinedConfiguration getApplicationConfiguration() {
		return ConfigurationFactory.createConfiguration(EMED_CONFIG_XML, EMED_CONFIG);
	}

	@Bean
	public ConfigurationFacade getApplicationConfigurationFacade() {
		return new ConfigurationFacade(getApplicationConfiguration());
	}

	private static final String EMED_CONFIG = "emed.resources";

	private static final String EMED_CONFIG_XML = "emed-config.xml";
}
