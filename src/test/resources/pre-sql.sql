delete from users;
delete from event;

insert into event (id, level_error, description, log_error, origin, date_error, quantity)
values (1, 1, 'Bad Credentials', 'Bad Credentials', 'auth', now(), 1);

insert into event (id, level_error, description, log_error, origin, date_error, quantity)
values (2, 1, 'No access', 'Service down', 'timeout', now(), 1);
