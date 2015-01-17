package eu.anmore.emed.mobi.observation;

import static java.lang.Double.valueOf;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.ApplicationState;
import eu.anmore.emed.mobi.MobiTaskRunner;
import eu.anmore.emed.mobi.R;
import eu.anmore.emed.mobi.SecurityHelper;
import eu.anmore.emed.mobi.observation.AddObservationPresenter.AddObservationView.AddObservationsClickHandler;
import eu.anmore.emed.mobi.observation.AddObservationsTask.AddObservationsResult;
import eu.anmore.emed.observation.Observation;
import eu.anmore.emed.observation.ObservationType;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.logger.LogCatLogger;
import eu.anmore.mvpdroid.logger.Logger;
import eu.anmore.mvpdroid.presenter.Presenter;
import eu.anmore.mvpdroid.toast.AndroidToast;

/**
 * Okno dodawania obserwacji.
 * 
 * @author glipecki
 */
public class AddObservationPresenter extends Presenter<AddObservationPresenter.AddObservationView> {

	@Inject
	public AddObservationPresenter(final AddObservationView view, final MobiTaskRunner taskRunner,
			final AndroidToast androidToast, final SecurityHelper securityHelper, final ApplicationState appState) {
		super(view);
		this.taskRunner = taskRunner;
		this.androidToast = androidToast;
		this.securityHelper = securityHelper;
		this.appState = appState;
	}

	public interface AddObservationView extends eu.anmore.mvpdroid.view.View {

		void setObservationTypes(Integer activePatientId, List<ObservationType> observationTypes);

		void setAddObservationsClickHandler(AddObservationsClickHandler clickHandler);

		interface AddObservationsClickHandler {

			void onAddObservation(Date date, Map<ObservationType, String> values);

		}

	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		final Integer activePatientId = getActivePatientId();
		final List<ObservationType> observationTypes = getObservationTypes();
		log.debug("Prepare add observation dialog for: [observationTypes={}, activePatientId={}]", observationTypes,
				activePatientId);
		getView().setObservationTypes(activePatientId, observationTypes);
		getView().setAddObservationsClickHandler(new AddObservationsClickHandler() {

			@Override
			public void onAddObservation(final Date date, final Map<ObservationType, String> values) {
				log.debug("[date=[{}], values=[{}]]", date, values);

				final List<Observation> observationsToAdd = new ArrayList<Observation>();
				for (final Entry<ObservationType, String> entry : values.entrySet()) {
					switch (entry.getKey().getType()) {
					case DOMAIN:
						addStringObservation(observationsToAdd, entry.getKey(), date, entry.getValue());
						break;
					case NUMERIC:
						addDoubleObservation(observationsToAdd, entry.getKey(), date, entry.getValue());
						break;
					default:
						break;

					}
				}

				// TODO (glipecki):
				// 1. validate
				// 2. save
				// 2a. show error
				// 3. close dialog
				// 4. refresh graph

				taskRunner.executeWithRetry(AddObservationsTask.action(activePatientId, observationsToAdd),
						new TaskCallback<AddObservationsTask.AddObservationsResult>() {

							@Override
							public void onSuccess(final AddObservationsResult taskResult) {
								androidToast.showShortToast(R.string.add_observation_success);
								getProxy().finish();
							}

							@Override
							public void onFailure(final Throwable caught) {
								androidToast.showShortToast(R.string.add_observation_error);
								log.error("Problem sending observations [{}]", caught.getMessage());
							}
						});
			}

			private void addStringObservation(final List<Observation> observationsToAdd, final ObservationType type,
					final Date date, final String rawValue) {
				if (hasStringValue(rawValue)) {
					observationsToAdd.add(new Observation(type.getKey(), date, format(type.getFormat(), rawValue)));
				}
			}

			private void addDoubleObservation(final List<Observation> observationsToAdd, final ObservationType type,
					final Date date, String rawValue) {
				if (hasStringValue(rawValue)) {
					rawValue = rawValue.replace(",", ".");
					if (rawValue.matches("^[-]?[0-9]+[\\.]?[0-9]*$")) {
						observationsToAdd.add(new Observation(type.getKey(), date, format(type.getFormat(),
								valueOf(rawValue)).replace(",", ".")));
					}
				}
			}

			private boolean hasStringValue(final String rawValue) {
				return rawValue != null && !rawValue.isEmpty();
			}

		});
	}

	@Override
	protected void onResume() {
		securityHelper.verifyAuthentication();
	}

	private static final Logger log = LogCatLogger.getLogger(AddObservationPresenter.class);

	private Integer getActivePatientId() {
		return getIntentParameter(AddObservationProxy.ACTIVE_PATIENT_ID, Integer.class);
	}

	private List<ObservationType> getObservationTypes() {
		final Intent intent = getIntent();
		if (!intent.getExtras().containsKey(AddObservationProxy.OBSERVATION_TYPES)) {
			// TODO (glipecki): error
		}

		final List<ObservationType> observationTypes = new ArrayList<ObservationType>();
		final String rawObservationTypes = ((String) getIntentParameter(intent)).trim();
		final String[] observationParts = rawObservationTypes.split(AddObservationProxy.OBSERVATION_TYPES_SEPARATOR);
		for (final String observataionType : observationParts) {
			if (!observataionType.isEmpty()) {
				final ObservationType type = appState.getObservationTypeFor(observataionType);
				if (type != null) {
					observationTypes.add(type);
				}
			}
		}

		return observationTypes;
	}

	private Object getIntentParameter(final Intent intent) {
		return intent.getExtras().get(AddObservationProxy.OBSERVATION_TYPES);
	}

	private final ApplicationState appState;

	private final SecurityHelper securityHelper;

	private final AndroidToast androidToast;

	private final MobiTaskRunner taskRunner;

}
