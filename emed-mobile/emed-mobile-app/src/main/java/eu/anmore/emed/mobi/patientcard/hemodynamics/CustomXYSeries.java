package eu.anmore.emed.mobi.patientcard.hemodynamics;

import org.achartengine.model.XYSeries;

public class CustomXYSeries extends XYSeries {

	/**
	 * Builds a new XY series.
	 * 
	 * @param title
	 *            the series title.
	 */
	public CustomXYSeries(final String title) {
		super(title, 0);
	}

	/**
	 * Builds a new XY series.
	 * 
	 * @param title
	 *            the series title.
	 * @param scaleNumber
	 *            the series scale number
	 */
	public CustomXYSeries(final String title, final int scaleNumber) {
		super(title, scaleNumber);
	}

	private static final long serialVersionUID = 302289730542338487L;

}
