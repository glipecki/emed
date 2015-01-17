package eu.anmore.emed.mobi.patientcard.hemodynamics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.achartengine.chart.TimeChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class CustomTimeChart extends TimeChart {

	/**
	 * Builds a new time chart instance.
	 * 
	 * @param dataset
	 *            the multiple series dataset
	 * @param renderer
	 *            the multiple series renderer
	 */
	public CustomTimeChart(final XYMultipleSeriesDataset dataset, final XYMultipleSeriesRenderer renderer) {
		super(dataset, renderer);
	}

	@Override
	protected void drawXTextLabels(final Double[] xTextLabelLocations, final Canvas canvas, final Paint paint,
			final boolean showLabels, final int left, final int top, final int bottom, final double xPixelsPerUnit,
			final double minX, final double maxX) {
		final Paint textPaint = new Paint(paint);
		textPaint.setTypeface(Typeface.create("Helvetica", Typeface.BOLD));
		textPaint.setColor(Color.RED);

		final boolean showCustomTextGridX = mRenderer.isShowCustomTextGridX();
		if (showLabels) {
			paint.setColor(mRenderer.getXLabelsColor());
			for (final Double location : xTextLabelLocations) {
				if (minX <= location && location <= maxX) {
					final float xLabel = (float) (left + xPixelsPerUnit * (location.doubleValue() - minX));
					paint.setColor(mRenderer.getXLabelsColor());
					canvas.drawLine(xLabel, bottom, xLabel, DATE_PADDING + bottom + mRenderer.getLabelsTextSize() / 3,
							paint);
					drawText(canvas, mRenderer.getXTextLabel(location), xLabel,
							DATE_PADDING + bottom + mRenderer.getXLabelsPadding() + mRenderer.getLabelsTextSize() * 4
									/ 3, textPaint, mRenderer.getXLabelsAngle());
					if (showCustomTextGridX) {
						paint.setColor(mRenderer.getGridColor(0));
						canvas.drawLine(xLabel, bottom, xLabel, top, paint);
					}
				}
			}
		}
	}

	@Override
	protected void drawChartValuesText(final Canvas canvas, final XYSeries series, final XYSeriesRenderer renderer,
			final Paint paint, final List<Float> points, final int seriesIndex, final int startIndex) {
		super.drawChartValuesText(canvas, series, renderer, paint, points, seriesIndex, startIndex);
	}

	@Override
	protected List<Double> getXLabels(final double min, final double max, final int count) {
		// final List<Double> xlabels = super.getXLabels(min, max, count);
		final List<Double> xlabels = new ArrayList<Double>();

		for (final XYSeries series : mDataset.getSeries()) {
			for (int index = 0; index < series.getItemCount(); ++index) {
				final double xLabel = series.getX(index);
				if (xLabel >= min && xLabel <= max) {
					xlabels.add(xLabel);
				}
			}
		}

		Collections.sort(xlabels);
		return xlabels;
	}

	private static final int DATE_STROKE_WIDTH = 3;

	private static final int DATE_PADDING = 0;

	private static final long serialVersionUID = 8747407568059026599L;

}
