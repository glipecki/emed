package eu.anmore.emed.api.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.anmore.emed.diff.ValueDiff;

/**
 * Tests for {@link ValueDiff}.
 *
 * @author Grzegorz Lipecki
 */
public class ValueDiffTest {

	@Before
	public void setup() {

	}

	@Test
	public void shouldSetChangedState() {
		// given

		// when
		final ValueDiff<String> diff = ValueDiff.changed(NEW_VALUE);

		// then
		assertTrue(diff.isChanged());
	}

	@Test
	public void shouldServerDiffForValue() {
		// given

		// when
		final ValueDiff<String> diff = ValueDiff.changed(NEW_VALUE);

		// then
		assertTrue(diff.isChanged());
		assertEquals(NEW_VALUE, diff.getNewValue());
	}

	@Test
	public void shouldServeOldValue() {
		// given

		// when
		final ValueDiff<String> diff = ValueDiff.changed(NEW_VALUE, OLD_VALUE);

		// then
		assertTrue(diff.isChanged());
		assertEquals(NEW_VALUE, diff.getNewValue());
		assertTrue(diff.hasOldValue());
		assertEquals(OLD_VALUE, diff.getOldValue());
	}

	@Test
	public void shouldntHasOldValueIfNoneSpecified() {
		// given

		// when
		final ValueDiff<String> diff = ValueDiff.changed(NEW_VALUE);

		// then
		assertTrue(diff.isChanged());
		assertEquals(NEW_VALUE, diff.getNewValue());
	}

	@Test
	public void shouldCanRemainNotChanged() {
		// given

		// when
		final ValueDiff<String> diff = ValueDiff.notChanged();

		// then
		assertFalse(diff.isChanged());
		assertFalse(diff.hasOldValue());
	}

	private static final String NEW_VALUE = "new value";

	private static final String OLD_VALUE = "old value";

}
