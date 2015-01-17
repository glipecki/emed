package eu.anmore.emed;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import eu.anmore.emed.authentication.BasicAuthorizedHttpRequestFactory;
import eu.anmore.emed.authentication.RestServerUnavailableException;

/**
 * Base class for Rest connetors.
 * 
 * @author mmiedzinski
 */
public abstract class BaseServiceRestConnector {

	public BaseServiceRestConnector() {
		this.restTemplate = initRestTemplate(new RestTemplate());
	}

	public BaseServiceRestConnector(final String username, final String password) {
		this.restTemplate = initRestTemplate(username, password);
	}

	protected RestTemplate restTemplate;

	protected void handleConnectionError(final RestClientException restClientException) {
		if (restClientException instanceof HttpStatusCodeException) {
			final HttpStatusCodeException httpStatusCodeException = ((HttpStatusCodeException) restClientException);
			throw new RestServerUnavailableException(httpStatusCodeException);
		} else if (restClientException instanceof ResourceAccessException) {
			throw new RestServerUnavailableException(restClientException);
		}
	}

	private RestTemplate initRestTemplate(final RestTemplate restTemplate) {
		restTemplate.getMessageConverters().clear();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(1000);
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(1000);
		return restTemplate;
	}

	private RestTemplate initRestTemplate(final String username, final String password) {
		final SimpleClientHttpRequestFactory basicAuthenticatedHttpClientFactory = new BasicAuthorizedHttpRequestFactory(
				username, password);
		final RestTemplate restTemplate = new RestTemplate(basicAuthenticatedHttpClientFactory);
		return initRestTemplate(restTemplate);
	}
}
