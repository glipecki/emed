package eu.anmore.emed.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import eu.anmore.emed.authentication.AuthenticationBean;
import eu.anmore.emed.authentication.User;
import eu.anmore.emed.authentication.UserBadCredentialsException;
import eu.anmore.emed.authentication.UserNotFoundException;

/**
 * Authentication provider against database.
 * 
 * @author Grzegorz Lipecki
 */
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

	@Override
	public boolean supports(final Class<?> authenticationClass) {
		return SUPPORTED_AUTHENTICATION_CLASSES.contains(authenticationClass);
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final User user = getAuthenticatedUserAndHandleExceptions(authentication);
		validateUserAuthentication(user);
		return getAuthenticationToken(user);
	}

	DatabaseAuthenticationProvider(final AuthenticationBean authenticationBean) {
		this.authenticationBean = authenticationBean;
	}

	private static final List<Class<?>> SUPPORTED_AUTHENTICATION_CLASSES = new ArrayList<Class<?>>() {
		{
			add(UsernamePasswordAuthenticationToken.class);
		}
		private static final long serialVersionUID = -6529814132772273693L;
	};

	private User getAuthenticatedUserAndHandleExceptions(final Authentication authentication) {
		try {
			return getAuthenticatedUser((UsernamePasswordAuthenticationToken) authentication);
		} catch (final UserNotFoundException userNotFoundException) {
			throw new AuthenticationCredentialsNotFoundException("User not found", userNotFoundException);
		} catch (final UserBadCredentialsException userBadCredentialsException) {
			throw new BadCredentialsException("User bad credentials", userBadCredentialsException);
		}
	}

	private User getAuthenticatedUser(final UsernamePasswordAuthenticationToken authentication)
			throws UserNotFoundException, UserBadCredentialsException {
		final String username = String.valueOf(authentication.getPrincipal());
		final String password = String.valueOf(authentication.getCredentials());
		return authenticationBean.authenticate(username, password);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(final User user) {
		final List<GrantedAuthority> grantedAuthorities = getUserAuthorities(user);
		final UserPrincipal userPrincipal = UserPrincipal.valueOf(user);
		return new UsernamePasswordAuthenticationToken(userPrincipal, null, grantedAuthorities);
	}

	private List<GrantedAuthority> getUserAuthorities(final User user) {
		final GrantedAuthoritiesResolver authoritiesResolver = new GrantedAuthoritiesResolver(user);
		return authoritiesResolver.getGrantedAuthorities();
	}

	/**
	 * Validates user authentication and throws
	 * InsufficientAuthenticationException at any validation error.
	 * 
	 * @param user
	 */
	private void validateUserAuthentication(final User user) {
		final UserAuthenticationValidator userAuthenticationValidator = new UserAuthenticationValidator(user);
		if (!userAuthenticationValidator.isValid()) {
			throw new AuthenticationValidationException(String.format("User not valid to authenticate. %s",
					userAuthenticationValidator.getValidationMessages()));
		}
	}

	private final AuthenticationBean authenticationBean;

}
