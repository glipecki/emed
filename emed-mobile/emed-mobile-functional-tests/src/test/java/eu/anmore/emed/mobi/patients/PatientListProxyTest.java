package eu.anmore.emed.mobi.patients;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import roboguice.RoboGuice;
import roboguice.test.RobolectricRoboTestRunner;

import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;

import eu.anmore.emed.mobi.MobiModule;

@RunWith(RobolectricRoboTestRunner.class)
public class PatientListProxyTest {

	@Before
	public void setup() {
		RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE,
				Modules.override(RoboGuice.newDefaultRoboModule(Robolectric.application)).with(new MobiModule()));

		patientListProxy = new PatientListProxy();
		patientListProxy.onCreate(null);
	}

	@Ignore
	@Test
	public void shouldInstantiateTextObject() {
		// given

		// when

		// then
		assertNotNull(patientListProxy);
	}

	private PatientListProxy patientListProxy;

}
