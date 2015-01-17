package eu.anmore.emed.observation;

import java.util.List;

public interface ObservationType {

	String getKey();

	List<ObservationGroup> getObservationGroups();

	/**
	 * Pobiera etykietę parametru, np. skrót.
	 * 
	 * @return
	 */
	String getVisibleName();

	/**
	 * Pobiera pełną nazwę parametru.
	 * 
	 * @return
	 */
	String getFullName();

	ValueType getType();

	boolean isMandatory();

	String getFormat();

	String getDisplayRawValue(String value);

	Double getGraphRawValue(String value);

}
