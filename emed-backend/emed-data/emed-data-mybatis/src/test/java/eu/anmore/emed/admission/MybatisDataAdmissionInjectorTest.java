package eu.anmore.emed.admission;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class {@link MybatisDataAdmissionInjector}.
 * 
 * @author mmiedzinski
 */
public class MybatisDataAdmissionInjectorTest {

	@Before
	public void setUp() {
		injector = new MybatisDataAdmissionInjector();
	}

	@Test
	public void shouldReturnAdmissionRepository() {
		// given

		// when
		final AdmissionsDao repository = injector.getAdmissionRepository();

		// then
		assertNotNull(repository);
	}

	private DataAdmissionInjector injector;
}
