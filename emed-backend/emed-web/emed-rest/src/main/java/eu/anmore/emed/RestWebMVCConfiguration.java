package eu.anmore.emed;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Spring MVC Rest configuration.
 * 
 * <p>
 * Scans all classes available on SpringWebContainerConfig.SCAN_PACKAGE package
 * looking for rest controllers.
 * </p>
 * <p>
 * Attaches "/res/**" url to static resource handler (omiting controllers
 * dispatcher).
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
@ComponentScan(basePackages = { SpringWebContainerConfig.SCAN_PACKAGE })
@EnableWebMvc
public class RestWebMVCConfiguration extends WebMvcConfigurationSupport {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/res/**").addResourceLocations("/res/");
	}

}
