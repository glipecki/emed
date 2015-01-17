package eu.anmore.emed.observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author glipecki
 */
public class ObservationsQuery {

	public static ObservationsQuery of(final Integer patientId, final String groupKey) {
		return new ObservationsQuery(patientId, groupKey);
	}

	public static ObservationsQuery of(final Integer patientId, final String type, final Date date) {
		return new ObservationsQuery(patientId, type, date);
	}

	public static ObservationsQuery of(final Integer patientId, final Date date) {
		return new ObservationsQuery(patientId, null, date);
	}

	public static ObservationsQuery of(final Integer patientId) {
		return new ObservationsQuery(patientId, null);
	}

	public ObservationsQuery(final Integer patientId, final String groupKey) {
		this.patientId = patientId;
		this.groupKey = groupKey;
	}

	public ObservationsQuery(final Integer patientId, final String type, final Date date) {
		this.patientId = patientId;
		this.type = type;
		if (date != null) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			this.day = calendar.get(Calendar.DAY_OF_MONTH);
			this.month = calendar.get(Calendar.MONTH) + 1;
			this.year = calendar.get(Calendar.YEAR);
		}
	}

	public ObservationsQuery withObservationTypes(final PredefinedObservationType... observationTypes) {
		this.observationTypes = new ArrayList<>(Arrays.asList(observationTypes));
		return this;
	}

	public ObservationsQuery withObservationGroups(final ObservationGroup... groups) {
		for (final ObservationGroup group : groups) {
			withObservationTypes(group.getObservationTypes().toArray(new PredefinedObservationType[0]));
		}
		return this;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public List<String> getObservationTypes() {
		if (observationTypes == null) {
			return null;
		} else {
			final List<String> types = new ArrayList<>();
			for (final PredefinedObservationType type : observationTypes) {
				types.add(type.getKey().toUpperCase());
			}
			return types.isEmpty() ? Arrays.asList("NOT_MATCHING_;)") : types;
		}
	}

	public Integer getDay() {
		return day;
	}

	public Integer getMonth() {
		return month;
	}

	public Integer getYear() {
		return year;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return String
				.format("ObservationsQuery [day=%s, month=%s, year=%s, patientId=%s, type=%s, groupKey=%s, observationTypes=%s]",
						day, month, year, patientId, type, groupKey, observationTypes);
	}

	private Integer day;

	private Integer month;

	private Integer year;

	private final Integer patientId;

	private String type;

	private String groupKey;

	private List<PredefinedObservationType> observationTypes;
}
