package eu.anmore.emed.observation;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * Observation norm.
 * 
 * @author glipecki
 * 
 */
public class ObservationNorm {

	public enum NormExcess {
		OVER, BELOW, GOOD
	}

	public static boolean overDays(final Date birthDate, final DateTime today, final Integer days) {
		return days == null || Days.daysBetween(new DateTime(birthDate), today).getDays() >= days;
	}

	public static boolean underDays(final Date birthDate, final DateTime today, final Integer days) {
		return days == null || Days.daysBetween(new DateTime(birthDate), today).getDays() < days;
	}

	public ObservationNorm() {
		super();
	}

	public ObservationNorm(final PredefinedObservationType observationType, final Integer daysFrom,
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

	public NormExcess getNormStatus(final String rawValue) {
		return getNormStatus(rawValue, minNorm, maxNorm);
	}

	public boolean isInNorm(final String rawValue) {
		return isInValueRange(rawValue, minNorm, maxNorm);
	}

	public boolean isAlarm(final String rawValue) {
		return isOutOfValueRange(rawValue, minAlarm, maxAlarm);
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

	@Override
	public String toString() {
		return String
				.format("ObservationNorm [observationType=%s, daysFrom=%s, daysTo=%s, minNorm=%s, maxNorm=%s, minAlarm=%s, maxAlarm=%s]",
						observationType, daysFrom, daysTo, minNorm, maxNorm, minAlarm, maxAlarm);
	}

	public boolean isDayInRange(final Date birthday) {
		final int daysSinceBorn = Days.daysBetween(new LocalDate(birthday), new LocalDate()).getDays();
		return overDays(daysSinceBorn) && underDays(daysSinceBorn);
	}

	private boolean overDays(final int daysSinceBorn) {
		return daysFrom == null || daysFrom <= daysSinceBorn;
	}

	private boolean underDays(final int daysSinceBorn) {
		return daysTo == null || daysSinceBorn <= daysTo;
	}

	private NormExcess getNormStatus(final String rawValue, final String rawMin, final String rawMax) {
		switch (observationType.getType()) {
		case NUMERIC:
			return getNumericNormStatus(rawValue, rawMin, rawMax);
		case DOMAIN:
			throw new UnsupportedOperationException("Unsupported value type for observation norm");
		default:
			throw new UnsupportedOperationException("Unknown value type for observation type");
		}
	}

	private boolean isInValueRange(final String rawValue, final String rawMin, final String rawMax) {
		switch (observationType.getType()) {
		case NUMERIC:
			return isInNumericRange(rawValue, rawMin, rawMax);
		case DOMAIN:
			throw new UnsupportedOperationException("Unsupported value type for observation norm");
		default:
			throw new UnsupportedOperationException("Unknown value type for observation type");
		}
	}

	private boolean isOutOfValueRange(final String rawValue, final String rawMin, final String rawMax) {
		switch (observationType.getType()) {
		case NUMERIC:
			return isOutOfNumericRange(rawValue, rawMin, rawMax);
		case DOMAIN:
			throw new UnsupportedOperationException("Unsupported value type for observation norm");
		default:
			throw new UnsupportedOperationException("Unknown value type for observation type");
		}
	}

	private NormExcess getNumericNormStatus(final String rawValue, final String rawMin, final String rawMax) {
		final Double value = Double.parseDouble(rawValue);
		final Double min = Double.parseDouble(rawMin);
		final Double max = Double.parseDouble(rawMax);

		if (value > max) {
			return NormExcess.OVER;
		} else if (value < min) {
			return NormExcess.BELOW;
		} else {
			return NormExcess.GOOD;
		}
	}

	private boolean isInNumericRange(final String rawValue, final String rawMin, final String rawMax) {
		final Double value = Double.parseDouble(rawValue);
		final Double min = Double.parseDouble(rawMin);
		final Double max = Double.parseDouble(rawMax);

		return value >= min && value <= max;
	}

	private boolean isOutOfNumericRange(final String rawValue, final String rawMin, final String rawMax) {
		final Double value = Double.parseDouble(rawValue);
		final Double min = Double.parseDouble(rawMin);
		final Double max = Double.parseDouble(rawMax);

		return value <= min || value >= max;
	}

	private PredefinedObservationType observationType;

	private Integer daysFrom;

	private Integer daysTo;

	private String minNorm;

	private String maxNorm;

	private String minAlarm;

	private String maxAlarm;

}
