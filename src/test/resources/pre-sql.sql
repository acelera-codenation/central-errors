delete from users;
delete from event;

insert into event(id, level_error, description, log_error, origin, date_error, quantity)
values (1, 'ERROR', 'Bad Credentials', 'Bad Credentials', 'auth', now(), 1);

insert into event(id, level_error, description, log_error, origin, date_error, quantity)
values (2, 'ERROR', 'No access', 'Service down', 'timeout', now(), 1);
