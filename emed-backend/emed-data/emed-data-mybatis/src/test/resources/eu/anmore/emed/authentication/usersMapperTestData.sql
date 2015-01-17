insert into users values (1, 'john', 'test', 'username', 'c88e9c67041a74e0357befdff93f87dde0904214', 'salt', true);
insert into users values (2, 'mark', 'test', 'test-user', 'c88e9c67041a74e0357befdff93f87dde0904214', 'salt', true);

insert into permissions values (1, 'test');
insert into permissions values (2, 'bf_authentication_authenticate');

insert into user_permission values (2, 1);
insert into user_permission values (2, 2);
