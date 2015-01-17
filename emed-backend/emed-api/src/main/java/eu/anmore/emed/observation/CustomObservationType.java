package eu.anmore.emed.observation;

import java.util.Arrays;
import java.util.List;

public class CustomObservationType implements ObservationType {

	public CustomObservationType() {
		super();
	}

	public CustomObservationType(final String key, final String visibleName, final ObservationGroup observationGroup) {
		super();
		this.key = key;
		this.visibleName = visibleName;
		this.observationGroup = observationGroup;
	}

	@Override
	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	@Override
	public String getVisibleName() {
		return visibleName;
	}

	@Override
	public String getFullName() {
		return getVisibleName();
	}

	public void setVisibleName(final String visibleName) {
		this.visibleName = visibleName;
	}

	public ObservationGroup getObservationGroup() {
		return observationGroup;
	}

	public void setObservationGroup(final ObservationGroup observationGroups) {
		this.observationGroup = observationGroups;
	}

	@Override
	public String toString() {
		return String.format("CustomObservationType [key=%s, visibleName=%s, observationGroup=%s]", key, visibleName,
				observationGroup);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (key == null ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CustomObservationType)) {
			return false;
		}
		final CustomObservationType other = (CustomObservationType) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}

	@Override
	public List<ObservationGroup> getObservationGroups() {
		return Arrays.asList(observationGroup);
	}

	@Override
	public ValueType getType() {
		return ValueType.NUMERIC;
	}

	@Override
	public boolean isMandatory() {
		return false;
	}

	@Override
	public String getFormat() {
		return "%.2f";
	}

	@Override
	public String getDisplayRawValue(final String value) {
		return value;
	}

	@Override
	public Double getGraphRawValue(final String value) {
		return PredefinedObservationType.getAsDouble(value);
	}

	private String key;

	private String visibleName;

	private ObservationGroup observationGroup;

}
