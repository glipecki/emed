package eu.anmore.emed.observation;

public class AdmissionObservationType {

	public AdmissionObservationType() {
		super();
	}

	public AdmissionObservationType(final int admissionId, final String typeKey, final Boolean direct) {
		super();
		this.admissionId = admissionId;
		this.typeKey = typeKey;
		this.direct = direct;
	}

	public int getAdmissionId() {
		return admissionId;
	}

	public void setAdmissionId(final int admissionId) {
		this.admissionId = admissionId;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(final String typeKey) {
		this.typeKey = typeKey;
	}

	public Boolean getDirect() {
		return direct;
	}

	public void setDirect(final Boolean direct) {
		this.direct = direct;
	}

	@Override
	public String toString() {
		return String.format("AdmissionObservationType [admissionId=%s, typeKey=%s, direct=%s]", admissionId, typeKey,
				direct);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + admissionId;
		result = prime * result + (typeKey == null ? 0 : typeKey.hashCode());
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
		if (!(obj instanceof AdmissionObservationType)) {
			return false;
		}
		final AdmissionObservationType other = (AdmissionObservationType) obj;
		if (admissionId != other.admissionId) {
			return false;
		}
		if (typeKey == null) {
			if (other.typeKey != null) {
				return false;
			}
		} else if (!typeKey.equals(other.typeKey)) {
			return false;
		}
		return true;
	}

	private int admissionId;

	private String typeKey;

	private Boolean direct;

}
