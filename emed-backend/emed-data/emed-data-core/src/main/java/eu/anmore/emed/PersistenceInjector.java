package eu.anmore.emed;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

import eu.anmore.emed.configuration.ConfigurationFacade;
import eu.anmore.emed.configuration.ConfigurationInjector;
import eu.anmore.emed.configuration.ConfigurationKeys.DB;

/**
 * Persistence configuration.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
@EnableTransactionManagement
public class PersistenceInjector {

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		loadDatabaseConfiguration();

		final BasicDataSource postgreSqlDataSource = new BasicDataSource();
		postgreSqlDataSource.setDriverClassName(databaseDriver);
		postgreSqlDataSource.setUrl(databaseUrl);
		postgreSqlDataSource.setUsername(databaseUsername);
		postgreSqlDataSource.setPassword(databasePassword);

		return postgreSqlDataSource;
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager getTransacationManager() {
		return new DataSourceTransactionManager(getDataSource());
	}

	private static final Logger log = LoggerFactory.getLogger(PersistenceInjector.class);

	private void loadDatabaseConfiguration() {
		final ConfigurationFacade config = configurationInjector.getApplicationConfigurationFacade();

		databaseDriver = config.getDatabaeDriver();
		databaseUrl = config.getDatabaseUrl();
		databaseUsername = config.getDatabaseUsername();
		databasePassword = config.getDatabasePassword();
		databaseMode = config.getDatabaseMode();

		Preconditions.checkNotNull(databaseDriver, "Database driver must not be null");
		Preconditions.checkNotNull(databaseUrl, "Database connection url must not be null");
		Preconditions.checkNotNull(databaseUsername, "Database username must not be null");
		Preconditions.checkNotNull(databasePassword, "Database password for username must not be null");

		printDatabaseConfiguration();
	}

	private void printDatabaseConfiguration() {
		log.debug("> Database configuration:");
		log.debug("- Driver: {}", databaseDriver);
		log.debug("- URL: {}", databaseUrl);
		log.debug("- Username: {}", databaseUsername);
		log.debug("- Mode: {}", DB.MODES.DEMO.equals(databaseMode) ? DB.MODES.DEMO : DB.MODES.PRODUCTION);
		log.debug("< database configuration.");
	}

	private String databaseMode;

	@Autowired
	private ConfigurationInjector configurationInjector;

	@Autowired
	private Environment env;

	private String databaseDriver;

	private String databaseUrl;

	private String databaseUsername;

	private String databasePassword;

}
