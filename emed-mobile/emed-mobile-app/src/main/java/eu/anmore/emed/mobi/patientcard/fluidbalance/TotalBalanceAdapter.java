package eu.anmore.emed.mobi.patientcard.fluidbalance;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.widget.TextView;
import eu.anmore.emed.observation.ObservationGroup;

public class TotalBalanceAdapter {

	public TotalBalanceAdapter(final TextView balanceField) {
		this.balanceField = balanceField;
		refreshBalance();
	}

	public TotalBalanceAdapter(final TotalBalanceAdapter parentBalanceAdapter, final String groupId,
			final TextView balanceField) {
		this.parentBalanceAdapter = parentBalanceAdapter;
		this.groupId = groupId;
		this.balanceField = balanceField;
	}

	public void setBalance(final ObservationGroup group, final BalanceDirection balanceDirection, final double balance) {
		balances.put(group.getKey(), balance);
		refreshBalance();
	}

	public void setBalance(final String groupId, final double balance) {
		balances.put(groupId, balance);
		refreshBalance();
	}

	private void refreshBalance() {
		double balance = 0d;
		for (final Entry<String, Double> balanceEntry : balances.entrySet()) {
			balance += balanceEntry.getValue();
		}
		if (parentBalanceAdapter != null) {
			parentBalanceAdapter.setBalance(groupId, balance);
		}
		balanceField.setText(String.format("%.2f", balance));
	}

	private String groupId;

	private TotalBalanceAdapter parentBalanceAdapter;

	private final Map<String, Double> balances = new HashMap<String, Double>();

	private final TextView balanceField;

}
