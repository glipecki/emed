package eu.anmore.emed.authentication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * HTTP Request Factory which uses HTTP Basic Authorization.
 * 
 * @author Grzegorz Lipecki
 */
public class BasicAuthorizedHttpRequestFactory extends SimpleClientHttpRequestFactory {

	public BasicAuthorizedHttpRequestFactory(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	protected void prepareConnection(final HttpURLConnection connection, final String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);

		final String encodedAuthorisation = encodeAuthorisation(username, password);
		connection.setRequestProperty(HTTP_HEADER_AUTHENTICATION,
				String.format(HTTP_HEADER_BASIC_AUTH_PATTERN, encodedAuthorisation));
	}

	private String encodeAuthorisation(final String username, final String password) {
		final String authorisation = username + ":" + password;
		return new String(Base64.encodeBase64(authorisation.getBytes()), Charset.forName(CHARSET_UTF_8));
	}

	// charset name is specified by RFC 2279, "http://www.ietf.org/rfc/rfc2279.txt"
	private static final String CHARSET_UTF_8 = "UTF-8";

	private static final String HTTP_HEADER_AUTHENTICATION = "Authorization";

	private static final String HTTP_HEADER_BASIC_AUTH_PATTERN = "Basic %s";

	private final String username;

	private final String password;

}
