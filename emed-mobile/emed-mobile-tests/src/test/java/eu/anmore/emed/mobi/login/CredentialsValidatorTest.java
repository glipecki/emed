package eu.anmore.emed.mobi.login;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import eu.anmore.emed.mobi.login.credentials.CredentialsValidator;
import eu.anmore.emed.mobi.login.credentials.ValidationError;

public class CredentialsValidatorTest {

	@Test
	public void shouldValidateNonEmptyUsernameForInvalidData() {
		// given
		String username = "";
		String password = "valid";

		// when
		List<ValidationError> result = CredentialsValidator.get(username, password).nonEmptyUsername().validate();

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(ValidationError.EMPTY_USERNAME, result.get(0));
	}

	@Test
	public void shouldValidateNonEmptyUsernameForValidData() {
		// given
		String username = "valid";
		String password = "valid";

		// when
		List<ValidationError> result = CredentialsValidator.get(username, password).nonEmptyUsername().validate();

		// then
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	public void shouldValidateNonEmptyPasswordForInvalidData() {
		// given
		String username = "valid";
		String password = "";

		// when
		List<ValidationError> result = CredentialsValidator.get(username, password).nonEmptyPassword().validate();

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(ValidationError.EMPTY_PASSWORD, result.get(0));
	}

	@Test
	public void shouldValidateNonEmptyPasswordForValidData() {
		// given
		String username = "valid";
		String password = "valid";

		// when
		List<ValidationError> result = CredentialsValidator.get(username, password).nonEmptyPassword().validate();

		// then
		assertNotNull(result);
		assertEquals(0, result.size());
	}

}
