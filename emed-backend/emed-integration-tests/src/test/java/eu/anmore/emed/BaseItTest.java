package eu.anmore.emed;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationsRestDescriptor;
import eu.anmore.emed.observation.PatientObservationsDto;
import eu.anmore.emed.observation.PredefinedObservationType;

/**
 * Base class for Integration Tests.
 * 
 * @author glipecki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { RestWebMVCConfiguration.class })
@Transactional
public abstract class BaseItTest {

	/**
	 * Prepares environment for integration tests.
	 */
	@Before
	public void baseItTestSetUp() {
		mockMvc = MockMvcBuilders.webApplicationContextSetup(wac).build();
	}

	/**
	 * All mock objects should be wired into test.
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldWireMocks() throws Exception {
		assertThat(wac).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	protected static final String TEST_ENDS_LINE = "< ---";

	protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	protected static final String OBSERVER = "glipecki";

	protected byte[] convertObjectToJsonBytes(final Object object) throws IOException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		final String objectAsString = mapper.writeValueAsString(object);
		log.debug("JSON serialized object: {}", objectAsString);
		return mapper.writeValueAsBytes(object);
	}

	/**
	 * Gets mock of MVC controllers facade.
	 * 
	 * @return mocked MVC facade
	 */
	protected MockMvc getMockMvc() {
		return mockMvc;
	}

	protected void addObservation(final Integer patientId, final int year, final int month, final int day,
			final PredefinedObservationType type, final String value) throws Exception, IOException {
		// given
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		final Observation observationToAdd = new Observation(type.getKey(), calendar.getTime(), value);

		// when
		getMockMvc().perform(
				post(ObservationsRestDescriptor.getAddObservationPath()).contentType(APPLICATION_JSON_UTF8).body(
						convertObjectToJsonBytes(PatientObservationsDto.of(patientId, Arrays.asList(observationToAdd),
								OBSERVER))));
	}

	private static final Logger log = LoggerFactory.getLogger(BaseItTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

}
