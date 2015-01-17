--
-- Users
--

CREATE TABLE users
		(
			user_id				INT 			PRIMARY KEY,
			first_name 			VARCHAR(150),
			surname 			VARCHAR(150),
			username			VARCHAR(150),
			password			VARCHAR(150),
			salt				VARchar(150)
		);

CREATE SEQUENCE users_user_id_seq;
ALTER TABLE users ALTER COLUMN user_id SET DEFAULT nextval('users_user_id_seq');

--
-- Permissions
--

CREATE TABLE permissions
		(
			permission_id		INT				PRIMARY KEY,
			permission 			VARCHAR(150)
		);

CREATE SEQUENCE permissions_permission_id_seq;
ALTER TABLE permissions ALTER COLUMN permission_id SET DEFAULT nextval('permissions_permission_id_seq');

CREATE TABLE user_permission
		(
			user_id				INT 			REFERENCES users(user_id),
			permission_id		INT				REFERENCES permissions(permission_id),
			PRIMARY KEY (user_id, permission_id)
		);

--
-- Patients
--

CREATE TABLE patients
		(
			patient_id			INT				PRIMARY KEY,
			first_name			VARCHAR(150),
			surname				VARCHAR(150),
			pesel				VARCHAR(11),
			birthday			DATE,
			sex					VARCHAR(5)
		);

CREATE SEQUENCE patients_patient_id_seq;
ALTER TABLE patients ALTER COLUMN patient_id SET DEFAULT nextval('patients_patient_id_seq');

--
-- Admissions
--

CREATE TABLE admissions
		(
			admission_id		INT				PRIMARY KEY,
			patient_id			INT				REFERENCES patients(patient_id),
			admission_date		DATE			DEFAULT now(),
			blood_type			VARCHAR(15),
			hearth_defect		VARCHAR(500),
			weight				NUMERIC(5,2),
			body_area			NUMERIC(6,2),
			height				NUMERIC(5,2),
			death				BOOLEAN,
			discharge_place		VARCHAR(500),
			discharge_date		DATE,
			admission_reason	TEXT
		);

CREATE SEQUENCE admissions_admission_id_seq;
ALTER TABLE admissions ALTER COLUMN admission_id SET DEFAULT nextval('admissions_admission_id_seq');

--
-- Parameters
--

CREATE TABLE parameter_types
		(
			parameter_type_id	INT				PRIMARY KEY,
			name 				VARCHAR(100)	UNIQUE,
			description			TEXT,
			norm_bottom			NUMERIC(5,2),
			norm_upper			NUMERIC(5,2),
			-- enum with value type, eq. TEXT, NUMERIC, etc
			value_type			VARCHAR(50)
		);

CREATE SEQUENCE parameter_types_parameter_type_id_seq;
ALTER TABLE parameter_types ALTER COLUMN parameter_type_id SET DEFAULT nextval('parameter_types_parameter_type_id_seq');

--
-- Observations
--

CREATE TABLE observations
		(
			observation_id 		INT				PRIMARY KEY,
			admission_id		INT				REFERENCES admissions(admission_id),
			parameter_type_id	INT				REFERENCES parameter_types(parameter_type_id),
			value				VARCHAR(100),
			observation_date	DATE,
			-- observer id
			user_id				INT				REFERENCES users(user_id)
		);

CREATE SEQUENCE observations_observation_id_seq;
ALTER TABLE observations ALTER COLUMN observation_id SET DEFAULT nextval('observations_observation_id_seq');

--
-- Parameter groups
--

CREATE TABLE parameter_groups
		(
			parameter_group_id	INT				PRIMARY KEY,
			name 				VARCHAR(150)	UNIQUE
		);

CREATE SEQUENCE parameter_groups_parameter_group_id_seq;
ALTER TABLE parameter_groups ALTER COLUMN parameter_group_id SET DEFAULT nextval('parameter_groups_parameter_group_id_seq');

CREATE TABLE parameter_type_parameter_group
		(
			parameter_type_id	INT				REFERENCES parameter_types(parameter_type_id),
			parameter_group_id	INT				REFERENCES parameter_groups(parameter_group_id),
			PRIMARY KEY (parameter_type_id, parameter_group_id)
		);
		
ALTER TABLE users ADD COLUMN active BOOLEAN DEFAULT true;