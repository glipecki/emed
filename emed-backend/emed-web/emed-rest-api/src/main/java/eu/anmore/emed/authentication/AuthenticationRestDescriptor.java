package eu.anmore.emed.authentication;

/**
 * Authentication REST Service descriptor.
 * <p>
 * Provides information about urls, param names.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public final class AuthenticationRestDescriptor {

	/**
	 * @return url to service listing users.
	 */
	public static String getUserListRestUrl() {
		return ROOT_URI + GET_USERS_URL_PATTERN;
	}

	/**
	 * @return
	 */
	public static String getAddUserRestUrl() {
		return ROOT_URI + ADD_USER_URL_PATTERN;
	}

	/**
	 * @return
	 */
	public static String getEditUserRestUrl() {
		return ROOT_URI + EDIT_USER_URL_PATTERN;
	}

	/**
	 * @return
	 */
	public static String getIsUniqueRestUrl(final String username) {
		return ROOT_URI + IS_UNIQUE_URL_PATTERN.replace("{user}", username);
	}

	/**
	 * @return
	 */
	public static String getBlockUserRestUrl(final String username) {
		return ROOT_URI + BLOCK_USER_URL_PATTERN.replace("{user}", username);
	}

	/**
	 * @return
	 */
	public static String getUnblockUserRestUrl(final String username) {
		return ROOT_URI + UNBLOCK_USER_URL_PATTERN.replace("{user}", username);
	}

	/**
	 * @return
	 */
	public static String getAddPermissionRestUrl(final String username, final String permission) {
		return ROOT_URI + ADD_PERMISSION_URL_PATTERN.replace("{user}", username).replace("{permission}", permission);
	}

	/**
	 * @return
	 */
	public static String getRemovePermissionRestUrl(final String username, final String permission) {
		return ROOT_URI + REMOVE_PERMISSION_URL_PATTERN.replace("{user}", username).replace("{permission}", permission);
	}

	public static String getAuthenticateRestUrl(final String username) {
		return ROOT_URI + AUTHENTICATE_URL_PATTERN.replace("{user}", username);
	}

	/** Authentication root module url */
	public static final String ROOT_URI = "/authentication";

	/** Authenticate method url pattern */
	public static final String AUTHENTICATE_URL_PATTERN = "/authenticate/{user}";

	/** Is unique method url pattern */
	public static final String IS_UNIQUE_URL_PATTERN = "/isUnique/{user}";

	/** Block user method url pattern */
	public static final String BLOCK_USER_URL_PATTERN = "/blockUser/{user}";

	/** Unblock user method url pattern */
	public static final String UNBLOCK_USER_URL_PATTERN = "/unblockUser/{user}";

	/** Add permission method url pattern */
	public static final String ADD_PERMISSION_URL_PATTERN = "/addPermission/{user}/{permission}";

	/** Remove permission method url pattern */
	public static final String REMOVE_PERMISSION_URL_PATTERN = "/removePermission/{user}/{permission}";

	/** Get users method url pattern */
	public static final String GET_USERS_URL_PATTERN = "/getUsers";

	/** Add user method url pattern */
	public static final String ADD_USER_URL_PATTERN = "/addUser";

	/** Edit user method url pattern */
	public static final String EDIT_USER_URL_PATTERN = "/editUser";

	/** Authentication method user param */
	public static final String AUTHENTICATE_USER_PARAM = "user";

	/** Authentication method permission param */
	public static final String AUTHENTICATE_PERMISSION_PARAM = "permission";

	/** Invalid credentials status code */
	public static final int INVALID_CREDENTIALS = 410;

	/** Utility class */
	private AuthenticationRestDescriptor() {
		// intentionally left blank
	}
}
