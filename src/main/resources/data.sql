delete from users;
delete from event;

insert into public.users (created_at,updated_at,email, "password", username) VALUES
('2020-07-30 11:02:17.800','2020-07-30 11:02:17.800','string@test.com','$2a$10$wZMI.SbNKl5HNctV8mNH8umTiEzXfLU5ayGYkJ6o8kS8xreMvkt3y','string')
;

insert into event (level_error, description, log_error, origin, date_error, quantity)
values ('ERROR', 'Bad Credentials', 'Bad Credentials', 'auth', now(), 1);

insert into event (level_error, description, log_error, origin, date_error, quantity)
values ('ERROR', 'No access', 'Service down', 'timeout', now(), 1);