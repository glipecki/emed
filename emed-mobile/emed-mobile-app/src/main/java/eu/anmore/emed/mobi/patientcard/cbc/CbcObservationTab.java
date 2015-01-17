package eu.anmore.emed.mobi.patientcard.cbc;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.patientcard.table.OneGroupTableObservationTab;
import eu.anmore.emed.observation.ObservationGroup;

public class CbcObservationTab extends OneGroupTableObservationTab {

	@Inject
	public CbcObservationTab(final MobiTaskRunner taskRunner, final ApplicationState appState) {
		super(taskRunner, appState);
	}

	@Override
	protected ObservationGroup getObservationGroup() {
		return ObservationGroup.CBC;
	}

}
