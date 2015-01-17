ALTER TABLE observations ADD COLUMN admission_id INT;

ALTER TABLE observations ADD FOREIGN KEY (admission_id) REFERENCES admissions (admission_id);
		
UPDATE observations o SET admission_id = (SELECT admission_id FROM admissions a WHERE o.patient_id = a.patient_id);

ALTER TABLE observations ALTER COLUMN admission_id SET NOT NULL;
