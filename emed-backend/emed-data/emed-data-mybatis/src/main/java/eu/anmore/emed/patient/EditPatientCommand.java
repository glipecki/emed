package eu.anmore.emed.patient;

/**
 * @author Grzegorz Lipecki
 */
public class EditPatientCommand {

	public static EditPatientCommand get(final int id, final PatientDiff diff) {
		return new EditPatientCommand(id, diff);
	}

	public EditPatientCommand(final int id, final PatientDiff diff) {
		this.id = id;
		this.diff = diff;
	}

	public int getId() {
		return id;
	}

	public PatientDiff getDiff() {
		return diff;
	}

	private final int id;

	private final PatientDiff diff;
}
