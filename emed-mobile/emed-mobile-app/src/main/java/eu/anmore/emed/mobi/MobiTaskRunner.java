package eu.anmore.emed.mobi;

import android.app.Activity;

import com.google.inject.Inject;

import eu.anmore.emed.mobi.login.authenticate.AuthenticateTask.AuthenticateAction;
import eu.anmore.emed.mobi.login.authenticate.AuthenticateTaskHandler;
import eu.anmore.emed.mobi.observation.AddObservationsTask.AddObservationsAction;
import eu.anmore.emed.mobi.observation.LoadCustomObservationTypes.LoadCustomObservationTypesAction;
import eu.anmore.emed.mobi.observation.LoadObservationNorms.LoadObservationNormsAction;
import eu.anmore.emed.mobi.observation.ObservationsTaskHandler;
import eu.anmore.emed.mobi.parameters.CustomObservationsTaskHandler;
import eu.anmore.emed.mobi.parameters.CustomObservationsTaskHandler.AddCustomObservationAction;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler;
import eu.anmore.emed.mobi.patientcard.LoadAdmisstionTypesTaskHandler.LoadAdmisstionTypesAction;
import eu.anmore.emed.mobi.patientcard.LoadCustomObservationTypesTaskHandler;
import eu.anmore.emed.mobi.patientcard.LoadObservationNormsTaskHandler;
import eu.anmore.emed.mobi.patientcard.LoadPatientInfoTask.LoadPatientInfoAction;
import eu.anmore.emed.mobi.patientcard.LoadPatientInfoTaskHandler;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SaveAdmissionTypesTaskHandler;
import eu.anmore.emed.mobi.patientcard.addmisontypes.SaveAdmissionTypesTaskHandler.SaveAdmissionTypesAction;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTask.GetObservationsAction;
import eu.anmore.emed.mobi.patientcard.observations.GetObservationsTaskHandler;
import eu.anmore.emed.mobi.patients.LoadPatientsTask.LoadPatientsAction;
import eu.anmore.emed.mobi.patients.LoadPatientsTaskHandler;
import eu.anmore.mvpdroid.task.runner.AndroidTaskRunner;

/**
 * Application task runner configuration.
 * 
 * @author Grzegorz Lipecki
 */
public class MobiTaskRunner extends AndroidTaskRunner {

	@Inject
	public MobiTaskRunner(final Activity activity) {
		super(activity);
	}

	@Override
	protected void configure() {
		bindAction(AuthenticateAction.class).toHandler(AuthenticateTaskHandler.class);
		bindAction(LoadPatientsAction.class).toHandler(LoadPatientsTaskHandler.class);
		bindAction(LoadPatientInfoAction.class).toHandler(LoadPatientInfoTaskHandler.class);
		bindAction(GetObservationsAction.class).toHandler(GetObservationsTaskHandler.class);
		bindAction(AddObservationsAction.class).toHandler(ObservationsTaskHandler.class);
		bindAction(LoadObservationNormsAction.class).toHandler(LoadObservationNormsTaskHandler.class);
		bindAction(LoadCustomObservationTypesAction.class).toHandler(LoadCustomObservationTypesTaskHandler.class);
		bindAction(AddCustomObservationAction.class).toHandler(CustomObservationsTaskHandler.class);
		bindAction(LoadAdmisstionTypesAction.class).toHandler(LoadAdmisstionTypesTaskHandler.class);
		bindAction(SaveAdmissionTypesAction.class).toHandler(SaveAdmissionTypesTaskHandler.class);
	}

}
