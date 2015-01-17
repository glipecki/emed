package eu.anmore.emed.mobi.parameters;

import android.os.Bundle;
import android.widget.ListAdapter;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes.LoadCustomObservationTypesResult;
import eu.anmore.emed.mobi.parameters.CustomObservationsPresenter.CustomObservationsView.AddCustomObservationsClickHandler;
import eu.anmore.emed.mobi.parameters.CustomObservationsTaskHandler.AddCustomObservationAction;
import eu.anmore.emed.mobi.parameters.CustomObservationsTaskHandler.AddCustomObservationResult;
import eu.anmore.emed.observation.CustomObservationType;
import eu.anmore.mvpdroid.async.TaskCallback;
import eu.anmore.mvpdroid.presenter.Presenter;
import eu.anmore.mvpdroid.task.runner.TaskRunner;
import eu.anmore.mvpdroid.toast.AndroidToast;

public class CustomObservationsPresenter extends Presenter<CustomObservationsPresenter.CustomObservationsView> {

	@Inject
	public CustomObservationsPresenter(final CustomObservationsView customObservationsView,
			final TaskRunner taskRunner, final AndroidToast toast,
			final CustomObservationsAdapter customObservationsAdapter) {
		super(customObservationsView);
		this.taskRunner = taskRunner;
		this.toast = toast;
		this.customObservationsAdapter = customObservationsAdapter;
	}

	public interface CustomObservationsView extends eu.anmore.mvpdroid.view.View {

		void setCustomObservationsAdapter(ListAdapter customObservationsAdapter);

		void setAddCustomObservationClickHandler(AddCustomObservationsClickHandler clickHandler);

		void showObservationsList();

		interface AddCustomObservationsClickHandler {

			void onAddCustomParameter(CustomObservationType customObservationType);

		}

	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getView().setCustomObservationsAdapter(customObservationsAdapter);

		getView().setAddCustomObservationClickHandler(new AddCustomObservationsClickHandler() {

			@Override
			public void onAddCustomParameter(final CustomObservationType customObservationType) {
				taskRunner.executeWithRetry(new AddCustomObservationAction(customObservationType),
						new TaskCallback<AddCustomObservationResult>() {

							@Override
							public void onSuccess(final AddCustomObservationResult taskResult) {
								toast.showShortToast("Dodano parametr użytkownika");
								getProxy().finish();
							}

							@Override
							public void onFailure(final Throwable caught) {
								toast.showShortToast("Nie udało się dodać parametru użytkownika");
							}
						});
			}

		});

		taskRunner.executeWithRetry(LoadCustomObservationTypes.action(),
				new TaskCallback<LoadCustomObservationTypesResult>() {

					@Override
					public void onSuccess(final LoadCustomObservationTypesResult taskResult) {
						setCustomObservations(taskResult);
					}

					@Override
					public void onFailure(final Throwable caught) {
						toast.showShortToast("Nie udało się pobrać parametrów użytkownika");
						getProxy().finish();
					}

				});
	}

	private void setCustomObservations(final LoadCustomObservationTypesResult taskResult) {
		customObservationsAdapter.clear();
		customObservationsAdapter.addAll(taskResult.getTypes());
		getView().showObservationsList();
	}

	private final CustomObservationsAdapter customObservationsAdapter;

	private final AndroidToast toast;

	private final TaskRunner taskRunner;

}
