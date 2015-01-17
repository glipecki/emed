package eu.anmore.emed.patient;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

import eu.anmore.emed.BaseItTest;

/**
 * Tests for listing patients.
 * 
 * @author glipecki
 */
public class ListPatientsItTest extends BaseItTest {

	@Test
	public void shouldListAllPatients() throws Exception {
		LOGGER.trace("> PatientsController should return list of all patients");
		// given

		// when
		final MockHttpServletRequestBuilder serviceRequest = get(PatientsRestDescriptor.getPatientListRestUrl());
		final String serviceResult = getMockMvc().perform(serviceRequest).andReturn().getResponse().getContentAsString();

		// then
		assertThat(serviceResult).isNotEmpty();
		LOGGER.debug("Service response: {}", serviceResult);

		LOGGER.trace(TEST_ENDS_LINE);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ListPatientsItTest.class);
}
