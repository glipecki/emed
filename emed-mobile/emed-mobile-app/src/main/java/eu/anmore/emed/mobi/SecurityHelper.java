package eu.anmore.emed.mobi;

import com.google.inject.Inject;

public class SecurityHelper {

	@Inject
	public SecurityHelper(final MobiPlaceManager placeManager, final ApplicationState applicationState) {
		this.placeManager = placeManager;
		this.applicationState = applicationState;
	}

	public boolean verifyAuthentication() {
		if (!applicationState.isAuthenticated()) {
			placeManager.logoutCurrentUser();
			return false;
		} else {
			return true;
		}
	}

	private final MobiPlaceManager placeManager;

	private final ApplicationState applicationState;

}
