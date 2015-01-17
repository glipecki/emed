package eu.anmore.emed;

/**
 * Spring Confiugration for servlet container.
 * 
 * @author Grzegorz Lipecki
 */
final class SpringWebContainerConfig {

	/** Spring Context component scan base package */
	static final String SCAN_PACKAGE = "eu.anmore.emed";

	static class DispatcherConfig {

		static class ServletConfig {

			static final String NAME = "spring-dispatcher";

			static final String MAPPING = "/";

		}

	}

	static class SecurityConfig {

		static class FilterConfig {

			static final String NAME = "springSecurity";

			static final String BEAN = "springSecurityFilterChain";

			static final String MAPPING = "/*";

		}

	}

	private SpringWebContainerConfig() {
		// intentionally left blank.
	}

}
