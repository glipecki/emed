package eu.anmore.emed.security;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for {@link PasswordGenerator}.
 * 
 * @author Grzegorz Lipecki
 */
public class PasswordGeneratorTest {

	@Test
	public void shouldGenerateSaltedHash() {
		logger.trace(">>> shouldGenerateSaltedHash");

		// given
		String testPassword = "password";
		String testSalt = "salt";
		PasswordGenerator passwordHashHenerator = new PasswordGenerator(testPassword, testSalt);

		// when
		String generatedHash = passwordHashHenerator.getHashedPassword();
		logger.debug("Generated hash: {}", generatedHash);

		// then
		assertTrue("Generator should generate not blank hash", StringUtils.isNotBlank(generatedHash));

		logger.trace("<<< shouldGenerateSaltedHash");
	}

	@Test
	public void shouldValidateSaltedHash() {
		logger.trace(">>> shouldValidateSaltedHash");

		// given
		String saltedHash = "c88e9c67041a74e0357befdff93f87dde0904214";
		String testPassword = "password";
		String testSalt = "salt";
		PasswordGenerator passwordHashHenerator = new PasswordGenerator(testPassword, testSalt);

		// when
		boolean isValid = passwordHashHenerator.isValidHash(saltedHash);

		// then
		assertTrue("Generator should correctly validate valid hash", isValid);

		logger.trace("<<< shouldValidateSaltedHash");
	}

	private static Logger logger = LoggerFactory.getLogger(PasswordGeneratorTest.class);

}
