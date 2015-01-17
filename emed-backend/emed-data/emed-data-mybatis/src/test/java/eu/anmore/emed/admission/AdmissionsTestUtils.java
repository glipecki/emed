package eu.anmore.emed.admission;

import java.util.Date;

import org.joda.time.LocalDate;

import eu.anmore.emed.patient.Patient;

/**
 * Utils class for admissions.
 * 
 * @author mmiedzinski
 */
public final class AdmissionsTestUtils {

	public static Admission getKnownAdmission(final Patient patient) {
		return new Admission(1, patient.getId(), new LocalDate(1989, 7, 23).toDate(), "0", "defect1", 22.0, 44.0, 33.0,
				true, "place1", new LocalDate(1989, 7, 24).toDate(), "reason1");
	}

	public static AdmissionDiff getSampleAdmissioinDiff(final Patient patient) {
		return AdmissionDiffBuilder.get().admissionDate(new Date()).blood("0").bodyArea(22.0).death(false)
				.dischargeDate(new Date()).dischargePlace("place1").height(22.0).hearthDefect("defect1")
				.patientId(patient.getId()).weight(1.0).build();
	}

	private AdmissionsTestUtils() {
		// empty utility class constructor
	}
}
