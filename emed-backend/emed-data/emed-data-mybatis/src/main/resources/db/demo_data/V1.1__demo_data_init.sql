ALTER SEQUENCE users_user_id_seq RESTART WITH 1000;
ALTER SEQUENCE permissions_permission_id_seq RESTART WITH 1000;
ALTER SEQUENCE patients_patient_id_seq RESTART WITH 1000;
ALTER SEQUENCE admissions_admission_id_seq RESTART WITH 1000;

insert into users (user_id, first_name, surname, username, password, salt) values (2, 'mark', 'twain', 'test-user', 'c88e9c67041a74e0357befdff93f87dde0904214', 'salt');
insert into users (user_id, first_name, surname, username, password, salt) values (3, 'Grzegorz', 'Lipecki', 'glipecki', 'c88e9c67041a74e0357befdff93f87dde0904214', 'salt');
insert into users (user_id, first_name, surname, username, password, salt) values (4, 'Marcin', 'Miedziński', 'mmiedzinski', 'c88e9c67041a74e0357befdff93f87dde0904214', 'salt');

insert into patients (patient_id, first_name, surname, pesel, birthday, sex) values (1, 'John', 'Doe', '89031004513', '1989-03-10', 'MAN');
insert into patients (patient_id, first_name, surname, pesel, birthday, sex) values (2, 'Mark', 'Twain', '89072306830', '1989-07-23', 'MAN');
insert into patients (patient_id, first_name, surname, pesel, birthday, sex) values (3, 'Maria', 'Santos', '89041004513', '1989-04-10', 'WOMAN');
