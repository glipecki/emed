package eu.anmore.emed.authentication;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Authentication controller interface.
 * 
 * @author Grzegorz Lipecki
 */
@RequestMapping(AuthenticationRestDescriptor.ROOT_URI)
public interface AuthenticationController extends Authentication {

	/**
	 * Call authentication REST service.
	 * <p>
	 * Provides REST abstraction layer to
	 * {@link Authentication#authenticate(String, String)} method.
	 * </p>
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UserNotFoundException
	 * @throws UserBadCredentialsException
	 * @throws InvalidAuthenticationRequestException
	 */
	@RequestMapping(value = AuthenticationRestDescriptor.AUTHENTICATE_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	UserDto callAuthenticate(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username,
			@RequestBody String password) throws UserNotFoundException, UserBadCredentialsException,
			InvalidAuthenticationRequestException;

	/**
	 * Get all users.
	 * 
	 * @return
	 */
	@RequestMapping(value = AuthenticationRestDescriptor.GET_USERS_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	UserListDto callGetUsers();

	/**
	 * Add user into system.
	 * 
	 * @param userDiff
	 * @return
	 */
	@RequestMapping(value = AuthenticationRestDescriptor.ADD_USER_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	UserDto callAddUser(@RequestBody final UserDiff userDiff);

	/**
	 * Edit user.
	 * 
	 * @param userDiff
	 * @return
	 * @throws UserNotFoundException
	 */
	@RequestMapping(value = AuthenticationRestDescriptor.EDIT_USER_URL_PATTERN, method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	UserDto callEditUser(@RequestBody final UserDiff userDiff) throws UserNotFoundException;

	/**
	 * Check that username is unique.
	 * 
	 * @param username
	 * @return
	 */
	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.IS_UNIQUE_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	boolean isUnique(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) String username);

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.BLOCK_USER_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	boolean blockUser(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) String username);

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.UNBLOCK_USER_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	boolean unblockUser(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) String username);

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.ADD_PERMISSION_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	boolean addPermission(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username,
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_PERMISSION_PARAM) final String permission);

	@Override
	@RequestMapping(value = AuthenticationRestDescriptor.REMOVE_PERMISSION_URL_PATTERN, method = RequestMethod.GET)
	@ResponseBody
	boolean removePermission(@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_USER_PARAM) final String username,
			@PathVariable(AuthenticationRestDescriptor.AUTHENTICATE_PERMISSION_PARAM) final String permission);
}
