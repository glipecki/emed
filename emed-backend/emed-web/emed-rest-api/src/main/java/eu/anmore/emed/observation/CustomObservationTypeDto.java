package eu.anmore.emed.observation;

public class CustomObservationTypeDto {

	public CustomObservationTypeDto(final CustomObservationType customObservationType) {
		this.customObservationType = customObservationType;
	}

	public String getKey() {
		return customObservationType.getKey();
	}

	public String getVisibleName() {
		return customObservationType.getVisibleName();
	}

	public String getObservationGroup() {
		return customObservationType.getObservationGroup().toString();
	}

	private final CustomObservationType customObservationType;

}
