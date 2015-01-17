package eu.anmore.emed.mobi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Singleton;

import eu.anmore.emed.mobi.user.User;
import eu.anmore.emed.observation.AdmissionObservationType;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.emed.observation.ObservationNorm;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.emed.observation.PredefinedObservationType;

@Singleton
public class ApplicationState {

	public boolean isAuthenticated() {
		return authenticatedUser != null;
	}

	public void logout() {
		authenticatedUser = null;
	}

	public void setAuthenticatedUser(final User user) {
		authenticatedUser = user;
	}

	public User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setObservationNorms(final List<ObservationNorm> newNorms) {
		this.norms = newNorms;
	}

	public List<ObservationNorm> getObservationNorms() {
		return norms;
	}

	public List<ObservationNorm> getObservationNorms(final Date birthday) {
		if (birthday == null) {
			return new ArrayList<ObservationNorm>();
		} else {
			final List<ObservationNorm> normsForAge = new ArrayList<ObservationNorm>();
			for (final ObservationNorm norm : norms) {
				if (norm.isDayInRange(birthday)) {
					normsForAge.add(norm);
				}
			}
			return normsForAge;
		}
	}

	public void setCustomObservationTypes(final List<CustomObservationType> customObservationTypes) {
		this.customObservationTypes = customObservationTypes;
		groupToCustomType.clear();
		for (final CustomObservationType customObservationType : customObservationTypes) {
			if (!groupToCustomType.containsKey(customObservationType.getObservationGroup())) {
				groupToCustomType.put(customObservationType.getObservationGroup(),
						new ArrayList<CustomObservationType>());
			}
			groupToCustomType.get(customObservationType.getObservationGroup()).add(customObservationType);
		}
	}

	public List<CustomObservationType> getCustomObservationTypes(final ObservationGroup group) {
		return groupToCustomType.containsKey(group) ? groupToCustomType.get(group)
				: new ArrayList<CustomObservationType>();
	}

	public Map<ObservationGroup, List<CustomObservationType>> getCustomObservationTypes() {
		return groupToCustomType;
	}

	public ObservationType getObservationTypeFor(final String typeKey) {
		for (final PredefinedObservationType predefined : PredefinedObservationType.values()) {
			if (predefined.getKey().equals(typeKey)) {
				return predefined;
			}
		}
		for (final CustomObservationType custom : customObservationTypes) {
			if (custom.getKey().equals(typeKey)) {
				return custom;
			}
		}
		return null;
	}

	public void putPatientAdmissionObservationTypes(final Integer patientId,
			final List<AdmissionObservationType> admissionTypes) {
		final List<AdmissionObservationType> types = new ArrayList<AdmissionObservationType>();
		for (final AdmissionObservationType admissionObservationType : admissionTypes) {
			types.add(admissionObservationType);
		}
		patientAdmissionTypes.put(patientId, types);
	}

	public List<AdmissionObservationType> getPatientAdmissionTypes(final Integer patientId) {
		return patientAdmissionTypes.containsKey(patientId) ? patientAdmissionTypes.get(patientId)
				: new ArrayList<AdmissionObservationType>();
	}

	public List<String> getPatientAdmissionTypeKeys(final Integer patientId) {
		final List<AdmissionObservationType> types = getPatientAdmissionTypes(patientId);
		final List<String> result = new ArrayList<String>();
		for (final AdmissionObservationType admissionObservationType : types) {
			result.add(admissionObservationType.getTypeKey());
		}
		return result;
	}

	public List<ObservationType> filterDirectObservationTypes(final Integer activePatientId,
			final List<ObservationType> observationTypes) {
		final List<AdmissionObservationType> admissionTypes = getPatientAdmissionTypes(activePatientId);
		final List<ObservationType> filtered = new ArrayList<ObservationType>();
		for (final ObservationType obervationType : observationTypes) {
			for (final AdmissionObservationType admissionType : admissionTypes) {
				if (obervationType.getKey().equals(admissionType.getTypeKey()) && admissionType.getDirect()) {
					filtered.add(obervationType);
					break;
				}
			}
		}
		return filtered;
	}

	public List<PredefinedObservationType> filterPredefinedObservationTypes(final Integer patientId,
			final List<PredefinedObservationType> observationTypes) {
		final List<String> admissionTypes = getPatientAdmissionTypeKeys(patientId);
		final List<PredefinedObservationType> filtered = new ArrayList<PredefinedObservationType>();
		for (final PredefinedObservationType obervationType : observationTypes) {
			if (admissionTypes.contains(obervationType.getKey())) {
				filtered.add(obervationType);
			}
		}
		return filtered;
	}

	public List<CustomObservationType> filterCustomObservationTypes(final int patientId,
			final List<CustomObservationType> types) {
		final List<CustomObservationType> filtered = new ArrayList<CustomObservationType>();
		final List<String> admissionTypes = getPatientAdmissionTypeKeys(patientId);
		for (final CustomObservationType customObservationType : types) {
			if (admissionTypes.contains(customObservationType.getKey())) {
				filtered.add(customObservationType);
			}
		}
		return filtered;
	}

	public List<Observation> filterObservations(final int patientId, final List<Observation> observations) {
		final List<Observation> filtered = new ArrayList<Observation>();
		final List<String> admissionTypes = getPatientAdmissionTypeKeys(patientId);
		for (final Observation observation : observations) {
			if (admissionTypes.contains(observation.getTypeKey())) {
				filtered.add(observation);
			}
		}
		return filtered;
	}

	private final Map<Integer, List<AdmissionObservationType>> patientAdmissionTypes = new HashMap<Integer, List<AdmissionObservationType>>();

	private List<CustomObservationType> customObservationTypes;

	private User authenticatedUser;

	private List<ObservationNorm> norms = null;

	private final Map<ObservationGroup, List<CustomObservationType>> groupToCustomType = new HashMap<ObservationGroup, List<CustomObservationType>>();

	// TODO (Grzegorz Lipecki): security context
}
