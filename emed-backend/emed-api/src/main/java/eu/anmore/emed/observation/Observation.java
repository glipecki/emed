package eu.anmore.emed.observation;

import java.util.Comparator;
import java.util.Date;

/**
 * Observation.
 * 
 * @author glipecki
 * 
 */
public class Observation {

	public static final CompareObservationsByDate byDateComparator() {
		return new CompareObservationsByDate();
	}

	/**
	 * Porównuje obserwacje po datach.
	 * 
	 * @author glipecki
	 * 
	 */
	public static final class CompareObservationsByDate implements Comparator<Observation> {
		@Override
		public int compare(final Observation lhs, final Observation rhs) {
			return lhs.getDate().compareTo(rhs.getDate());
		}
	}

	/**
	 * Serialization constructor.
	 * 
	 * @deprecated serialization constructor
	 */
	@Deprecated
	public Observation() {
	}

	public Observation(final String typeKey, final Date date, final String value) {
		super();
		this.typeKey = typeKey;
		this.value = value;
		if (date != null) {
			this.date = new Date(date.getTime());
		} else {
			this.date = null;
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setObserverUsername(final String observerUsername) {
		this.observerUsername = observerUsername;
	}

	public String getObserverUsername() {
		return observerUsername;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(final Integer batchId) {
		this.batchId = batchId;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public String getTypeKey() {
		return typeKey;
	}

	@Override
	public String toString() {
		return String
				.format("Observation [groupKey=%s, typeKey=%s, value=%s, date=%s, timestamp=%s, observerUsername=%s, batchId=%s]",
						groupKey, typeKey, value, date, timestamp, observerUsername, batchId);
	}

	public void setGroupKey(final String groupKey) {
		this.groupKey = groupKey;
	}

	public void setTypeKey(final String typeKey) {
		this.typeKey = typeKey;
	}

	private String groupKey;

	private String typeKey;

	private String value;

	private Date date;

	private Date timestamp;

	private String observerUsername;

	private Integer batchId;

}
