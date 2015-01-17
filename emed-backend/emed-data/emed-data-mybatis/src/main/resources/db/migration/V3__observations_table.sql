--
-- Observations
--

CREATE TABLE observations
		(
			observation_id		INT 			PRIMARY KEY,
			patient_id			INT				REFERENCES patients(patient_id),
			value	 			VARCHAR(20),
			type				VARCHAR(20),
			date				TIMESTAMP
		);

CREATE SEQUENCE observations_observation_id_seq;
ALTER TABLE observations ALTER COLUMN observation_id SET DEFAULT nextval('observations_observation_id_seq');