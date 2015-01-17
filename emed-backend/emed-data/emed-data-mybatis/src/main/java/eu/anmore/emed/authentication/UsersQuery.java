package eu.anmore.emed.authentication;


/**
 * User query.
 *
 * @author Grzegorz Lipecki
 */
public final class UsersQuery {

	public static UsersQueryBuilder getBuilder() {
		return UsersQueryBuilder.getBuilder();
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * UserQuery builder.
	 *
	 * @author Grzegorz Lipecki
	 */
	public static final class UsersQueryBuilder {

		private static UsersQueryBuilder getBuilder() {
			return new UsersQueryBuilder();
		}

		public UsersQueryBuilder idEquals(final int id) {
			userQuery.id = id;
			return this;
		}

		public UsersQueryBuilder usernameEquals(final String username) {
			userQuery.username = username;
			return this;
		}

		public UsersQueryBuilder passwordEquals(final String password) {
			userQuery.password = password;
			return this;
		}

		public UsersQuery build() {
			return userQuery;
		}

		private UsersQueryBuilder() {
			userQuery = new UsersQuery();
		}

		private final UsersQuery userQuery;

	}

	private UsersQuery() {
	}

	private Integer id;

	private String username;

	private String password;

}
