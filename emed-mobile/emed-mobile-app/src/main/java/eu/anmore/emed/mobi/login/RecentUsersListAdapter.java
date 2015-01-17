package eu.anmore.emed.mobi.login;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationConfigurationFacade;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.user.User;

public class RecentUsersListAdapter extends ArrayAdapter<User> {

	@Inject
	public RecentUsersListAdapter(final Context context, final ApplicationConfigurationFacade configuration,
			final LayoutInflater layoutInflater) {
		super(context, LAYOUT_ID, new ArrayList<User>());
		this.layoutInflater = layoutInflater;

		for (final User user : configuration.getRecentUsers()) {
			add(user);
		}
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final RowView rowView = new RowView(layoutInflater.inflate(LAYOUT_ID, parent, false));
		final User user = getItem(position);

		rowView.setUsername(user.getUsername());
		rowView.setDisplayName(getUserDisplayName(user));

		return rowView.getView();
	}

	private static final int LAYOUT_ID = R.layout.login_recent_users_row;

	private static final int ROW_USERNAME = R.id.loginPresenter_recent_users_row_username;

	private static final int ROW_DISPLAY_NAME = R.id.loginPresenter_recent_users_row_display_name;

	private static final class RowView {

		public RowView(final View rowView) {
			this.rowView = rowView;
		}

		public void setDisplayName(final String userDisplayName) {
			final TextView displayNameTextView = (TextView) rowView.findViewById(ROW_DISPLAY_NAME);
			displayNameTextView.setText(userDisplayName);
		}

		public void setUsername(final String username) {
			final TextView usernameTextView = (TextView) rowView.findViewById(ROW_USERNAME);
			usernameTextView.setText(username);
		}

		public View getView() {
			return rowView;
		}

		private final View rowView;
	}

	private String getUserDisplayName(final User user) {
		return String.format("%s %s", user.getFirstName(), user.getSurname());
	}

	private final LayoutInflater layoutInflater;

}