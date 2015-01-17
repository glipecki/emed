package eu.anmore.emed.patient;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import eu.anmore.emed.admission.Admission;

/**
 * Patient utils class;
 * 
 * @author mmiedzinski
 */
public final class PatientTestUtils {

	public static Patient getKnownPatient() {
		return new Patient(51, "Mark", "Twain", "89072306830", new LocalDate(1989, 7, 23).toDate(), Patient.Sex.MAN,
				new ArrayList<Admission>());
	}

	private PatientTestUtils() {
		// empty utils class constructor
	}
}
