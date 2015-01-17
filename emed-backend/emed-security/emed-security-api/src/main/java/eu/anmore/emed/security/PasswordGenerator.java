package eu.anmore.emed.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Password hash with salt generator.
 * <p>
 * Generates MD5 hashes. Can use salt value.
 * </p>
 * <p>
 * Possible refactoring: we could introduce PasswordGenerator interface, rename this class to
 * SaltedHashPasswordGenerator and implement PasswordGenerator interface. Next create
 * PasswordGeneratorFactory.getPasswordGenerator(User user) and inject factory to beans, instead of using
 * PasswordGenerator implementations directly.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public class PasswordGenerator {

	public PasswordGenerator(String password, String salt) {
		validateArguments(password);

		this.password = password;
		this.salt = salt != null ? salt : "";
	}

	public String getHashedPassword() {
		logger.debug("Get hash for: {}, salt: {}", password, !StringUtils.isBlank(salt) ? salt : "[no salt]");
		return DigestUtils.shaHex(password + salt);
	}

	public boolean isValidHash(String saltedHash) {
		return getHashedPassword().equals(saltedHash);
	}

	private void validateArguments(String password) {
		validatePasswordArgument(password);
	}

	private void validatePasswordArgument(String password) {
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Password has to have value");
		}
	}

	private static Logger logger = LoggerFactory.getLogger(PasswordGenerator.class);

	private final String password;

	private final String salt;

}
