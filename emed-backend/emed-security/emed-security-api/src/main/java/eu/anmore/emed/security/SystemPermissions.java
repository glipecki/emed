package eu.anmore.emed.security;

/**
 * Permissions table.
 * 
 * @author Grzegorz Lipecki
 */
public final class SystemPermissions {

	/**
	 * Permission for authentication module.
	 * 
	 * @author Grzegorz Lipecki
	 */
	public static final class Authentication {

		/**
		 * Permission to authenticate.
		 */
		public static final String AUTHENTICATE = "bf_authentication_authenticate";

		/**
		 * PreAuthorize annotation value for hasAuthority to authenticate.
		 */
		public static final String HAS_AUTHENTICATE = "hasAuthority('" + AUTHENTICATE + "')";

	}

}
