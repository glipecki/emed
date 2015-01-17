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
-- Users data
--

insert into users (user_id, first_name, surname, username, password, salt) values (1, 'Administrator', '', 'administrator', 'c88e9c67041a74e0357befdff93f87dde0904214', 'salt');
ALTER SEQUENCE users_user_id_seq RESTART WITH 1000;

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
