package eu.anmore.emed.tests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author Grzegorz Lipecki
 */
public abstract class BaseMybatisTest {

	@Before
	public void baseSetup() throws Exception {
		final EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2);
		databaseBuilder.addScript("bootsrap.sql");
		for (final String scriptFile : getBootrapScript()) {
			databaseBuilder.addScript(scriptFile);
		}
		dataSource = databaseBuilder.build();

		final Resource[] mappers = getMappers();

		sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(mappers);
		sqlSessionFactoryBean.getObject().getConfiguration().setCacheEnabled(false);

		sqlSession = sqlSessionFactoryBean.getObject().openSession();
	}

	@After
	public void baseTeardown() {
		sqlSession.close();
		dataSource.shutdown();
	}

	@Test
	public void shouldCreateSqlSessionFactoryBean() throws Exception {
		// given

		// when
		final SqlSessionFactory sqlSesionFactory = sqlSessionFactoryBean.getObject();

		// then
		assertNotNull(sqlSesionFactory);
	}

	protected SqlSession getSqlSession() {
		return sqlSession;
	}

	protected abstract Resource[] getMappers();

	protected abstract List<String> getBootrapScript();

	private EmbeddedDatabase dataSource;

	private SqlSessionFactoryBean sqlSessionFactoryBean;

	private SqlSession sqlSession;

}
