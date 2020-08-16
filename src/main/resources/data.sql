delete from users;
delete from event;

insert into users (created_at,updated_at,email, "password", username) VALUES
('2020-07-30 11:02:17.800','2020-07-30 11:02:17.800','string@test.com','$2a$10$wZMI.SbNKl5HNctV8mNH8umTiEzXfLU5ayGYkJ6o8kS8xreMvkt3y','string');


insert into event(date_error, description, level_error, log_error, origin, quantity)
values (
'2020-07-30 11:02:17.800', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1),
(
'2020-06-30 11:02:17.800', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1),
(
'2020-05-30 11:02:17.800', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1),
(
'2020-07-29 11:02:17.800', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1);
