package eu.anmore.emed;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.googlecode.flyway.core.Flyway;

import eu.anmore.emed.configuration.ConfigurationInjector;

/**
 * MyBatis data layer configuration.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
@Import(value = { PersistenceInjector.class })
@ImportResource(value = { "classpath:appCtx-mybatis-mappers.xml" })
public class MybatisInjector {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(persistenceInjector.getDataSource());
		return sqlSessionFactoryBean;
	}

	@Bean(name = "dbMigrations")
	public String dbMigrations() {
		log.info("> Migrating database");

		final Flyway flyway = new Flyway();
		flyway.setDataSource(persistenceInjector.getDataSource());
		if (configurationInjector.getApplicationConfigurationFacade().isDatabaseDemo()) {
			log.debug("Migrate demo data");
			flyway.setLocations("db.migration", "db.demo_data");
		}
		flyway.migrate();
		final String currentVersion = flyway.info().current().getVersion().toString();

		log.info("< current database version: {}", currentVersion);
		return currentVersion;
	}

	private static final Logger log = LoggerFactory.getLogger(MybatisInjector.class);

	@Autowired
	private PersistenceInjector persistenceInjector;

	@Autowired
	private ConfigurationInjector configurationInjector;

}
