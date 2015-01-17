package eu.anmore.emed.configuration;

import org.apache.commons.configuration.CombinedConfiguration;

import eu.anmore.emed.configuration.ConfigurationKeys.DB;

public class ConfigurationFacade {

	public ConfigurationFacade(final CombinedConfiguration config) {
		this.config = config;
	}

	public String getDatabaseMode() {
		return config.getString(DB.MODE);
	}

	public String getDatabasePassword() {
		return config.getString(DB.PASSWORd);
	}

	public String getDatabaseUsername() {
		return config.getString(DB.USERNAME);
	}

	public boolean isDatabaseDemo() {
		return DB.MODES.DEMO.equals(config.getString(ConfigurationKeys.DB.MODE));
	}

	public String getDatabaseUrl() {
		return config.getString(DB.URL);
	}

	public String getDatabaeDriver() {
		return config.getString(DB.DRIVER);
	}

	private final CombinedConfiguration config;

}
