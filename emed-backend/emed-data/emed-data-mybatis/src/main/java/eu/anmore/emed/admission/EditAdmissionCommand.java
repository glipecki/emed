package eu.anmore.emed.admission;

/**
 * Edit admission command.
 * 
 * @author mmiedzinski
 */
public class EditAdmissionCommand {

	public static EditAdmissionCommand get(final int id, final AdmissionDiff diff) {
		return new EditAdmissionCommand(id, diff);
	}

	public EditAdmissionCommand(final int id, final AdmissionDiff diff) {
		this.id = id;
		this.diff = diff;
	}

	public int getId() {
		return id;
	}

	public AdmissionDiff getDiff() {
		return diff;
	}

	private final int id;

	private final AdmissionDiff diff;
}
