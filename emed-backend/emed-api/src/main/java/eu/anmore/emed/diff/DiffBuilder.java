package eu.anmore.emed.diff;

/**
 * Base diff builder.
 * 
 * @author glipecki
 * 
 * @param <T>
 *            object type to get diff
 * @param <V>
 *            diff type
 */
public abstract class DiffBuilder<T, V extends ObjectDiff<T>> {

	public V build() {
		if (!diff.hasChanges()) {
			throw new IllegalArgumentException("Diff has to describe change of at least one parameter.");
		}
		return diff;
	}

	protected DiffBuilder(final V diff) {
		this.object = null;
		this.diff = diff;
	}

	protected DiffBuilder(final V diff, final T object) {
		this(diff);
		this.object = object;
	}

	protected boolean equals(final Object objectOne, final Object objectTwo) {
		if ((objectOne == null) && (objectTwo == null)) {
			return true;
		} else {
			if (objectOne != null) {
				return objectOne.equals(objectTwo);
			} else {
				return objectTwo.equals(objectOne);
			}
		}
	}

	protected <A> ValueDiff<A> getAttributeDiff(final A newValue, final A value) {
		if (object == null) {
			return ValueDiff.changed(newValue);
		} else if (!equals(value, newValue)) {
			return ValueDiff.changed(newValue, value);
		}
		return ValueDiff.notChanged();
	}

	protected V diff;

	protected T object;

}
