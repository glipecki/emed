package eu.anmore.emed.authentication;

/**
 * @author mmiedzinski
 */
public class UserPermissionEntity {

	public UserPermissionEntity(final int userId, final int permissionId) {
		super();
		this.userId = userId;
		this.permissionId = permissionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(final int permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserPermissionEntity [userId=");
		builder.append(userId);
		builder.append(", permissionId=");
		builder.append(permissionId);
		builder.append("]");
		return builder.toString();
	}

	private int userId;

	private int permissionId;
}
