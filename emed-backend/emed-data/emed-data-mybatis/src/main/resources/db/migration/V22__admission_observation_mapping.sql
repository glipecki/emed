create table admission_observation (
        admission_id int references admissions(admission_id),
        type varchar(50),
        
        PRIMARY KEY (admission_id, type)
     );