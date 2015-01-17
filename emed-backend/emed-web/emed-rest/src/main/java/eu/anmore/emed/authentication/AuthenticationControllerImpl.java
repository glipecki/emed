package eu.anmore.emed.authentication;

import java.io.IOException;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.CombinedConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.anmore.emed.security.UserPrincipal;

/**
 * @author Grzegorz Lipecki
 */
@RequestMapping(AuthenticationRestDescriptor.ROOT_URI)
public class AuthenticationControllerImpl implements AuthenticationController {

	/** Just to fulfill CGLIB requirements */
	public AuthenticationControllerImpl() {
		authenticationBean = null;
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.AUTHENTICATE_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UserDto callAuthenticate(
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username,
			@RequestBody String password) throws UserNotFoundException, UserBadCredentialsException,
			InvalidAuthenticationRequestException {
		password = extractPasswordFromJson(password);
		// if (!validateRequestAgainsSecurityContext(username)) {
		// throw new InvalidAuthenticationRequestException(
		// "Username from request dosen't match username from security context");
		// }
		return UserDto.valueOf(authenticationBean.authenticate(username, password));
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.GET_USERS_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public UserListDto callGetUsers() {
		final List<User> users = authenticationBean.getUsers();
		return UserListDto.valueOf(users);
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.ADD_USER_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UserDto callAddUser(@RequestBody final UserDiff userDiff) {
		return UserDto.valueOf(authenticationBean.addUser(userDiff));
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.EDIT_USER_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UserDto callEditUser(@RequestBody final UserDiff userDiff) throws UserNotFoundException {
		return UserDto.valueOf(authenticationBean.editUser(userDiff));
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.IS_UNIQUE_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public boolean isUnique(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username) {
		return authenticationBean.isUnique(username);
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.BLOCK_USER_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public boolean blockUser(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username) {
		return authenticationBean.blockUser(username);
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.UNBLOCK_USER_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public boolean unblockUser(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username) {
		return authenticationBean.unblockUser(username);
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.ADD_PERMISSION_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public boolean addPermission(
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username,
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_PERMISSION_PARAM) final String permission) {
		return authenticationBean.addPermission(username, permission);
	}

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.REMOVE_PERMISSION_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	public boolean removePermission(
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username,
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_PERMISSION_PARAM) final String permission) {
		return authenticationBean.removePermission(username, permission);
	}

	@ExceptionHandler(value = { UserNotFoundException.class, UserBadCredentialsException.class })
	@ResponseBody
	public String handleUserNotFoundException(final Exception ex, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		response.setStatus(AuthenticationRestDescriptor.INVALID_CREDENTIALS);
		return "user not found!";
	}

	@Override
	public User authenticate(final String username, final String password) throws UserNotFoundException {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<User> getUsers() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public User addUser(final UserDiff userDiff) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public User editUser(final UserDiff userDiff) throws UserNotFoundException {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	AuthenticationControllerImpl(final CombinedConfiguration configuration, final AuthenticationBean authenticationBean) {
		// this.configuration = configuration;
		this.authenticationBean = authenticationBean;
	}

	private boolean validateRequestAgainsSecurityContext(final String requestUsername) {
		final UserPrincipal authenticatedUserPrincipal = getAuthenticatedUserPrincipal();
		return requestUsername.equals(authenticatedUserPrincipal.getUsername());
	}

	private UserPrincipal getAuthenticatedUserPrincipal() {
		final UserPrincipal authenticatedUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return authenticatedUser;
	}

	private String extractPasswordFromJson(String password) {
		password = password.substring(1, password.length() - 1);
		return password;
	}

	private final AuthenticationBean authenticationBean;
}
