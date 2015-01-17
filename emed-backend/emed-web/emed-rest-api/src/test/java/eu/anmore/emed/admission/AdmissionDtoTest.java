package eu.anmore.emed.admission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

/**
 * Test class for {@link AdmissionDto}.
 * 
 * @author mmiedzinski
 */
public class AdmissionDtoTest {

	@Test
	public void shouldCreateAdmissionDtoFromAdmission() {
		// given
		Admission admission = new Admission(ID, PATIENT_ID, ADMISSION_DATE, BLOOD, HEARTH_DEFECT, WEIGHT, BODY_AREA,
				HEIGHT, DEATH, DISCHARGE_PLACE, DISCHARGE_DATE, ADMISSION_REASON);

		// when
		AdmissionDto admissionDto = AdmissionDto.valueOf(admission);

		// then
		assertNotNull(admissionDto);
		assertEquals(admission.getPatientId(), admissionDto.getPatientId());
		assertEquals(admission.getBlood(), admissionDto.getBlood());
		assertEquals(admission.getDischargePlace(), admissionDto.getDischargePlace());
		assertEquals(admission.getHearthDefect(), admissionDto.getHearthDefect());
		assertEquals(admission.getAdmissionDate(), admissionDto.getAdmissionDate());
		assertEquals(admission.getBodyArea(), admissionDto.getBodyArea());
		assertEquals(admission.getDischargeDate(), admissionDto.getDischargeDate());
		assertEquals(admission.getHeight(), admissionDto.getHeight());
		assertEquals(admission.getId(), admissionDto.getId());
		assertEquals(admission.getWeight(), admissionDto.getWeight());
		assertEquals(admission.getAdmissionReason(), admissionDto.getAdmissionReason());
	}

	@Test
	public void shouldCreateAdmissionFromDto() {
		// given
		Admission admission = new Admission(ID, PATIENT_ID, ADMISSION_DATE, BLOOD, HEARTH_DEFECT, WEIGHT, BODY_AREA,
				HEIGHT, DEATH, DISCHARGE_PLACE, DISCHARGE_DATE, ADMISSION_REASON);
		AdmissionDto admissionDto = AdmissionDto.valueOf(admission);

		// when
		Admission result = admissionDto.asAdmission();

		// then
		assertNotNull(result);
		assertEquals(admission.getPatientId(), result.getPatientId());
		assertEquals(admission.getBlood(), result.getBlood());
		assertEquals(admission.getDischargePlace(), result.getDischargePlace());
		assertEquals(admission.getHearthDefect(), result.getHearthDefect());
		assertEquals(admission.getAdmissionDate(), result.getAdmissionDate());
		assertEquals(admission.getBodyArea(), result.getBodyArea());
		assertEquals(admission.getDischargeDate(), result.getDischargeDate());
		assertEquals(admission.getHeight(), result.getHeight());
		assertEquals(admission.getId(), result.getId());
		assertEquals(admission.getWeight(), result.getWeight());
		assertEquals(admission.getAdmissionReason(), result.getAdmissionReason());
	}

	private static final int ID = 2;

	private static final int PATIENT_ID = 1;

	private static final Date ADMISSION_DATE = new Date();

	private static final String BLOOD = "0";

	private static final String HEARTH_DEFECT = "sampleDefect";

	private static final double WEIGHT = 99;

	private static final double BODY_AREA = 77;

	private static final double HEIGHT = 87;

	private static final boolean DEATH = true;

	private static final String DISCHARGE_PLACE = "samplePlace";

	private static final String ADMISSION_REASON = "sampleReason";

	private static final Date DISCHARGE_DATE = new Date();
}
