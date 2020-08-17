delete from users;
delete from event;

insert into users (created_at,updated_at,email, "password", username) values
(now(), now(),'string@test.com','$2a$10$wZMI.SbNKl5HNctV8mNH8umTiEzXfLU5ayGYkJ6o8kS8xreMvkt3y','string'),
(now(), now(),'zelda@codenation.com','$2a$10$zHLk8wjO2mYAMGkxbLYMDO2vu5r.oIcfU.nraMVociZYjnmyVRANi', 'zelda');

insert into event(date_event, description, level_event, log_event, origin, quantity)
values (
'2020-07-30', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1),
(
'2020-06-30', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1),
(
'2020-05-30', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1),
(
'2020-07-29', 'restartedMain', 'INFO', 'org.springframework.web.servlet.mvc.method.annotation',
'ControllerAdvice beans', 1);
