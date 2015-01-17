insert into parameter_types values (51, 'parameterType1', 'desc1', 1, 2, 'NUMERIC');
insert into parameter_types values (52, 'parameterType2', 'desc2', 2, 3, 'NUMERIC');
insert into parameter_types values (53, 'parameterType3', 'desc3', 3, 4, 'NUMERIC');

insert into parameter_groups values(51, 'type1');
insert into parameter_groups values(52, 'type2');

insert into parameter_type_parameter_group values (51, 51);
insert into parameter_type_parameter_group values (51, 52);
insert into parameter_type_parameter_group values (52, 52);