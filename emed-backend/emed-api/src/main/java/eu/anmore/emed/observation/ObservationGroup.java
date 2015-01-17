package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author glipecki
 */
public enum ObservationGroup {

	/**
	 * Hemodynamika.
	 */
	HEMODYNAMICS("HEMODYNAMICS", "Hemodynamika"),

	/**
	 * Gazometria.
	 */
	GASOMETRY("GASOMETRY", "Gazometria"),

	/**
	 * Drenaż.
	 */
	DRAINAGE("DRAINAGE", "Drenaż"),

	/**
	 * Koloidy.
	 */
	COLLOIDS("COLLOIDS", "Koloidy"),

	/**
	 * Diureza.
	 */
	DIURESIS("DIURESIS", "Diureza"),

	/**
	 * Sonda podaż.
	 */
	PROBEIN("PROBEIN", "Sonda podaż"),

	/**
	 * Sonda zalegania.
	 */
	PROBEOUT("PROBEOUT", "Sonda zalegania"),

	/**
	 * Stolec / wymioty.
	 */
	STOOLEEMESIS("STOOLEEMESIS", "Stolec / wymioty"),

	/**
	 * Morfologia.
	 * <p>
	 * complete blood count
	 * </p>
	 */
	CBC("CBC", "Morfologia"),

	/**
	 * Biochemia.
	 */
	BLOODCHEMISTRY("BLOODCHEMISTRY", "Biochemia"),

	/**
	 * Koagulologia.
	 */
	COAGULATIONPANEL("COAGULATIONPANEL", "Koagulologia"),

	/**
	 * Bolusy.
	 */
	BOLUSY("BOLUSY", "Bolusy"),

	/**
	 * Wlewy.
	 */
	WLEWY("WLEWY", "Wlewy"),

	/**
	 * Dla parametrów zdefiniowanych przez użytkownika.
	 */
	CUSTOM("CUSTOM", "Typt użytkownika");

	public static ObservationGroup getByObservationTypeKey(final String typeKey) {
		for (final ObservationGroup group : ObservationGroup.values()) {
			for (final PredefinedObservationType type : group.getObservationTypes()) {
				if (type.getKey().equals(typeKey)) {
					return group;
				}
			}
		}
		return null;
	};

	public static List<String> getObservationGroupKeys() {
		final List<String> keys = new ArrayList<String>();
		for (final ObservationGroup group : ObservationGroup.values()) {
			keys.add(group.getVisibleName());
		}
		return keys;
	}

	public String getKey() {
		return key;
	}

	public String getVisibleName() {
		return visibleName;
	}

	public List<PredefinedObservationType> getObservationTypes() {
		final List<PredefinedObservationType> observationTypes = new ArrayList<PredefinedObservationType>();

		for (final PredefinedObservationType observationType : PredefinedObservationType.values()) {
			if (observationType.getObservationGroups().contains(this)) {
				observationTypes.add(observationType);
			}
		}

		return observationTypes;
	}

	private ObservationGroup(final String key, final String visibleName) {
		this.key = key;
		this.visibleName = visibleName;
	}

	private final String visibleName;

	private String key;

}
