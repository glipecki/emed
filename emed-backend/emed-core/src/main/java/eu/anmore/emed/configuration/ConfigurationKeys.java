package eu.anmore.emed.configuration;

public final class ConfigurationKeys {

	public static final String SEPARATOR = ".";

	public interface DB {

		static final String KEY = "database";

		static final String DRIVER = KEY + SEPARATOR + "driver";

		static final String URL = KEY + SEPARATOR + "url";

		static final String USERNAME = KEY + SEPARATOR + "username";

		static final String PASSWORd = KEY + SEPARATOR + "password";

		static final String MODE = KEY + SEPARATOR + "mode";

		interface MODES {

			static final String DEMO = "demo";

			static final String PRODUCTION = "production";

		}

	}

	private ConfigurationKeys() {
	}

}
