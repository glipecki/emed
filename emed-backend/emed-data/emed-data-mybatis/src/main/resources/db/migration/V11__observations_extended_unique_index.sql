DROP INDEX IF EXISTS uq_observations_observation_time;

CREATE UNIQUE INDEX uq_observations_observation_time ON observations (patient_id, type, date, admission_id);

