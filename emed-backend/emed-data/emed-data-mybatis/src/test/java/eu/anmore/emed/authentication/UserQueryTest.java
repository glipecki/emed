package eu.anmore.emed.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Tests for {@link UsersQuery}.
 * 
 * @author mmiedzinski
 */
public class UserQueryTest {

	@Test
	public void shouldBuildQuery() {
		// given
		final UsersQuery.UsersQueryBuilder queryBuilder = UsersQuery.getBuilder();

		// when
		final UsersQuery query = queryBuilder.idEquals(SAMPLE_ID).usernameEquals(SAMPLE_USERNAME)
				.passwordEquals(SAMPLE_PASSWORD_HASH).build();

		// then
		assertSame(SAMPLE_ID, query.getId());
		assertEquals(SAMPLE_USERNAME, query.getUsername());
		assertEquals(SAMPLE_PASSWORD_HASH, query.getPassword());
	}

	private static final int SAMPLE_ID = 3;

	private static final String SAMPLE_PASSWORD_HASH = "763946320743027432";

	private static final String SAMPLE_USERNAME = "username";
}
