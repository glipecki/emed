package eu.anmore.emed.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.anmore.emed.PersistenceInjector;

/**
 * Mybatis version of {@link DataAuthenticationInjector}.
 * 
 * @author Grzegorz Lipecki
 */
@Configuration
public class MybatisDataAuthenticationInjector implements DataAuthenticationInjector {

	@Override
	@Bean
	public UsersDao getUserRepository() {
		return new UsersDaoImpl(userMapper);
	}

	@Autowired
	PersistenceInjector persistenceInjector;

	@Autowired
	UsersMapper userMapper;
}
