package eu.anmore.emed.mobi.observation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationType;

public class ObservationListToMapConverter {

	public ObservationListToMapConverter(final List<ObservationType> observationTypes) {
		this.observationTypes = observationTypes;
		this.map = new HashMap<ObservationGroup, List<ObservationType>>();
		this.toAddLater = new ArrayList<ObservationType>();
	}

	public Map<ObservationGroup, List<ObservationType>> convert() {
		map.clear();
		toAddLater.clear();

		fillMapWithEasyValues();
		fillMapWithTroubleValues();

		return map;
	}

	private void fillMapWithTroubleValues() {
		for (final ObservationType observationType : toAddLater) {
			addObservationToFirstGroup(observationType);
		}
	}

	private void addObservationToFirstGroup(final ObservationType observationType) {
		for (final ObservationGroup group : observationType.getObservationGroups()) {
			if (map.containsKey(group)) {
				map.get(group).add(observationType);
				return;
			}
		}
		final List<ObservationType> list = new ArrayList<ObservationType>();
		list.add(observationType);
		map.put(observationType.getObservationGroups().get(0), list);
	}

	private void fillMapWithEasyValues() {
		for (final ObservationType observationType : observationTypes) {
			if (observationType.getObservationGroups().size() == 0) {
				continue;
			}
			if (observationType.getObservationGroups().size() == 1) {
				addObservationToMap(observationType);
			} else {
				if (!addObservationToMapIfAnyGroupAlreadyExists(observationType)) {
					toAddLater.add(observationType);
				}
			}
		}
	}

	private void addObservationToMap(final ObservationType observationType) {
		final ObservationGroup group = observationType.getObservationGroups().get(0);
		if (!map.containsKey(group)) {
			map.put(group, new ArrayList<ObservationType>());
		}
		map.get(group).add(observationType);
	}

	private boolean addObservationToMapIfAnyGroupAlreadyExists(final ObservationType observationType) {
		for (final ObservationGroup group : observationType.getObservationGroups()) {
			if (map.containsKey(group)) {
				map.get(group).add(observationType);
				return true;
			}
		}
		return false;
	}

	private final List<ObservationType> observationTypes;

	private final Map<ObservationGroup, List<ObservationType>> map;

	private final List<ObservationType> toAddLater;

}
