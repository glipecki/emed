CREATE TABLE observation_norm
		(
			observation_type	VARCHAR(50),
			age_range 			VARCHAR(50),
			min_norm 			VARCHAR(50),
			max_norm			VARCHAR(50),
			min_alarm			VARCHAR(50),
			max_alarm			VARCHAR(50)
		);
		
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('HR', 'FIRST', '130', '140', '110', '160');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('HR', 'SECOND', '120', '140', '110', '160');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('HR', 'THIRD', '100', '120', '90', '130');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('HR', 'FOURTH', '90', '100', '80', '120');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('HR', 'FIFTH', '60', '100', '55', '120');

INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('SAT', 'FIRST', '95', '100', '90', '101');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('SAT', 'SECOND', '95', '100', '90', '101');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('SAT', 'THIRD', '95', '100', '90', '101');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('SAT', 'FOURTH', '95', '100', '90', '101');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('SAT', 'FIFTH', '95', '100', '90', '101');
	
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('CVP', 'FIRST', '5', '10', '4', '12');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('CVP', 'SECOND', '5', '10', '4', '12');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('CVP', 'THIRD', '5', '10', '4', '12');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('CVP', 'FOURTH', '5', '10', '4', '12');
INSERT INTO observation_norm (observation_type, age_range, min_norm, max_norm, min_alarm, max_alarm)
	values ('CVP', 'FIFTH', '5', '10', '4', '12');	
