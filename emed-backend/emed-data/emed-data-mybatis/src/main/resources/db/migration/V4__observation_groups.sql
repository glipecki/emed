--
-- Observations groups
--

CREATE TABLE observations_groups
		(
			group_key		VARCHAR(50),
			type			VARCHAR(20),
			
			PRIMARY KEY (group_key, type)
		);
		
--
-- Data
--

insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'HR');
insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'SAT');
insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'ABP');
insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'CVP');
insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'PAP');
insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'LAP');
insert into observations_groups (group_key, type) values ('HEMODYNAMICS', 'CO');
		
