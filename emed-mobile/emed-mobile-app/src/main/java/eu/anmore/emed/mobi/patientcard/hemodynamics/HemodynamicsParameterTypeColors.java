package eu.anmore.emed.mobi.patientcard.hemodynamics;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Color;
import eu.anmore.emed.observation.PredefinedObservationType;

public class HemodynamicsParameterTypeColors {

	public static Integer getColor(final String key) {
		for (final Entry<PredefinedObservationType, Integer> entry : H_PARAM_COLOR.entrySet()) {
			if (entry.getKey().getKey().equals(key)) {
				return getColor(entry.getValue());
			}
		}
		return getColor(Color.WHITE);
	}

	public static Integer getColor(final PredefinedObservationType type) {
		return getColor(H_PARAM_COLOR.get(type));
	}

	private static Integer getColor(final Integer color) {
		return color != null ? color : Color.WHITE;
	}

	private static final ConcurrentHashMap<PredefinedObservationType, Integer> H_PARAM_COLOR = new ConcurrentHashMap<PredefinedObservationType, Integer>() {

		{
			put(PredefinedObservationType.HR, Color.WHITE);
			put(PredefinedObservationType.SAT, Color.GREEN);
			put(PredefinedObservationType.ABP, Color.RED);
			put(PredefinedObservationType.CVP, Color.BLUE);
			put(PredefinedObservationType.PAP, Color.MAGENTA);
			put(PredefinedObservationType.LAP, Color.YELLOW);
			put(PredefinedObservationType.CO, Color.CYAN);
		}

		private static final long serialVersionUID = 1631049359198633446L;

	};

}
