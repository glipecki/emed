package eu.anmore.emed.observation;

public class CustomObservationTypeEntity {

	public static CustomObservationTypeEntity of(final CustomObservationType model) {
		return new CustomObservationTypeEntity(model.getKey(), model.getVisibleName(), model.getObservationGroup());
	}

	public CustomObservationTypeEntity() {
		super();
	}

	public CustomObservationTypeEntity(final String key, final String visibleName,
			final ObservationGroup observationGroup) {
		super();
		this.key = key;
		this.visibleName = visibleName;
		this.observationGroup = observationGroup;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public String getVisibleName() {
		return visibleName;
	}

	public void setVisibleName(final String visibleName) {
		this.visibleName = visibleName;
	}

	public ObservationGroup getObservationGroup() {
		return observationGroup;
	}

	public void setObservationGroup(final ObservationGroup observationGroup) {
		this.observationGroup = observationGroup;
	}

	@Override
	public String toString() {
		return String.format("CustomObservationTypeEntity [key=%s, visibleName=%s, observationGroup=%s]", key,
				visibleName, observationGroup);
	}

	public CustomObservationType asModel() {
		return new CustomObservationType(key, visibleName, observationGroup);
	}

	private String key;

	private String visibleName;

	private ObservationGroup observationGroup;

}
