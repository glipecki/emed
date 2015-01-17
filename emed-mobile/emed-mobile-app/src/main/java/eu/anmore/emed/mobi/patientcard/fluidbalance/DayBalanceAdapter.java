package eu.anmore.emed.mobi.patientcard.fluidbalance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.widget.TextView;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;
import eu.anmore.utils.DateUtil;

public class DayBalanceAdapter extends GroupBalanceAdapter {

	public DayBalanceAdapter(final TotalBalanceAdapter totalBalanceAdapter, final ObservationGroup group,
			final TextView balanceTextView, final BalanceDirection balanceDirection, final Date day) {
		super(totalBalanceAdapter, group, balanceTextView, balanceDirection);
		this.day = day;
	}

	public DayBalanceAdapter(final TotalBalanceAdapter totalBalanceAdapter, final ObservationGroup group,
			final TextView balanceTextView, final BalanceDirection balanceDirection, final BalanceType balanceType,
			final Date day) {
		super(totalBalanceAdapter, group, balanceTextView, balanceDirection, balanceType);
		this.day = day;
	}

	@Override
	public void setObservations(final List<Observation> observations) {
		final List<Observation> dayObservations = new ArrayList<Observation>();
		for (final Observation observation : observations) {
			if (DateUtil.isSameDay(day, observation.getDate())) {
				dayObservations.add(observation);
			}
		}
		super.setObservations(dayObservations);
	}

	private final Date day;

}
