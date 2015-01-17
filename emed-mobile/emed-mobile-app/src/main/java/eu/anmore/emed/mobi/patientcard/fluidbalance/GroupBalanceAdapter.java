package eu.anmore.emed.mobi.patientcard.fluidbalance;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.widget.TextView;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationGroup;

public class GroupBalanceAdapter {

	public enum BalanceType {
		ALL {
			@Override
			public double getBalance(final List<Observation> observations, final ObservationGroup group) {
				double balance = 0d;
				for (final Observation observation : observations) {
					if (observation.getGroupKey().equals(group.getKey()) && isNumeric(observation.getValue())) {
						balance += Double.parseDouble(observation.getValue());
					}
				}
				return balance;
			}
		},
		LAST {
			@Override
			public double getBalance(final List<Observation> observations, final ObservationGroup group) {
				Collections.sort(observations, Observation.byDateComparator());

				final Map<String, Double> typeInGroupBalance = new HashMap<String, Double>();
				for (final Observation observation : observations) {
					if (observation.getGroupKey().equals(group.getKey()) && isNumeric(observation.getValue())) {
						typeInGroupBalance.put(observation.getTypeKey(), Double.parseDouble(observation.getValue()));
					}
				}

				double balance = 0;
				for (final Entry<String, Double> e : typeInGroupBalance.entrySet()) {
					balance += e.getValue();
				}
				return balance;
			}
		};

		public abstract double getBalance(final List<Observation> observations, ObservationGroup group);

		public boolean isNumeric(final String s) {
			return s.matches("[-+]?\\d*\\.?\\d+");
		}

	}

	public GroupBalanceAdapter(final TotalBalanceAdapter totalBalanceAdapter, final ObservationGroup group,
			final TextView balanceTextView, final BalanceDirection balanceDirection) {
		this(totalBalanceAdapter, group, balanceTextView, balanceDirection, BalanceType.ALL);
	}

	public GroupBalanceAdapter(final TotalBalanceAdapter totalBalanceAdapter, final ObservationGroup group,
			final TextView balanceTextView, final BalanceDirection balanceDirection, final BalanceType balanceType) {
		this.totalBalanceAdapter = totalBalanceAdapter;
		this.group = group;
		this.balanceTextView = balanceTextView;
		this.bilanceDirection = balanceDirection;
		this.balanceType = balanceType;
	}

	public void setObservations(final List<Observation> observations) {
		double balance = balanceType.getBalance(observations, group);
		if (minusBalanceOver0(balance) || plusBalanceUnder0(balance)) {
			balance *= -1;
		}
		balanceTextView.setText(String.format("%.2f", balance));
		totalBalanceAdapter.setBalance(group, bilanceDirection, balance);
	}

	private boolean plusBalanceUnder0(final double balance) {
		return bilanceDirection.equals(BalanceDirection.PLUS) && balance < 0;
	}

	private boolean minusBalanceOver0(final double balance) {
		return bilanceDirection.equals(BalanceDirection.MINUS) && balance > 0;
	}

	private final BalanceType balanceType;

	private final BalanceDirection bilanceDirection;

	private final ObservationGroup group;

	private final TextView balanceTextView;

	private final TotalBalanceAdapter totalBalanceAdapter;

}
