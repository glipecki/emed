package eu.anmore.emed.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import eu.anmore.emed.tests.BaseMybatisTest;

/**
 * org.h2.tools.Server.startWebServer(getSqlSession().getConnection());
 * 
 * @author Grzegorz Lipecki
 */
public class UsersMapperTest extends BaseMybatisTest {

	@Before
	public void setup() {
		usersMapper = getSqlSession().getMapper(UsersMapper.class);
	}

	@Test
	public void shouldGetMapper() {
		// given

		// when
		final UsersMapper usersMapper = getSqlSession().getMapper(UsersMapper.class);

		// then
		assertNotNull(usersMapper);
	}

	@Test
	public void shouldQueryAllUsers() {
		// given

		// when
		final List<UserEntity> result = usersMapper.queryUsers(UsersQuery.getBuilder().build());

		// then
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	public void shouldQueryWithPermissions() {
		// given

		// when
		final List<UserEntity> result = usersMapper.queryUsers(UsersQuery.getBuilder().usernameEquals(TEST_USER)
				.build());

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		final UserEntity testUserEntity = result.get(0);
		assertEquals(2, testUserEntity.getPermissions().size());
	}

	@Override
	protected List<String> getBootrapScript() {
		return Arrays.asList("classpath:eu/anmore/emed/authentication/usersMapperTestData.sql");
	}

	@Override
	protected Resource[] getMappers() {
		return new Resource[] { new ClassPathResource("eu/anmore/emed/authentication/UsersMapper.xml") };
	}

	private static final String TEST_USER = "test-user";

	private UsersMapper usersMapper;

}
