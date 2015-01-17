package eu.anmore.emed.observation;

import java.util.Date;

/**
 * @author glipecki
 */
public class ObservationEntity {

	public ObservationEntity(final String type, final Integer patientId, final Date date, final String value,
			final String observerUsername, final Date timestamp, final Integer batchId) {
		this();
		this.type = type;
		this.patientId = patientId;
		this.date = date;
		this.value = value;
		this.observerUsername = observerUsername;
		this.timestamp = timestamp;
		this.batchId = batchId;
	}

	public ObservationEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(final Integer patientId) {
		this.patientId = patientId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public void setType(final String typeString) {
		this.type = typeString;
		this.typeKey = typeString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getObserverUsername() {
		return observerUsername;
	}

	public void setObserverUsername(final String observerUsername) {
		this.observerUsername = observerUsername;
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

	public void setGroupKey(final String groupKey) {
		this.groupKey = groupKey;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(final String typeKey) {
		this.typeKey = typeKey;
	}

	@Override
	public String toString() {
		return String
				.format("ObservationEntity "
						+ "[typeKey=%s, observerUsername=%s, id=%s, patientId=%s, value=%s, type=%s, date=%s, timestamp=%s, batchId=%s, groupKey=%s]",
						typeKey, observerUsername, id, patientId, value, type, date, timestamp, batchId, groupKey);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
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
		if (!(obj instanceof ObservationEntity)) {
			return false;
		}
		final ObservationEntity other = (ObservationEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Observation asModel() {
		final Observation observation = new Observation(type, date, value);
		observation.setObserverUsername(observerUsername);
		if (timestamp != null) {
			observation.setTimestamp(new Date(timestamp.getTime()));
		} else {
			observation.setTimestamp(null);
		}
		observation.setBatchId(batchId);
		if (groupKey != null) {
			observation.setGroupKey(groupKey);
		} else {
			final ObservationGroup group = ObservationGroup.getByObservationTypeKey(typeKey);
			if (group != null) {
				observation.setGroupKey(group.getKey());
			}
		}
		return observation;
	}

	private String typeKey;

	private String observerUsername;

	/** Int field; primary key */
	private Integer id;

	/** Int field with reference to patients table */
	private Integer patientId;

	/** String field with max length 10 */
	private String value;

	/** String field with max length 20 */
	private String type;

	/** Date field */
	private Date date;

	/** Timestamp field */
	private Date timestamp;

	private Integer batchId;

	private String groupKey;

}
