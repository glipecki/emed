package eu.anmore.emed.helpers;

import java.util.Date;

/**
 * Helps cloning objects.
 * <p>
 * Helps with defensive copying of mutable objects.
 * </p>
 * 
 * @author Grzegorz Lipecki
 */
public final class ObjectsCloner {

	public static Date cloneDate(final Date originalDate) {
		if (originalDate != null) {
			return new Date(originalDate.getTime());
		} else {
			return null;
		}
	}

	private ObjectsCloner() {
		// intentionally left blank.
	}

}
