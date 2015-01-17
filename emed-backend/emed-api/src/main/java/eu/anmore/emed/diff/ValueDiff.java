package eu.anmore.emed.diff;

/**
 * Difference between two values.
 *
 * @author Grzegorz Lipecki
 * @param <T>
 *            value type
 */
public final class ValueDiff<T> {

	public static <T> ValueDiff<T> notChanged() {
		return new ValueDiff<T>();
	}

	public static <T> ValueDiff<T> changed(final T newValue) {
		return new ValueDiff<T>(newValue);
	}

	public static <T> ValueDiff<T> changed(final T newValue, final T oldValue) {
		return new ValueDiff<T>(newValue, oldValue);
	}

	public boolean isChanged() {
		return changed;
	}

	public T getNewValue() {
		return newValue;
	}

	public boolean hasOldValue() {
		return oldValue != null;
	}

	public T getOldValue() {
		return oldValue;
	}

	private ValueDiff() {
		this.changed = false;
		this.newValue = null;
		this.oldValue = null;
	}

	private ValueDiff(final T newValue) {
		this.changed = true;
		this.newValue = newValue;
		this.oldValue = null;
	}

	private ValueDiff(final T newValue, final T oldValue) {
		this.changed = true;
		this.newValue = newValue;
		this.oldValue = oldValue;
	}

	private final Boolean changed;

	private final T newValue;

	private final T oldValue;

}
