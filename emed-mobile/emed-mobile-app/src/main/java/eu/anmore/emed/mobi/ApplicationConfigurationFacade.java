package eu.anmore.emed.mobi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.achartengine.chart.PointStyle;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.user.User;

/**
 * Facade to shared configuration.
 * 
 * @author Grzegorz Lipecki
 */
public class ApplicationConfigurationFacade {

	@Inject
	public ApplicationConfigurationFacade(final Activity context) {
		config = context.getSharedPreferences(CONFIGURATION_NAME, 0);
	}

	public boolean isUseNetowrkStub() {
		// TODO (glipecki): to remove
		return false;
	}

	public String getServerAddress() {
		return config.getString(CONFIG_KEY_SERVER_ADDRESS, CONFIG_DEAULT_SERVER_ADDRESS);
	}

	public void setUseNetworkStub(final boolean useNetworkStub) {
		final SharedPreferences.Editor configEditor = config.edit();
		configEditor.putBoolean(CONFIG_KEY_STUB_NETWORK, useNetworkStub);
		configEditor.commit();
	}

	public void setServerAddress(final String serverAddress) {
		final SharedPreferences.Editor configEditor = config.edit();
		configEditor.putString(CONFIG_KEY_SERVER_ADDRESS, serverAddress);
		configEditor.commit();
	}

	public List<User> getRecentUsers() {
		final String rawRecentUsers = config.getString(CONFIG_KEY_RECENT_USERS, "");
		final List<User> recentUsers = getUsersFromString(rawRecentUsers);
		return recentUsers;
	}

	public void addRecentUser(final User user) {
		final String rawRecentUsers = config.getString(CONFIG_KEY_RECENT_USERS, "");
		final List<User> recentUsers = getUsersFromString(rawRecentUsers);

		while (recentUsers.contains(user)) {
			recentUsers.remove(user);
		}
		recentUsers.add(0, user);

		final SharedPreferences.Editor configEditor = config.edit();
		configEditor.putString(CONFIG_KEY_RECENT_USERS, getStringFromUsers(recentUsers));
		configEditor.commit();
	}

	public boolean isDeveloperMode() {
		return true;
	}

	public float getChartLineWidth() {
		return config.getFloat(CONFIG_KEY_CHART_LINE_WIDTH, 3f);
	}

	public float getChartValuesTextSize() {
		return config.getFloat(CONFIG_KEY_CHART_VALUES_TEXT_SIZE, 16f);
	}

	public float getChartAxisTextSize() {
		return config.getFloat(CONFIG_KEY_CHART_AXIS_TEXT_SIZE, 16f);
	}

	public float getChartLablesTextSize() {
		return config.getFloat(CONFIG_KEY_LABLES_TEXT_SIZE, 16f);
	}

	public float getChartPointWidth() {
		return config.getFloat(CONFIG_KEY_CHART_POINT_WIDTH, 2f);
	}

	public PointStyle getChartPointStyle() {
		return PointStyle.TRIANGLE;
	}

	private static final String CONFIGURATION_NAME = "emed-mobile-shared-preferences";

	private static final String CONFIG_KEY_SERVER_ADDRESS = "serverAddress";

	private static final String CONFIG_KEY_RECENT_USERS = "recentUsers";

	private static final String CONFIG_KEY_CHART_LINE_WIDTH = "chart.lineWidth";

	private static final String CONFIG_KEY_CHART_VALUES_TEXT_SIZE = "chart.valuesTextSize";

	private static final String CONFIG_KEY_CHART_AXIS_TEXT_SIZE = "chart.axisTextSize";

	private static final String CONFIG_KEY_LABLES_TEXT_SIZE = "chart.labelsTextSize";

	private static final String CONFIG_KEY_CHART_POINT_WIDTH = "chart.pointWidth";

	private static final String CONFIG_DEAULT_SERVER_ADDRESS = "http://192.168.1.10/emed-rest";

	private static final String CONFIG_KEY_STUB_NETWORK = "useNetworkStub";

	private static final Boolean CONFIG_DEFAULT_STUB_NETWORK = true;

	private String getStringFromUsers(final List<User> recentUsers) {
		final StringBuilder sb = new StringBuilder();

		final Iterator<User> userIterator = recentUsers.iterator();
		while (userIterator.hasNext()) {
			final User user = userIterator.next();
			sb.append(String.format("%s:%s:%s", user.getUsername(), user.getFirstName(), user.getSurname()));
			if (userIterator.hasNext()) {
				sb.append(";");
			}
		}

		return sb.toString();
	}

	private List<User> getUsersFromString(final String rawList) {
		if (rawList == null || rawList.isEmpty()) {
			return new ArrayList<User>();
		}

		final List<User> result = new ArrayList<User>();
		final String[] users = rawList.split(";");
		for (final String user : users) {
			final String[] userParts = user.split(":");
			result.add(new User(userParts[0], userParts[1], userParts.length > 2 ? userParts[2] : ""));
		}
		return result;
	}

	private final SharedPreferences config;

}
