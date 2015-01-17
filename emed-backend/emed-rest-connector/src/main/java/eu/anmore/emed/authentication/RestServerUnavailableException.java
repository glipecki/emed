package eu.anmore.emed.authentication;

import org.springframework.web.client.RestClientException;

/**
 * REST server is unavailable.
 * <p>
 * REST server refuses to accept connection.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public class RestServerUnavailableException extends RuntimeException {

	public RestServerUnavailableException(
			final RestClientException restClientException) {
		super(restClientException);
	}

	private static final long serialVersionUID = 254197737377161647L;

}
