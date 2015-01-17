package eu.anmore.emed;

import static eu.anmore.emed.SpringWebContainerConfig.SCAN_PACKAGE;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * J2EE web application configuration.
 * <p>
 * Since javax.servlet version 3.0 we can skip web.xml file and provide configuration in Java class implementing
 * {@link WebApplicationInitializer}. J2EE container will look for all implementations of
 * {@link WebApplicationInitializer} and use it as web application descriptor.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public class WebApp implements WebApplicationInitializer {

	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		final AnnotationConfigWebApplicationContext springContext = createSpringContext();
		attachDispatcherMapping(servletContext, springContext);
		// attachSecurityFilter(servletContext, springContext);
		attachContextLoaderListener(servletContext, springContext);
	}

	private AnnotationConfigWebApplicationContext createSpringContext() {
		rootContext = new AnnotationConfigWebApplicationContext();
		registerSpringConfigurations();
		return rootContext;
	}

	private void attachDispatcherMapping(final ServletContext servletContext,
			final AnnotationConfigWebApplicationContext springContext) {
		final ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				SpringWebContainerConfig.DispatcherConfig.ServletConfig.NAME, new DispatcherServlet(springContext));
		dispatcher.setLoadOnStartup(DISPATCHER_PRIORITY);
		dispatcher.addMapping(SpringWebContainerConfig.DispatcherConfig.ServletConfig.MAPPING);
	}

	private void attachSecurityFilter(final ServletContext servletContext,
			final AnnotationConfigWebApplicationContext springContext) {
		servletContext.addFilter(SpringWebContainerConfig.SecurityConfig.FilterConfig.NAME,
				new DelegatingFilterProxy(SpringWebContainerConfig.SecurityConfig.FilterConfig.BEAN))
				.addMappingForUrlPatterns(null, false, SpringWebContainerConfig.SecurityConfig.FilterConfig.MAPPING);
	}

	private void attachContextLoaderListener(final ServletContext servletContext,
			final AnnotationConfigWebApplicationContext springContext) {
		servletContext.addListener(new ContextLoaderListener(springContext));
	}

	private void registerSpringConfigurations() {
		rootContext.scan(SCAN_PACKAGE);
	}

	private static final int DISPATCHER_PRIORITY = 1;

	private AnnotationConfigWebApplicationContext rootContext;

}
