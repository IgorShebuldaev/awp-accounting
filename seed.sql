insert into roles(role, lookup_code) values('Administrator','admin');
insert into roles(role, lookup_code) values('Accounting','manager');
insert into users(email, password, role_id) values(1,1,(select id from roles where lookup_code='admin'));
insert into users(email, password, role_id) values(2,2,(select id from roles where lookup_code='manager'));