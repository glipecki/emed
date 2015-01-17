package eu.anmore.emed.observation;

public class ObservationNormEntity {

	public static ObservationNormEntity from(final ObservationNorm observationNorm) {
		return new ObservationNormEntity(observationNorm.getObservationType(), observationNorm.getDaysFrom(),
				observationNorm.getDaysTo(), observationNorm.getMinNorm(), observationNorm.getMaxNorm(),
				observationNorm.getMinAlarm(), observationNorm.getMaxAlarm());
	}

	public ObservationNormEntity() {
		super();
	}

	public ObservationNormEntity(final PredefinedObservationType observationType, final Integer daysFrom,
			final Integer daysTo, final String minNorm, final String maxNorm, final String minAlarm,
			final String maxAlarm) {
		super();
		this.observationType = observationType;
		this.daysFrom = daysFrom;
		this.daysTo = daysTo;
		this.minNorm = minNorm;
		this.maxNorm = maxNorm;
		this.minAlarm = minAlarm;
		this.maxAlarm = maxAlarm;
	}

	public PredefinedObservationType getObservationType() {
		return observationType;
	}

	public void setObservationType(final PredefinedObservationType observationType) {
		this.observationType = observationType;
	}

	public String getMinNorm() {
		return minNorm;
	}

	public void setMinNorm(final String minNorm) {
		this.minNorm = minNorm;
	}

	public String getMaxNorm() {
		return maxNorm;
	}

	public void setMaxNorm(final String maxNorm) {
		this.maxNorm = maxNorm;
	}

	public String getMinAlarm() {
		return minAlarm;
	}

	public void setMinAlarm(final String minAlarm) {
		this.minAlarm = minAlarm;
	}

	public String getMaxAlarm() {
		return maxAlarm;
	}

	public void setMaxAlarm(final String maxAlarm) {
		this.maxAlarm = maxAlarm;
	}

	public Integer getDaysFrom() {
		return daysFrom;
	}

	public void setDaysFrom(final Integer daysFrom) {
		this.daysFrom = daysFrom;
	}

	public Integer getDaysTo() {
		return daysTo;
	}

	public void setDaysTo(final Integer daysTo) {
		this.daysTo = daysTo;
	}

	public ObservationNorm asModel() {
		return new ObservationNorm(observationType, daysFrom, daysTo, minNorm, maxNorm, minAlarm, maxAlarm);
	}

	@Override
	public String toString() {
		return String
				.format("ObservationNormEntity [observationType=%s, daysFrom=%s, daysTo=%s, minNorm=%s, maxNorm=%s, minAlarm=%s, maxAlarm=%s]",
						observationType, daysFrom, daysTo, minNorm, maxNorm, minAlarm, maxAlarm);
	}

	private PredefinedObservationType observationType;

	private Integer daysFrom;

	private Integer daysTo;

	private String minNorm;

	private String maxNorm;

	private String minAlarm;

	private String maxAlarm;

}
