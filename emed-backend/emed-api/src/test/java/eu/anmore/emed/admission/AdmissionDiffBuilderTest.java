package eu.anmore.emed.admission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * Test class for {@link AdmissionDiffBuilder}.
 * 
 * @author mmiedzinski
 */
public class AdmissionDiffBuilderTest {

	@Test
	public void shouldDescribeAdmissionDateChange() {
		// given
		final Date newDate = new Date();

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().admissionDate(newDate).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newDate, diff.getAdmissionDate().getNewValue());
	}

	@Test
	public void shouldDescribeAdmissionDateChangeWithOldValue() {
		// given
		final Date newDate = new Date();
		final Date oldDate = new LocalDate(1999, 2, 2).toDate();
		final Admission admission = new Admission(0, 0, oldDate, null, null, null, null, null, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).admissionDate(newDate).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newDate, diff.getAdmissionDate().getNewValue());
		assertEquals(oldDate, diff.getAdmissionDate().getOldValue());
	}

	@Test
	public void shouldDescribeBloodChange() {
		// given
		final String newBlood = "0";

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().blood(newBlood).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newBlood, diff.getBlood().getNewValue());
	}

	@Test
	public void shouldDescribeBloodChangeWithOldValue() {
		// given
		final String newBlood = "0";
		final String oldBlood = "A";
		final Admission admission = new Admission(0, 0, null, oldBlood, null, null, null, null, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).blood(newBlood).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newBlood, diff.getBlood().getNewValue());
		assertEquals(oldBlood, diff.getBlood().getOldValue());
	}

	@Test
	public void shouldDescribeBodyAreaChange() {
		// given
		final Double newValue = 2.0;

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().bodyArea(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getBodyArea().getNewValue());
	}

	@Test
	public void shouldDescribeBodyAreaChangeWithOldValue() {
		// given
		final Double newValue = 2.0;
		final Double oldValue = 3.0;
		final Admission admission = new Admission(0, 0, null, null, null, null, oldValue, null, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).bodyArea(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getBodyArea().getNewValue());
		assertEquals(oldValue, diff.getBodyArea().getOldValue());
	}

	@Test
	public void shouldDescribeDeathChange() {
		// given
		final boolean newValue = true;

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().death(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getDeath().getNewValue());
	}

	@Test
	public void shouldDescribeDeatchChangeWithOldValue() {
		// given
		final boolean newValue = true;
		final Admission admission = new Admission(0, 0, null, null, null, null, null, null, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).death(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertSame(newValue, diff.getDeath().getNewValue());
		assertSame(false, diff.getDeath().getOldValue());
	}

	@Test
	public void shouldDescribeDischargeDateChange() {
		// given
		final Date newDate = new Date();

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().dischargeDate(newDate).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newDate, diff.getDischargeDate().getNewValue());
	}

	@Test
	public void shouldDescribeDischargeDateChangeWithOldValue() {
		// given
		final Date newDate = new Date();
		final Date oldDate = new LocalDate(1999, 2, 2).toDate();
		final Admission admission = new Admission(0, 0, oldDate, null, null, null, null, null, false, null, oldDate,
				null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).dischargeDate(newDate).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newDate, diff.getDischargeDate().getNewValue());
		assertEquals(oldDate, diff.getDischargeDate().getOldValue());
	}

	@Test
	public void shouldDescribeDischargePlaceChange() {
		// given
		final String newValue = "place1";

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().dischargePlace(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getDischargePlace().getNewValue());
	}

	@Test
	public void shouldDescribeDischargePlaceChangeWithOldValue() {
		// given
		final String newValue = "place1";
		final String oldValue = "place2";
		final Admission admission = new Admission(0, 0, null, null, null, null, null, null, false, oldValue, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).dischargePlace(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getDischargePlace().getNewValue());
		assertEquals(oldValue, diff.getDischargePlace().getOldValue());
	}

	@Test
	public void shouldDescribeHeightChange() {
		// given
		final Double newValue = 2.0;

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().height(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getHeight().getNewValue());
	}

	@Test
	public void shouldDescribeHeightChangeWithOldValue() {
		// given
		final Double newValue = 2.0;
		final Double oldValue = 3.0;
		final Admission admission = new Admission(0, 0, null, null, null, null, null, oldValue, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).height(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getHeight().getNewValue());
		assertEquals(oldValue, diff.getHeight().getOldValue());
	}

	@Test
	public void shouldDescribeWeightChange() {
		// given
		final Double newValue = 2.0;

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().weight(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getWeight().getNewValue());
	}

	@Test
	public void shouldDescribeWeightChangeWithOldValue() {
		// given
		final Double newValue = 2.0;
		final Double oldValue = 3.0;
		final Admission admission = new Admission(0, 0, null, null, null, oldValue, null, null, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).weight(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getWeight().getNewValue());
		assertEquals(oldValue, diff.getWeight().getOldValue());
	}

	@Test
	public void shouldDescribePatientIdChange() {
		// given
		final int newValue = 2;

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().patientId(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertSame(newValue, diff.getPatientId().getNewValue());
	}

	@Test
	public void shouldDescribePatientIdChangeWithOldValue() {
		// given
		final int newValue = 2;
		final int oldValue = 3;
		final Admission admission = new Admission(0, oldValue, null, null, null, null, null, null, false, null, null,
				null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).patientId(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertSame(newValue, diff.getPatientId().getNewValue());
		assertSame(oldValue, diff.getPatientId().getOldValue());
	}

	@Test
	public void shouldDescribeHearthDefectChange() {
		// given
		final String newValue = "defect1";

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().hearthDefect(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getHearthDefect().getNewValue());
	}

	@Test
	public void shouldDescribeDischargeHearthDefectWithOldValue() {
		// given
		final String newValue = "defect1";
		final String oldValue = "defect2";
		final Admission admission = new Admission(0, 0, null, null, oldValue, null, null, null, false, null, null, null);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).hearthDefect(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getHearthDefect().getNewValue());
		assertEquals(oldValue, diff.getHearthDefect().getOldValue());
	}

	@Test
	public void shouldDescribeAdmissionReasonChange() {
		// given
		final String newValue = "reason1";

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get().admissionReason(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getAdmissionReason().getNewValue());
	}

	@Test
	public void shouldDescribeAdmissionReasonWithOldValue() {
		// given
		final String newValue = "reason1";
		final String oldValue = "reason2";
		final Admission admission = new Admission(0, 0, null, null, null, null, null, null, false, null, null, oldValue);

		// when
		final AdmissionDiff diff = AdmissionDiffBuilder.get(admission).admissionReason(newValue).build();

		// then
		assertTrue(diff.hasChanges());
		assertEquals(newValue, diff.getAdmissionReason().getNewValue());
		assertEquals(oldValue, diff.getAdmissionReason().getOldValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenNoChange() {
		// given
		final Admission admission = new Admission(0, 0, null, null, null, null, null, null, false, null, null, null);

		// when
		AdmissionDiffBuilder.get(admission).build();

		// then
		// exception should be throw
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldIgnoresetTheSameValues() {
		// given
		final Date sampleDate = new Date();
		final String sampleText = "sample";
		final Admission admission = new Admission(1, 1, sampleDate, sampleText, sampleText, 1.0, 1.0, 1.0, false,
				sampleText, sampleDate, sampleText);

		// when
		AdmissionDiffBuilder.get(admission).admissionDate(sampleDate).blood(sampleText).bodyArea(1.0).death(false)
				.dischargeDate(sampleDate).dischargePlace(sampleText).height(1.0).hearthDefect(sampleText).patientId(1)
				.weight(1.0).admissionReason(sampleText).build();

		// then
		// exception should be throw
	}
}
