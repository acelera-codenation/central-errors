delete from event;
delete from users;

insert into event(id, date_error, description, level_error, log_error, origin, quantity) values
(1, '2020-07-30 11:02:17.800', 'ID-1', 'WARNING', 'org.springframework' ,'ControllerAdvice beans', 1),
(2, '2020-06-30 11:02:17.800', 'ID-2', 'INFO', 'org.springframework', 'ControllerAdvice beans', 2),
(3, '2020-05-30 11:02:17.800', 'ID-3', 'ERROR', 'org.springframework', 'ControllerAdvice beans', 3);
